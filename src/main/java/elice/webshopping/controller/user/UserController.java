package elice.webshopping.controller.user;

import elice.webshopping.domain.user.*;
import elice.webshopping.repository.user.RefreshRepository;
import elice.webshopping.repository.user.UserRepository;
import elice.webshopping.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //회원가입 후 id를 반환
    @PostMapping("/api/user")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userRequestDto) {
        String register_id = userService.save(userRequestDto);

        return ResponseEntity.ok(register_id);
    }

    //회원 정보 단일 조회
    @GetMapping("/api/user/info")
    public ResponseEntity<?> getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //현재 로그인한 유저의 id 가져오기
        UserResponseDto userResponseDto = userService.findByUsername(username); //가져온 id를 기반으로 유저 정보 가져오기

        return ResponseEntity.ok(userResponseDto);
    }

    //회원 전체 조회
    @GetMapping("/api/user/findAll")
    public ResponseEntity<List<User>> findAllUsers(){
        List<User> Users =userService.findAll();

        return ResponseEntity.ok(Users);
    }

    @GetMapping("/api/user/findAllDto")
    public ResponseEntity<List<UserResponseDto>> findAllUsersDto(){
        List<UserResponseDto> users = userService.find();
        return ResponseEntity.ok(users);
    }


    //회원 정보 수정
    @PutMapping("/api/user/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //현재 로그인한 유저의 id 가져와서

        User updatedUser = userService.update(username, userUpdateDto); //회원 정보 업데이트

        return ResponseEntity.ok(updatedUser);
    }

    //회원 탈퇴
    @DeleteMapping("/api/user/delete")
    public ResponseEntity<?> deleteUser(HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        userService.delete(username);

        refreshRepository.deleteByUsername(username);

        //Refresh 토큰 Cookie 값 0
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);

        return ResponseEntity.ok("Deleted ok");
    }

    //비밀번호 일치 확인
    @PostMapping("/api/user/passwordCheck")
    public ResponseEntity<?> checkPassword(@RequestBody PasswordDto passwordDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User currentUser = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("로그인한 유저를 찾을 수 없습니다"));

        boolean match = bCryptPasswordEncoder.matches(passwordDto.getPassword(), currentUser.getPassword());

        if(match){
            return ResponseEntity.ok(true);
        }
        else {
            return ResponseEntity.ok(false);
        }
    }

    //관리자 - 회원 강제 탈퇴
    @DeleteMapping("/api/user/adminDelete/{id}")
    public ResponseEntity<?> deleteUserTest(@PathVariable Long id){
        userService.deleteByUserId(id);

        return ResponseEntity.ok("deleted");
    }

    //관리자 회원가입
    @PostMapping("/api/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody UserRequestDto userRequestDto) {
        String register_id = userService.saveAdmin(userRequestDto);

        return ResponseEntity.ok(register_id);
    }

    //관리자 계정인지 확인
    @PostMapping("/api/user/admin-check")
    public ResponseEntity<?> checkAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        UserResponseDto user = userService.findByUsername(username);

        //log.info(String.valueOf(user.getRole()));

        Map<String, String> response = new HashMap<>();
        if (String.valueOf(user.getRole()).equals("ROLE_ADMIN")) {
            response.put("result", "success"); // JSON key-value로 반환
            return ResponseEntity.ok(response);
        } else {
            /*
            response.put("result", "denied"); // JSON key-value로 반환
            return ResponseEntity.ok(response);
            */
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("denied");
        }
    }
}
