package elice.webshopping.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {
//요청 시 사용할 dto

    private String username; //아이디

    private String password;

    private String real_name;

    private String email;

    private String phone;

    @Builder //@Setter 대신
    public UserRequestDto(String username, String password, String real_name, String email, String phone) {
        this.username = username;
        this.password = password;
        this.real_name = real_name;
        this.email = email;
        this.phone = phone;
    }
}
