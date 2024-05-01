package com.ecommerce.website.seeds.admin;

import com.ecommerce.website.entities.user.User;
import com.ecommerce.website.entities.user.components.role.Role;
import com.ecommerce.website.repositories.user.components.RoleRepository;
import com.ecommerce.website.repositories.user.UserRepository;
import com.ecommerce.website.seeds.role.RolesSeededEvent;
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
    public void LoadAdminUser(RolesSeededEvent event) {
        if(userRepository.existsByEmail(adminEmail)) return;

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_ADMIN"));

        User admin = User.builder()
                .fullName(adminName)
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .roles(roles)
                .build();

        userRepository.save(admin);
    }
}
