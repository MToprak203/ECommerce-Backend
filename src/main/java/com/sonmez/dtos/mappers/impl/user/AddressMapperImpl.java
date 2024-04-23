package com.sonmez.dtos.mappers.impl.user;

import com.sonmez.dtos.mappers.Mapper;
import com.sonmez.dtos.user.AddressDto;
import com.sonmez.entities.user.AddressEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapperImpl extends Mapper<AddressEntity, AddressDto> {

    public AddressMapperImpl(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public AddressDto mapTo(AddressEntity addressEntity) {
        return modelMapper.map(addressEntity, AddressDto.class);
    }

    @Override
    public AddressEntity mapFrom(AddressDto addressDto) {
        return modelMapper.map(addressDto, AddressEntity.class);
    }
}
