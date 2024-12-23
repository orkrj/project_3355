package elice.webshopping.controller.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import elice.webshopping.domain.address.Address;
import elice.webshopping.domain.address.addressDto.AddressRequestDto;
import elice.webshopping.domain.user.User;
import elice.webshopping.repository.address.AddressRepository;
import elice.webshopping.repository.user.Role;
import elice.webshopping.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//@SpringBootTest
//@AutoConfigureMockMvc
//class AddressControllerTest {
//
//    @Autowired
//    protected MockMvc mockMvc;
//
//    @Autowired
//    protected ObjectMapper objectMapper;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    AddressRepository addressRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @BeforeEach
//    public void mockMvcSetUp() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//        addressRepository.deleteAll();
//        userRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("특정 주소를 수정하는데 성공한다.")
//    public void updateAddress() throws Exception {
//        // given
//        final String url = "/address/specific";
//
//        User user = userRepository.save(User.builder()
//                .username("admin")
//                .password("1234")
//                .real_name("Test User")
//                .email("test@example.com")
//                .phone("123-456-7890")
//                .role(Role.USER)
//                .build());
//
//        Address address = addressRepository.save(Address.builder()
//                .zipCode("12345")
//                .streetAddress("123 Test St")
//                .detailAddress("Apt 101")
//                .addressTarget("Home")
//                .user(user)
//                .build());
//
//        AddressRequestDto requestDto = new AddressRequestDto(
//                "67890",
//                "456 Sample Rd",
//                "Suite 202",
//                "Home", // addressTarget 추가
//                true,
//                false,
//                "admin"
//        );
//
//        String request = objectMapper.writeValueAsString(requestDto);
//
//        // when
//        mockMvc.perform(put(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(request))
//                .andExpect(status().isOk());
//
//        // then
//        Address myAddress = addressRepository.findByUsernameAndBaseAddress(
//                requestDto.getUsername()
//        ).orElseThrow(() -> new AddressNotFoundException("해당 주소를 찾을 수 없습니다."));;
//
//
//        assertEquals("67890", myAddress.getZipCode());
//        assertEquals("456 Sample Rd", myAddress.getStreetAddress());
//        assertEquals("Suite 202", myAddress.getDetailAddress());
//        assertEquals("Home", myAddress.getAddressTarget()); // addressTarget 검증
//        assertEquals("admin", myAddress.getUser().getUsername()); // username 검증
//    }
//
//
//}

