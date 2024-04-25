package com.sonmez.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonmez.TestDataUtil;
import com.sonmez.dtos.mappers.Mapper;
import com.sonmez.dtos.user.UserDto;
import com.sonmez.dtos.user.UserLoginDto;
import com.sonmez.dtos.user.UserRegisterDto;
import com.sonmez.entities.product.ProductEntity;
import com.sonmez.entities.user.AddressEntity;
import com.sonmez.entities.user.UserEntity;
import com.sonmez.services.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final Mapper<UserEntity, UserDto> userMapper;

    @Autowired
    public UserControllerIntegrationTests(MockMvc mockMvc,
                                          ObjectMapper objectMapper,
                                          UserService userService,
                                          Mapper<UserEntity, UserDto> userMapper)
    {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Test
    public void registerTest() throws Exception {
        UserRegisterDto registerDto = TestDataUtil.createTestUserRegisterDto();
        String registerDtoJson = objectMapper.writeValueAsString(registerDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fullName").value("testTester")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("testemail@test.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.phoneNumber").value("+555555555555")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.roles[0].role").value("USER")
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errorMessage").isString()
        );
    }

    @Test
    public void testPasswordEncoded()
    {
        UserEntity savedUser =  userService.register(TestDataUtil.createTestUser());
        assertThat(savedUser.getPassword()).isNotEqualTo("asdsfaA15!2");
    }

    @Test
    public void loginTest() throws Exception {
        UserEntity user = TestDataUtil.createTestUser();
        userService.register(user);

        UserLoginDto loginDto = TestDataUtil.createTestUserLoginDto();
        String loginDtoJson = objectMapper.writeValueAsString(loginDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fullName").value(user.getFullName())
        );

        loginDto.setPassword("incorrectPass");
        loginDtoJson = objectMapper.writeValueAsString(loginDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isUnauthorized()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errorMessage").isString()
        );

        loginDto = TestDataUtil.createTestUserLoginDto();
        loginDto.setEmail("incorrectEmail@test.com");
        loginDtoJson = objectMapper.writeValueAsString(loginDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isUnauthorized()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errorMessage").isString()
        );
    }

    @Test
    public void listUsers() throws Exception {
        for(int i = 0; i < 5; i++)
        {
            UserEntity user = TestDataUtil.createTestUser();
            user.setId(null);
            user.setEmail(String.valueOf(i) + "@test.com");
            user.setPhoneNumber("+55555555555" + i);
            userService.register(user);
        }

        mockMvc.perform(
                MockMvcRequestBuilders.get("admin/users")
                        .param("page", "0")
                        .param("size", "4")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content.length()").value(4)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].fullName").value("testTester")
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("admin/users")
                        .param("page", "1")
                        .param("size", "4")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content.length()").value(1)
        );
    }

    @Test
    public void getUserTest() throws Exception {
        UserEntity user = TestDataUtil.createTestUser();
        AddressEntity address = TestDataUtil.createTestAddress();
        address.setUser(user);
        user.getAddresses().add(address);
        userService.register(user);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fullName").value(user.getFullName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].address").value(user.getAddresses().get(0).getAddress())
        );
    }

    @Test
    public void updateUserTest() throws Exception {
        UserEntity user = TestDataUtil.createTestUser();
        userService.register(user);

        UserDto userDto = userMapper.mapTo(user);
        userDto.setFullName("dsafasd");
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fullName").value(userDto.getFullName())
        );

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + 99)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void deleteProductTest() throws Exception {
        UserEntity user = TestDataUtil.createTestUser();
        userService.register(user);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/products/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/products/" + 99)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
