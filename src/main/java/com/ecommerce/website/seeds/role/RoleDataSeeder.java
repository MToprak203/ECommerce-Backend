package com.ecommerce.website.seeds.role;

import com.ecommerce.website.entities.user.components.role.Role;
import com.ecommerce.website.repositories.user.components.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RoleDataSeeder {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @EventListener
    @Transactional
    public void LoadRoles(ContextRefreshedEvent event) {

        List<String> roles = List.of(
                "ROLE_ADMIN",
                "ROLE_USER"
        );

        for (String role : roles) {
            if (roleRepository.findByName(role) == null) {
                roleRepository.save(createRole(role));
            }
        }

        eventPublisher.publishEvent(new RolesSeededEvent(this));
    }

    private Role createRole(String role) {
        switch (role) {
            case "ROLE_ADMIN" -> {
                return createAdminRole();
            }
            case "ROLE_USER" -> {
                return createUserRole();
            }
            default -> {
                return null;
            }
        }
    }

    private Role createAdminRole() {
        return Role.builder()
                .name("ROLE_ADMIN")
                .editAllProductsPermission(true)
                .editAllUsersPermission(true)
                .editSelfProductsPermission(true)
                .editSelfUserPermission(true)
                .editProductCategoriesPermission(true)
                .build();
    }

    private Role createUserRole() {
        return Role.builder()
                .name("ROLE_USER")
                .editAllProductsPermission(false)
                .editAllUsersPermission(false)
                .editSelfProductsPermission(true)
                .editSelfUserPermission(true)
                .editProductCategoriesPermission(false)
                .build();
    }
}

