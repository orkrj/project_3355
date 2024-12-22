package elice.webshopping.controller.user;

import elice.webshopping.domain.user.User;
import elice.webshopping.domain.user.UserRequestDto;
import elice.webshopping.domain.user.UserResponseDto;
import elice.webshopping.domain.user.UserUpdateDto;
import elice.webshopping.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //회원가입 후 id를 반환
    @PostMapping("/user")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userRequestDto) {
        String register_id = userService.save(userRequestDto);

        return ResponseEntity.ok(register_id);
    }

    //회원 정보 단일 조회
    @GetMapping("/user/info")
    public ResponseEntity<?> getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //현재 로그인한 유저의 id 가져오기
        UserResponseDto userResponseDto = userService.findByUsername(username); //가져온 id를 기반으로 유저 정보 가져오기

        return ResponseEntity.ok(userResponseDto);
    }

    //회원 전체 조회
    @GetMapping("/user/findAll")
    public ResponseEntity<List<User>> findAllUsers(){
        List<User> Users =userService.findAll();

        return ResponseEntity.ok(Users);
    }

    //회원 정보 수정
    @PutMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //현재 로그인한 유저의 id 가져와서

        User updatedUser = userService.update(username, userUpdateDto); //회원 정보 업데이트

        return ResponseEntity.ok(updatedUser);
    }

    //회원 탈퇴
    @DeleteMapping("/user/delete")
    public ResponseEntity<?> deleteUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        userService.delete(username);

        return ResponseEntity.ok("Deleted");
    }


}
