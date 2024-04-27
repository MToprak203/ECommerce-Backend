package com.ecommerce.website.seeds;

import com.ecommerce.website.entities.user.role.ERole;
import com.ecommerce.website.entities.user.role.RoleEntity;
import com.ecommerce.website.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleDataSeeder {
    @Autowired
    private RoleRepository roleRepository;

    @EventListener
    @Transactional
    public void LoadRoles(ContextRefreshedEvent event) {

        List<ERole> roles = Arrays.stream(ERole.values()).toList();

        for(ERole erole: roles) {
            if (roleRepository.findByName(erole)==null) {
                roleRepository.save(RoleEntity.builder().name(erole).build());
            }
        }

    }

}
