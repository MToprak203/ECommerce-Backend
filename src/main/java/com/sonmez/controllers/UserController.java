package com.sonmez.controllers;

import com.sonmez.dtos.mappers.Mapper;
import com.sonmez.dtos.user.SignInResultDto;
import com.sonmez.dtos.user.UserDto;
import com.sonmez.dtos.user.UserLoginDto;
import com.sonmez.dtos.user.UserRegisterDto;
import com.sonmez.entities.user.UserEntity;
import com.sonmez.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;
    private final Mapper<UserEntity, UserDto> userMapper;
    private final Mapper<UserEntity, UserRegisterDto> userRegisterMapper;

    public UserController(UserService userService,
                          Mapper<UserEntity, UserDto> userMapper,
                          Mapper<UserEntity, UserRegisterDto> userRegisterMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRegisterMapper = userRegisterMapper;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto)
    {
        UserEntity userEntity = userRegisterMapper.mapFrom(userRegisterDto);
        UserEntity savedUserEntity = userService.register(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<SignInResultDto> loginUser(@Valid @RequestBody UserLoginDto userLoginDto)
    {
        SignInResultDto resultDto = userService.login(userLoginDto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @GetMapping("admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDto>> listUsers(Pageable pageable)
    {
        Page<UserEntity> userEntities = userService.findAll(pageable);
        return new ResponseEntity<>(userEntities.map(userMapper::mapTo), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_USER') and @authComponent.hasPermission(#id) or hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id)
    {
        Optional<UserEntity> foundUser = userService.findOne(id);
        return foundUser.map(userEntity -> {
            UserDto userDto = userMapper.mapTo(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('USER') and @authComponent.hasPermission(#id) or hasRole('ADMIN')")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto)
    {
        if (!userService.isExists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userDto.setId(id);
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUserEntity = userService.update(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('USER') and @authComponent.hasPermission(#id) or hasRole('ADMIN')")
    public ResponseEntity deleteUser(@PathVariable("id") Long id)
    {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
