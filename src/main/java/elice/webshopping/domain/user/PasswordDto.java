package elice.webshopping.domain.user;

import lombok.Getter;

@Getter
public class PasswordDto { //회원탈퇴 시 비밀번호 입력 확인을 위한 dto
    private String password;
}
