package elice.webshopping.service.address;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import elice.webshopping.domain.address.Address;
import elice.webshopping.domain.address.addressDto.AddressRequestDto;
import elice.webshopping.domain.address.addressDto.AddressResponseDto;
import elice.webshopping.domain.user.User;
import elice.webshopping.repository.address.AddressRepository;
import elice.webshopping.repository.user.Role;
import elice.webshopping.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddressService addressService;

    private User user;
    private List<Address> mockAddresses;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .username("admin")
                .password("1234")
                .real_name("Test User")
                .email("test@example.com")
                .phone("123-456-7890")
                .role(Role.USER)
                .build();



        mockAddresses = Arrays.asList(
                new Address("12345", "123 Main St", "Apt 101", "Home", true, false, user),
                new Address("45678", "456 Oak St", "Apt 202", "Work", false, false, user)
        );
    }

//    @DisplayName("새로운 기본 배송지가 추가될 때 처리를 테스트")
//    @Test
//    void shouldResetBaseAddressesWhenNewBaseAddressAdded() {
//        // Arrange
//        long userId = 1;
//        AddressRequestDto request = new AddressRequestDto(
//                "345", "Main St","Apt 101", "parent", true, false,"admin"
//        );
//
//        // Act
//        if (request.getIsBaseAddress() == true) {
//            addressRepository.resetBaseAddresses(userId);
//
//        }
//
//        List<Address> nonBaseAddresses = addressRepository.findAll();
//
//        // Assert
//        assertEquals(2, nonBaseAddresses.size(), "비기본 배송지의 개수는 2개여야 합니다.");
//    }

    @DisplayName("해당 유저를 찾지 못하면 에러 처리를 하는지 테스트")
    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        String invalidUsername = "eds";
        when(userRepository.findByUsername(invalidUsername)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> {
                    // AddressService에서 유저를 조회하려는 시점에 예외 발생
                    addressService.save(new AddressRequestDto(invalidUsername, "12345", "Main St", "Apt 101", true, true, "eds"));
                });

        assertThat(e.getMessage()).isEqualTo("User not found with username: " + invalidUsername);
    }


//
//    @Test
//    void shouldSetBaseAddressWhenNoOtherAddressesExist() {
//        // Arrange
//        AddressRequestDto request = new AddressRequestDto("admin", "12345", "Main St", "Apt 101", "Home", false, false);
//        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
//        when(addressRepository.findAllByUsername(user.getUsername())).thenReturn(Collections.emptyList());
//
//        // Act
//        addressService.save(request);
//
//        // Assert
//        assertTrue(request.getIsBaseAddress());
//    }
//
//    @Test
//    void shouldThrowExceptionWhenAddressTargetAlreadyExists() {
//        // Arrange
//        AddressRequestDto request = new AddressRequestDto("admin", "12345", "Main St", "Apt 101", "Home", true, false);
//        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
//        when(addressRepository.existsByUserAndAddressTarget(user, request.getAddressTarget())).thenReturn(true);
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> addressService.save(request));
//    }
//
//    @Test
//    void shouldSaveAddressSuccessfully() {
//        // Arrange
//        AddressRequestDto request = new AddressRequestDto("admin", "12345", "Main St", "Apt 101", "Home", true, false);
//        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
//        when(addressRepository.save(any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Act
//        addressService.save(request);
//
//        // Assert
//        verify(addressRepository).save(any(Address.class));
//    }
}

//    @Test
//    public void testFindAddressSuccess() {
//        // Given: mock the behavior of addressRepository
//        when(addressRepository.findAllByUsername(username)).thenReturn(mockAddresses);
//
//        // When: calling the service method
//        List<AddressResponseDto> response = addressService.findAddress(username);
//
//        // Then: verify the result
//        assertNotNull(response);
//        assertEquals(2, response.size());
//        assertEquals("123 Main St", response.get(0).zipCode());
//        assertEquals("456 Oak St", response.get(1).zipCode());
//
//        // Verify that the repository method was called once
//        verify(addressRepository, times(1)).findAllByUsername(username);
//    }


//    @Test
//    public void testFindAddressNoAddresses() {
//        // Given: empty address list
//        when(addressRepository.findAllByUsername(username)).thenReturn(Collections.emptyList());
//
//        // When & Then: exception is thrown
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
//            addressService.findAddress(username);
//        });
//
//        assertEquals("No addresses found for username: " + username, thrown.getMessage());
//
//        // Verify that the repository method was called once
//        verify(addressRepository, times(1)).findAllByUsername(username);
//    }