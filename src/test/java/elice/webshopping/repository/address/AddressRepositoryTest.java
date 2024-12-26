package elice.webshopping.repository.address;

import elice.webshopping.domain.address.Address;
import elice.webshopping.domain.user.User;
import elice.webshopping.repository.user.Role;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@ActiveProfiles("test")
//@AutoConfigureTestEntityManager
//class AddressRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private AddressRepository addressRepository;
//
//    private User user1;
//    private User user2;
//
//    @BeforeEach
//    void setUp() {
//        // 사용자 생성
//        user1 = User.builder()
//                .username("admin")
//                .password("1234")
//                .real_name("Admin User")
//                .email("admin@example.com")
//                .phone("123-456-7890")
//                .role(Role.USER)
//                .build();
//
//            user2 = User.builder()
//                .username("user1")
//                .password("abcd")
//                .real_name("Another User")
//                .email("user1@example.com")
//                .phone("987-654-3210")
//                .role(Role.ADMIN)
//                .build();
//
//        entityManager.persist(user1);
//        entityManager.persist(user2);
//
//        // 주소 생성
//        Address address1 = Address.builder()
//                .zipCode("12345")
//                .streetAddress("123 Main St")
//                .detailAddress("Apt 101")
//                .addressTarget("Home")
//                .isBaseAddress(true)
//                .isDeleted(false)
//                .user(user1)
//                .build();
//
//        Address address2 = Address.builder()
//                .zipCode("67890")
//                .streetAddress("456 Oak St")
//                .detailAddress("Apt 202")
//                .addressTarget("Work")
//                .isBaseAddress(false)
//                .isDeleted(true)
//                .user(user1)
//                .build();
//
//        Address address3 = Address.builder()
//                .zipCode("45678")
//                .streetAddress("789 Pine St")
//                .detailAddress("Apt 303")
//                .addressTarget("Office")
//                .isBaseAddress(true)
//                .isDeleted(false)
//                .user(user2)
//                .build();
//
//        Address address4 = Address.builder()
//                .zipCode("11223")
//                .streetAddress("101 Elm St")
//                .detailAddress("Apt 404")
//                .addressTarget("Vacation Home")
//                .isBaseAddress(false)
//                .isDeleted(false)
//                .user(user2)
//                .build();
//
//        entityManager.persist(address1);
//        entityManager.persist(address2);
//        entityManager.persist(address3);
//
//        entityManager.flush();
//    }
//
//
//    @Test
//    @DisplayName("findAllByUsername 쿼리 삭제 후 조회 테스트")
//    @Transactional
//    void testFindAllByUsername() {
//        // Act
//        List<Address> addresses = addressRepository.findAllByUsername("admin");
//
//        // Assert
//        assertEquals(1, addresses.size(), "admin 사용자의 주소는 1개여야 합니다.");
//        assertTrue(addresses.stream().anyMatch(a -> a.getStreetAddress().equals("123 Main St")));
//    }
//
//
//
//    @Test
//    @DisplayName("findBaseAddress 쿼리 테스트")
//    @Transactional
//    void testFindBaseAddress() {
//
//        // Act
//        Optional<Address> result = addressRepository.findBaseAddress("user1");
//
//        // Assert
//        assertTrue(result.isPresent(), "사용자의 기본 주소가 존재해야 합니다.");
//        assertEquals("789 Pine St", result.get().getStreetAddress(), "기본 주소가 올바르지 않습니다.");
//        assertTrue(result.get().getIsBaseAddress(), "기본 주소여야 합니다.");
//    }
//
//
//    @Test
//    @DisplayName("findByUsernameAndAddressTarget 쿼리 테스트")
//    @Transactional
//    void testFindByUsernameAndAddressTarget() {
//        // Act
//        Optional<Address> result = addressRepository.findByAddressTarget("user1", "Home");
//
//        // Assert
//        assertFalse(result.isPresent(), "겹치는 배송지명이 있어서 등록할 수 없습니다.");
//
//
//        // Act
//        Optional<Address> resultForNonExistingTarget = addressRepository.findByAddressTarget("user1", "job");
//
//        // Assert
//        assertFalse(resultForNonExistingTarget.isPresent(), "겹치는 주소가 존재하지 않아서 등록가능합니다.");
//
//        // Act
//        Optional<Address> resultForNonExistingUser = addressRepository.findByAddressTarget("nonexistentUser", "Work");
//
//        // Assert
//        assertFalse(resultForNonExistingUser.isPresent(), "존재하지 않는 사용자에 대한 주소는 존재하지 않아야 합니다.");
//    }
//
//
//
//
//
//}

