package elice.webshopping.domain.user;

import lombok.Getter;

@Getter
public class UserUpdateDto {
    private String username;
    private String password;
    private String real_name;
    private String email;
    private String phone;

}
