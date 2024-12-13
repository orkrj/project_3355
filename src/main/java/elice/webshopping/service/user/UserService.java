package elice.webshopping.service.user;

import elice.webshopping.domain.user.User;
import elice.webshopping.domain.user.UserRequestDto;
import elice.webshopping.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(UserRequestDto userRequestDto){ //회원가입
        User register = userRepository.save(User.builder()
                .username(userRequestDto.getUsername())
                .password(bCryptPasswordEncoder.encode(userRequestDto.getPassword()))
                .real_name(userRequestDto.getReal_name())
                .email(userRequestDto.getEmail())
                .phone(userRequestDto.getPhone())
                .build());

        return register.getUser_id();

    }

}
