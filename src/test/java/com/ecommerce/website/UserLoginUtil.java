package com.ecommerce.website;

import com.ecommerce.website.dtos.user.auth.SignInResultDto;
import com.ecommerce.website.dtos.user.auth.UserLoginDto;
import com.ecommerce.website.entities.user.User;
import com.ecommerce.website.entities.user.components.role.Role;
import com.ecommerce.website.repositories.user.components.RoleRepository;
import com.ecommerce.website.repositories.user.UserRepository;
import com.ecommerce.website.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserLoginUtil {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    public SignInResultDto TestUserLogin()
    {
        User testUser = TestDataUtil.createTestUser();

        userService.register(testUser);

        return userService.login(UserLoginDto
                .builder()
                .email(testUser.getEmail())
                .password(TestDataUtil.createTestUser().getPassword())
                .build()
        );
    }

    public SignInResultDto TestAdminLogin()
    {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_ADMIN"));

        User admin = User.builder()
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .roles(roles)
                .build();

        userRepository.save(admin);

        return userService.login(UserLoginDto
                .builder()
                .email(adminEmail)
                .password(adminPassword)
                .build()
        );
    }
}
