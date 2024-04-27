package com.ecommerce.website.dtos.mappers.impl.product;

import com.ecommerce.website.dtos.product.ProductImageDto;
import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.entities.product.ProductImageEntity;
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
