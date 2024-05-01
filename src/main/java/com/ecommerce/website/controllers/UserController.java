package com.ecommerce.website.controllers;

import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.dtos.user.auth.SignInResultDto;
import com.ecommerce.website.dtos.user.UserDto;
import com.ecommerce.website.dtos.user.auth.UserLoginDto;
import com.ecommerce.website.dtos.user.auth.UserRegisterDto;
import com.ecommerce.website.entities.user.User;
import com.ecommerce.website.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;
    @Autowired
    private Mapper<User, UserDto> userMapper;
    @Autowired
    private Mapper<User, UserRegisterDto> userRegisterMapper;

    @PostMapping("/public/auth/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        User user = userRegisterMapper.mapFrom(userRegisterDto);
        User savedUserEntity = userService.register(user);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.CREATED);
    }

    @PostMapping("/public/auth/login")
    public ResponseEntity<SignInResultDto> loginUser(@Valid @RequestBody UserLoginDto userLoginDto) {
        SignInResultDto resultDto = userService.login(userLoginDto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @GetMapping("/public/users")
    public ResponseEntity<Page<UserDto>> listUsers(Pageable pageable) {
        Page<User> userEntities = userService.findAll(pageable);
        return new ResponseEntity<>(userEntities.map(userMapper::mapTo), HttpStatus.OK);
    }

    @GetMapping("/public/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        Optional<User> foundUser = userService.findOne(id);
        return foundUser.map(userEntity -> {
            UserDto userDto = userMapper.mapTo(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/private/users/{id}")
    @PreAuthorize("@authComponent.hasPermissionForEditUsers(#id)")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto) {
        if (!userService.isExists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userDto.setId(id);
        User user = userMapper.mapFrom(userDto);
        User savedUserEntity = userService.update(user);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.OK);
    }

    @DeleteMapping("/private/users/{id}")
    @PreAuthorize("@authComponent.hasPermissionForEditUsers(#id)")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
