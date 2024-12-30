package elice.webshopping.domain.user;

import elice.webshopping.domain.address.Address;
import elice.webshopping.domain.common.BaseEntity;
import elice.webshopping.repository.user.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class User /*implements UserDetails */{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long user_id;
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username; //아이디

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String real_name; //본명

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

/*
    @Embedded
    private BaseEntity baseEntity;
*/

    @Builder
    public User(String username, String password, String real_name, String email, String phone, Role role) {
        this.username = username;
        this.password = password;
        this.real_name = real_name;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public void update(String username, String password, String real_name, String email, String phone, Role role){
        this.username = username;
        this.password = password;
        this.real_name = real_name;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    @OneToMany(mappedBy = "user")
    private List<Address> addressList = new ArrayList<>();

    /*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){  //사용자가 가진 권한 반환
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername(){ //사용자 이름(아이디) 반환, 고유한 값을 반환..
        return username;
    }

    @Override
    public String getPassword(){ //비밀번호 반환
        return password;
    }

    @Override
    public boolean isAccountNonExpired(){ //계정 만료 여부
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){ //계정 잠금 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){ //비밀번호 만료 여부
        return true;
    }

    @Override
    public boolean isEnabled(){ //계정 사용 가능 여부
        return true;
    }
*/


}
