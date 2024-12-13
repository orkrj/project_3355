package elice.webshopping.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { //패스워드 인코더 빈 등록
        return new BCryptPasswordEncoder();
    }
}
