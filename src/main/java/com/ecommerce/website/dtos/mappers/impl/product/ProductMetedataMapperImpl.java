package com.ecommerce.website.dtos.mappers.impl.product;

import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.dtos.product.ProductMetadataDto;
import com.ecommerce.website.entities.product.ProductEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMetedataMapperImpl extends Mapper<ProductEntity, ProductMetadataDto> {
    public ProductMetedataMapperImpl(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ProductMetadataDto mapTo(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductMetadataDto.class);
    }

    @Override
    public ProductEntity mapFrom(ProductMetadataDto productMetadataDto) {
        return modelMapper.map(productMetadataDto, ProductEntity.class);
    }
}
