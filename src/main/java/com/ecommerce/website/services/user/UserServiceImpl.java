package com.ecommerce.website.services.user;

import com.ecommerce.website.dtos.user.auth.SignInResultDto;
import com.ecommerce.website.dtos.user.auth.UserLoginDto;
import com.ecommerce.website.entities.user.User;
import com.ecommerce.website.exception.user.UserAlreadyExistsException;
import com.ecommerce.website.repositories.user.components.RoleRepository;
import com.ecommerce.website.repositories.user.UserRepository;
import com.ecommerce.website.security.jwt.JwtUtils;
import com.ecommerce.website.security.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User register(User user) {

        if (userRepository.existsByEmail(user.getEmail()))
            throw new UserAlreadyExistsException(user.getEmail());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(new HashSet<>());
        user.getRoles().add(roleRepository.findByName("ROLE_USER"));

        return userRepository.save(user);
    }

    @Override
    public SignInResultDto login(UserLoginDto userLoginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getEmail(),
                        userLoginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return SignInResultDto.builder()
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .token(jwt)
                .type("Bearer")
                .roles(roles)
                .build();
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findOne(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
