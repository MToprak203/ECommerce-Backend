package com.ecommerce.website.dtos.mappers.impl.user;

import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.dtos.user.UserMetadataDto;
import com.ecommerce.website.entities.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMetadataMapperImpl extends Mapper<User, UserMetadataDto> {
    public UserMetadataMapperImpl(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public UserMetadataDto mapTo(User user) {
        return modelMapper.map(user, UserMetadataDto.class);
    }

    @Override
    public User mapFrom(UserMetadataDto userMetadataDto) {
        return modelMapper.map(userMetadataDto, User.class);
    }
}
