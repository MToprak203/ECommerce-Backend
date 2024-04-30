package com.ecommerce.website.controllers;

import com.ecommerce.website.UserLoginUtil;
import com.ecommerce.website.dtos.user.auth.SignInResultDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ecommerce.website.TestDataUtil;
import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.dtos.user.UserDto;
import com.ecommerce.website.dtos.user.auth.UserLoginDto;
import com.ecommerce.website.dtos.user.auth.UserRegisterDto;
import com.ecommerce.website.entities.user.Address;
import com.ecommerce.website.entities.user.User;
import com.ecommerce.website.services.user.UserService;
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

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private Mapper<User, UserDto> userMapper;
    @Autowired
    private UserLoginUtil userLoginUtil;

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
                MockMvcResultMatchers.jsonPath("$.roles[0].name").value("ROLE_USER")
        );

        // Already registered user.

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isConflict()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errorMessage").isString()
        );
    }

    @Test
    public void testPasswordEncoded() {
        User savedUser = userService.register(TestDataUtil.createTestUser());
        assertThat(savedUser.getPassword()).isNotEqualTo("asdsfaA15!2");
    }

    @Test
    public void loginTest() throws Exception {
        User user = TestDataUtil.createTestUser();
        userService.register(user);

        UserLoginDto loginDto = TestDataUtil.createTestUserLoginDto();
        String loginDtoJson = objectMapper.writeValueAsString(loginDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.token").isString()
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
        for (int i = 0; i < 5; i++) {
            User user = TestDataUtil.createTestUser();
            user.setId(null);
            user.setEmail(i + "@test.com");
            user.setPhoneNumber("+555555555555");
            userService.register(user);
        }

        // Unauthorized without token
        mockMvc.perform(
                MockMvcRequestBuilders.get("/admin/users")
                        .param("page", "0")
                        .param("size", "4")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isUnauthorized()
        );


        // User is not authorized
        SignInResultDto userSignIn = userLoginUtil.TestUserLogin();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/admin/users")
                        .header("Authorization",
                                userSignIn.getType() + " " + userSignIn.getToken())
                        .param("page", "0")
                        .param("size", "4")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );

        // Admin is authorized

        SignInResultDto adminSignIn = userLoginUtil.TestAdminLogin();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/admin/users")
                        .header("Authorization",
                                adminSignIn.getType() + " " + adminSignIn.getToken())
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


        // It should return remain users.
        mockMvc.perform(
                MockMvcRequestBuilders.get("/admin/users")
                        .header("Authorization",
                                adminSignIn.getType() + " " + adminSignIn.getToken())
                        .param("page", "1")
                        .param("size", "4")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content.length()").value(3)
        );
    }

    @Test
    public void getUserTest() throws Exception {
        User user = TestDataUtil.createTestUser();
        user.setId(null);
        user.setEmail("user@test.com");
        Address address = TestDataUtil.createTestAddress();
        address.setId(null);
        address.setUser(user);
        user.getAddresses().add(address);
        User savedUser = userService.register(user);

        // Authentication is required
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isUnauthorized()
        );

        // User can get its data
        SignInResultDto userSignIn = userLoginUtil.TestUserLogin();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + userSignIn.getId())
                        .header("Authorization",
                                userSignIn.getType() + " " + userSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(userSignIn.getEmail())
        );

        // User can not get another user's data
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + savedUser.getId())
                        .header("Authorization",
                                userSignIn.getType() + " " + userSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );


        // Admin can get users' data
        SignInResultDto adminSignIn = userLoginUtil.TestAdminLogin();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + savedUser.getId())
                        .header("Authorization",
                                adminSignIn.getType() + " " + adminSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fullName").value(user.getFullName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].address")
                        .value(user.getAddresses().get(0).getAddress())
        );


    }

    @Test
    public void updateUserTest() throws Exception {
        User user = TestDataUtil.createTestUser();
        user.setEmail("fdsafwq@test.com");
        user.setId(null);
        User savedUser = userService.register(user);

        UserDto userDto = userMapper.mapTo(user);
        userDto.setFullName("dsafasd");
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        // Authentication is required
        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isUnauthorized()
        );

        SignInResultDto userSignIn = userLoginUtil.TestUserLogin();

        // User can not change another user's data
        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + savedUser.getId())
                        .header("Authorization",
                                userSignIn.getType() + " " + userSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );

        // User can change its data
        userDto.setId(userSignIn.getId());
        userDto.setEmail(userSignIn.getEmail());
        userDto.setFullName("fdsafewdad");
        userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + userSignIn.getId())
                        .header("Authorization",
                                userSignIn.getType() + " " + userSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fullName").value(userDto.getFullName())
        );

        SignInResultDto adminSignIn = userLoginUtil.TestAdminLogin();

        // Admin can change user's data

        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFullName("xzcsafq");
        userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + savedUser.getId())
                        .header("Authorization",
                                adminSignIn.getType() + " " + adminSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fullName").value(userDto.getFullName())
        );


        // If user not found, return 404
        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + 999)
                        .header("Authorization",
                                adminSignIn.getType() + " " + adminSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void deleteUserTest() throws Exception {
        User user = TestDataUtil.createTestUser();
        user.setEmail("asd@test.com");
        user.setId(null);
        User savedUser = userService.register(user);

        // Authorization is required
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isUnauthorized()
        );

        SignInResultDto userSignIn = userLoginUtil.TestUserLogin();

        // A user can not delete another user
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + savedUser.getId())
                        .header("Authorization",
                                userSignIn.getType() + " " + userSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );

        // A user can delete its data
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + userSignIn.getId())
                        .header("Authorization",
                                userSignIn.getType() + " " + userSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );

        SignInResultDto adminSignIn = userLoginUtil.TestAdminLogin();

        // Admin can delete a user
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + savedUser.getId())
                        .header("Authorization",
                                adminSignIn.getType() + " " + adminSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );

        // If user not found return no content
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + 99)
                        .header("Authorization",
                                adminSignIn.getType() + " " + adminSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
