package com.sonmez.services.user;

import com.sonmez.dtos.user.UserLoginDto;
import com.sonmez.entities.user.UserEntity;
import com.sonmez.entities.user.role.RoleEntity;
import com.sonmez.entities.user.role.ERole;
import com.sonmez.exception.user.IncorrectPasswordException;
import com.sonmez.exception.user.UserExistsException;
import com.sonmez.exception.user.UserNotFoundException;
import com.sonmez.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserEntity register(UserEntity user) {

        if (userRepository.existsByEmail(user.getEmail()))
            throw new UserExistsException(user.getEmail());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        RoleEntity role = RoleEntity.builder()
                .id(null)
                .name(ERole.USER)
                .user(user)
                .build();

        user.setRoles(new ArrayList<>());
        user.getRoles().add(role);

        return userRepository.save(user);
    }

    @Override
    public UserEntity login(UserLoginDto userLoginDto) {

        Optional<UserEntity> userOpt = userRepository.findByEmail(userLoginDto.getEmail());

        if (userOpt.isEmpty()) throw new UserNotFoundException(userLoginDto.getEmail());

        if (!passwordEncoder.matches(userLoginDto.getPassword(), userOpt.get().getPassword()))
            throw new IncorrectPasswordException();

        return userOpt.get();
    }

    @Override
    public UserEntity update(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<UserEntity> findOne(Long id) {
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
