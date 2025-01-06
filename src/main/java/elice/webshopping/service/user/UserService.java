package elice.webshopping.service.user;

import elice.webshopping.domain.user.User;
import elice.webshopping.domain.user.UserRequestDto;
import elice.webshopping.domain.user.UserResponseDto;
import elice.webshopping.domain.user.UserUpdateDto;
import elice.webshopping.repository.user.Role;
import elice.webshopping.repository.user.UserRepository;
import elice.webshopping.service.order.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OrderService orderService;

    public String save(UserRequestDto userRequestDto){ //회원가입
        User register = userRepository.save(User.builder()
                .username(userRequestDto.getUsername())
                .password(bCryptPasswordEncoder.encode(userRequestDto.getPassword()))
                .real_name(userRequestDto.getReal_name())
                .email(userRequestDto.getEmail())
                .phone(userRequestDto.getPhone())
                .role(Role.ROLE_USER)
                .build());

        return register.getUsername();  //가입한 유저의 id 반환
    }

    public String saveAdmin(UserRequestDto userRequestDto){ //관리자 회원가입
        User register = userRepository.save(User.builder()
                .username(userRequestDto.getUsername())
                .password(bCryptPasswordEncoder.encode(userRequestDto.getPassword()))
                .real_name(userRequestDto.getReal_name())
                .email(userRequestDto.getEmail())
                .phone(userRequestDto.getPhone())
                .role(Role.ROLE_ADMIN)
                .build());

        return register.getUsername();  //가입한 유저의 id 반환
    }


    public List<UserResponseDto> find(){
        List<UserResponseDto> users = userRepository.findAll().stream().map(UserResponseDto::new).collect(Collectors.toList());

        return users;
    }



    //회원 정보 전체 조회(관리자 페이지에서 확인하는 용도)
    public List<User> findAll(){
        return userRepository.findAll();
    }

    //회원정보 단일 조회 (특정 유저-로그인상태일때) ok
    public UserResponseDto findByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));

        UserResponseDto userResponseDto = new UserResponseDto(user);

        return userResponseDto;
    }

    //회원정보 수정 ok
    public User update(String username, UserUpdateDto userUpdateDto){
        //원래 정보 불러오기
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));

        //updateDto로 받은 정보를 원래 정보 대신 저장
        //username은 변경 불가인데.. html에서 readOnly로 읽을수만 있게하고 저장되는 값은 원래 정보의 username을 그대로??

        user.update(userUpdateDto.getUsername(),
                bCryptPasswordEncoder.encode(userUpdateDto.getPassword()),
                userUpdateDto.getReal_name(),
                userUpdateDto.getEmail(),
                userUpdateDto.getPhone(),
                Role.ROLE_USER);

        userRepository.save(user);
        return user;
    }

    @Transactional
    //회원 정보 삭제
    public void delete(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(username));

        orderService.deleteOrders(user);
        userRepository.deleteByUsername(username);
        //리프레쉬 토큰까지 삭제?
    }

    /*
    //관리자가 회원 삭제
    @Transactional
    public void deleteByAdmin(Long id){
        userRepository.deleteByUser_id(id);
    }
*/

    @Transactional
    public void deleteByUserId(Long userId){
        userRepository.deleteByUserId(userId);
    }

}
