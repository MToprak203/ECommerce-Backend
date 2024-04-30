package com.ecommerce.website.dtos.mappers.impl.user.components;

import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.dtos.user.components.AddressDto;
import com.ecommerce.website.entities.user.Address;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapperImpl extends Mapper<Address, AddressDto> {

    public AddressMapperImpl(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public AddressDto mapTo(Address address) {
        return modelMapper.map(address, AddressDto.class);
    }

    @Override
    public Address mapFrom(AddressDto addressDto) {
        return modelMapper.map(addressDto, Address.class);
    }
}
