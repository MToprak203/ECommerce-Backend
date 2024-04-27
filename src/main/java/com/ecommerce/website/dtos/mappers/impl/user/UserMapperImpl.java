package com.ecommerce.website.dtos.mappers.impl.user;

import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.dtos.user.AddressDto;
import com.ecommerce.website.dtos.user.UserDto;
import com.ecommerce.website.entities.user.AddressEntity;
import com.ecommerce.website.entities.user.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapperImpl extends Mapper<UserEntity, UserDto> {

    private final Mapper<AddressEntity, AddressDto> addressMapper;

    public UserMapperImpl(ModelMapper modelMapper,
                          Mapper<AddressEntity, AddressDto> addressMapper) {
        super(modelMapper);
        this.addressMapper = addressMapper;
    }

    @Override
    public UserDto mapTo(UserEntity userEntity) {
        UserDto mapped = modelMapper.map(userEntity, UserDto.class);

        if (userEntity.getAddresses() != null)
        {
            mapped.setAddresses(userEntity
                    .getAddresses()
                    .stream()
                    .map(addressMapper::mapTo)
                    .collect(Collectors.toList())
            );
        }

        return mapped;
    }

    @Override
    public UserEntity mapFrom(UserDto userDto) {

        UserEntity mapped = modelMapper.map(userDto, UserEntity.class);

        if (userDto.getAddresses() != null)
        {
            mapped.setAddresses(userDto
                    .getAddresses()
                    .stream()
                    .map(addressMapper::mapFrom)
                    .collect(Collectors.toList())
            );

            mapped.getAddresses().forEach(a -> a.setUser(mapped));
        }

        return mapped;
    }
}
