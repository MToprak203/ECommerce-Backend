package com.ecommerce.website.dtos.mappers.impl.user;

import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.dtos.user.components.AddressDto;
import com.ecommerce.website.dtos.user.UserDto;
import com.ecommerce.website.entities.user.Address;
import com.ecommerce.website.entities.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapperImpl extends Mapper<User, UserDto> {

    private final Mapper<Address, AddressDto> addressMapper;

    public UserMapperImpl(ModelMapper modelMapper,
                          Mapper<Address, AddressDto> addressMapper) {
        super(modelMapper);
        this.addressMapper = addressMapper;
    }

    @Override
    public UserDto mapTo(User user) {
        UserDto mapped = modelMapper.map(user, UserDto.class);

        if (user.getAddresses() != null)
        {
            mapped.setAddresses(user
                    .getAddresses()
                    .stream()
                    .map(addressMapper::mapTo)
                    .collect(Collectors.toList())
            );
        }

        return mapped;
    }

    @Override
    public User mapFrom(UserDto userDto) {

        User mapped = modelMapper.map(userDto, User.class);

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
