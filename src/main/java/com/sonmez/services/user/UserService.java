package com.sonmez.services.user;

import com.sonmez.dtos.user.UserLoginDto;
import com.sonmez.entities.user.UserEntity;
import com.sonmez.services.CrudService;

import java.util.Optional;

public interface UserService extends CrudService<UserEntity> {
    Optional<UserEntity> login(UserLoginDto userLoginDto);
}
