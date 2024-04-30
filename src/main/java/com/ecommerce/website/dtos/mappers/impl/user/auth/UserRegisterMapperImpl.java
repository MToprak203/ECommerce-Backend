package com.ecommerce.website.dtos.mappers.impl.user.auth;

import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.dtos.user.auth.UserRegisterDto;
import com.ecommerce.website.entities.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterMapperImpl extends Mapper<User, UserRegisterDto> {
    public UserRegisterMapperImpl(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public UserRegisterDto mapTo(User user) {
        return modelMapper.map(user, UserRegisterDto.class);
    }

    @Override
    public User mapFrom(UserRegisterDto userRegisterDto) {
        return modelMapper.map(userRegisterDto, User.class);
    }
}
