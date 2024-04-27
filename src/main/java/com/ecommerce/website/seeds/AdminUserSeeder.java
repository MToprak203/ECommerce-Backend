package com.ecommerce.website.seeds;

import com.ecommerce.website.entities.user.UserEntity;
import com.ecommerce.website.entities.user.role.ERole;
import com.ecommerce.website.entities.user.role.RoleEntity;
import com.ecommerce.website.repositories.RoleRepository;
import com.ecommerce.website.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class AdminUserSeeder {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @EventListener
    @Transactional
    public void LoadAdminUser(ContextRefreshedEvent event)
    {
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ERole.ROLE_ADMIN));

        UserEntity admin = UserEntity.builder()
                .fullName(adminName)
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .roles(roles)
                .build();

        userRepository.save(admin);
    }
}
