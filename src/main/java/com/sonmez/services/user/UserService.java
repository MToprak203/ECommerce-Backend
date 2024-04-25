package com.sonmez.services.user;

import com.sonmez.dtos.user.SignInResultDto;
import com.sonmez.dtos.user.UserLoginDto;
import com.sonmez.entities.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    UserEntity register(UserEntity entity);

    SignInResultDto login(UserLoginDto userLoginDto);

    UserEntity update(UserEntity entity);

    Page<UserEntity> findAll(Pageable pageable);

    Optional<UserEntity> findOne(Long id);

    boolean isExists(Long id);

    void delete(Long id);


}
