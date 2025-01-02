package elice.webshopping.domain.user;

import elice.webshopping.repository.user.Role;
import lombok.Getter;

@Getter

public class UserResponseDto {
    private Long user_id;
    private String username;
    private String password;
    private String real_name;
    private String email;
    private String phone;

    private Role role;


    public UserResponseDto(User user) {
        this.user_id=user.getUserId();
        //this.user_id = user.getUser_id();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.real_name = user.getReal_name();
        this.email = user.getEmail();
        this.phone = user.getPhone();

        this.role = user.getRole();
    }


}
