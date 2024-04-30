package com.ecommerce.website.dtos.mappers.impl.product.components;

import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.dtos.product.components.BrandDto;
import com.ecommerce.website.entities.product.components.Brand;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BrandMapperImpl extends Mapper<Brand, BrandDto> {

    public BrandMapperImpl(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public BrandDto mapTo(Brand brand) {
        return modelMapper.map(brand, BrandDto.class);
    }

    @Override
    public Brand mapFrom(BrandDto brandDto) {
        return modelMapper.map(brandDto, Brand.class);
    }
}
