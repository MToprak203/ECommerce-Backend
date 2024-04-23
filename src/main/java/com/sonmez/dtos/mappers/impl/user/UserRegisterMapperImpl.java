package com.sonmez.dtos.mappers.impl.user;

import com.sonmez.dtos.mappers.Mapper;
import com.sonmez.dtos.user.UserRegisterDto;
import com.sonmez.entities.user.UserEntity;
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
