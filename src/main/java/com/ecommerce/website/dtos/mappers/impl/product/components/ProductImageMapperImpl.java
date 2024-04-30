package com.ecommerce.website.dtos.mappers.impl.product.components;

import com.ecommerce.website.dtos.product.components.ProductImageDto;
import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.entities.product.components.ProductImage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapperImpl extends Mapper<ProductImage, ProductImageDto> {

    public ProductImageMapperImpl(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ProductImageDto mapTo(ProductImage productImage) {
        return modelMapper.map(productImage, ProductImageDto.class);
    }

    @Override
    public ProductImage mapFrom(ProductImageDto productImageDto) {
        return modelMapper.map(productImageDto, ProductImage.class);
    }
}
