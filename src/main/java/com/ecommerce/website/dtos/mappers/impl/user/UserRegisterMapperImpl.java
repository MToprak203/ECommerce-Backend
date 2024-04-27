package com.ecommerce.website.dtos.mappers.impl.user;

import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.dtos.user.UserRegisterDto;
import com.ecommerce.website.entities.user.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterMapperImpl extends Mapper<UserEntity, UserRegisterDto> {
    public UserRegisterMapperImpl(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public UserRegisterDto mapTo(UserEntity user) {
        return modelMapper.map(user, UserRegisterDto.class);
    }

    @Override
    public UserEntity mapFrom(UserRegisterDto userRegisterDto) {
        return modelMapper.map(userRegisterDto, UserEntity.class);
    }
}
