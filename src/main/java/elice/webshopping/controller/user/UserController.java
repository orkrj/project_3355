package elice.webshopping.controller.user;

import elice.webshopping.domain.user.UserRequestDto;
import elice.webshopping.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user") //회원가입 후 id를 반환
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userRequestDto) {
        String register_id = userService.save(userRequestDto);

        return ResponseEntity.ok(register_id);
    }

}
