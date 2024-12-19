package elice.webshopping.service.user;

import elice.webshopping.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){ //사용자 정보 가져옴
        return userRepository.findByUsername(username).orElseThrow(()->new IllegalArgumentException(username));
    }
}
