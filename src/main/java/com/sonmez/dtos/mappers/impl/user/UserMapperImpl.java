package com.sonmez.dtos.mappers.impl.user;

import com.sonmez.dtos.mappers.Mapper;
import com.sonmez.dtos.user.AddressDto;
import com.sonmez.dtos.user.RoleDto;
import com.sonmez.dtos.user.UserDto;
import com.sonmez.entities.user.AddressEntity;
import com.sonmez.entities.user.UserEntity;
import com.sonmez.entities.user.role.RoleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapperImpl extends Mapper<UserEntity, UserDto> {

    private final Mapper<AddressEntity, AddressDto> addressMapper;
    private final Mapper<RoleEntity, RoleDto> roleMapper;

    public UserMapperImpl(ModelMapper modelMapper,
                          Mapper<AddressEntity, AddressDto> addressMapper,
                          Mapper<RoleEntity, RoleDto> roleMapper) {
        super(modelMapper);
        this.addressMapper = addressMapper;
        this.roleMapper = roleMapper;
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

        mapped.setRoles(userEntity
                .getRoles()
                .stream()
                .map(roleMapper::mapTo)
                .collect(Collectors.toList())
        );

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

        mapped.setRoles(userDto
                .getRoles()
                .stream()
                .map(roleMapper::mapFrom)
                .collect(Collectors.toList())
        );

        mapped.getRoles().forEach(r -> r.setUser(mapped));

        return mapped;
    }
}
