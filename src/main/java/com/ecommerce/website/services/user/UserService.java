package com.ecommerce.website.services.user;

import com.ecommerce.website.dtos.user.auth.SignInResultDto;
import com.ecommerce.website.dtos.user.auth.UserLoginDto;
import com.ecommerce.website.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    User register(User entity);

    SignInResultDto login(UserLoginDto userLoginDto);

    User update(User entity);

    Page<User> findAll(Pageable pageable);

    Optional<User> findOne(Long id);

    boolean isExists(Long id);

    void delete(Long id);


}
