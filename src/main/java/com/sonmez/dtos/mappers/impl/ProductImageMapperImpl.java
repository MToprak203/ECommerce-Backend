package com.sonmez.dtos.mappers.impl;

import com.sonmez.dtos.ProductImageDto;
import com.sonmez.dtos.mappers.Mapper;
import com.sonmez.entities.ProductImageEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapperImpl extends Mapper<ProductImageEntity, ProductImageDto> {

    public ProductImageMapperImpl(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ProductImageDto mapTo(ProductImageEntity productImageEntity) {
        return modelMapper.map(productImageEntity, ProductImageDto.class);
    }

    @Override
    public ProductImageEntity mapFrom(ProductImageDto productImageDto) {
        return modelMapper.map(productImageDto, ProductImageEntity.class);
    }
}
