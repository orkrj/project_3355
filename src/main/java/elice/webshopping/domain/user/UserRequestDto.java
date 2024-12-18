package elice.webshopping.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {
//요청 시 사용할 dto
    //@NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String username; //아이디
    //@Size(min=8, max=16, message = "비밀번호는 8자 이상 16자 이하로 입력해주세요.")
    private String password;
    //@NotBlank(message = "이름을 입력해주세요.")
    private String real_name;
    //@NotBlank(message = "이메일을 입력해주세요.")
    //@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 맞지 않습니다.")
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
