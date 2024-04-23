package com.sonmez.dtos.mappers.impl.product;

import com.sonmez.dtos.product.ProductImageDto;
import com.sonmez.dtos.mappers.Mapper;
import com.sonmez.entities.product.ProductImageEntity;
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
