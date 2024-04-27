package com.ecommerce.website;

import com.ecommerce.website.dtos.user.SignInResultDto;
import com.ecommerce.website.dtos.user.UserLoginDto;
import com.ecommerce.website.entities.user.UserEntity;
import com.ecommerce.website.entities.user.role.ERole;
import com.ecommerce.website.entities.user.role.RoleEntity;
import com.ecommerce.website.repositories.RoleRepository;
import com.ecommerce.website.repositories.UserRepository;
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
        UserEntity testUser = TestDataUtil.createTestUser();

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
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ERole.ROLE_ADMIN));

        UserEntity admin = UserEntity.builder()
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
