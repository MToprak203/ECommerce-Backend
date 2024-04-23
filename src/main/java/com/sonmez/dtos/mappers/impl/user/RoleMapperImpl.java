package com.sonmez.dtos.mappers.impl.user;


import com.sonmez.dtos.mappers.Mapper;
import com.sonmez.dtos.user.RoleDto;
import com.sonmez.entities.user.role.RoleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperImpl extends Mapper<RoleEntity, RoleDto> {

    public RoleMapperImpl(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public RoleDto mapTo(RoleEntity role) {
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    public RoleEntity mapFrom(RoleDto roleDto) {
        return modelMapper.map(roleDto, RoleEntity.class);
    }
}
