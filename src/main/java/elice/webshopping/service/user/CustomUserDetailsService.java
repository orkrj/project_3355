package elice.webshopping.service.user;

import elice.webshopping.domain.user.CustomUserDetails;
import elice.webshopping.domain.user.User;
import elice.webshopping.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userData = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));

        return new CustomUserDetails(userData);
    }
}
