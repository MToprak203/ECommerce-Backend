package com.sonmez.dtos.mappers.impl.product;

import com.sonmez.dtos.mappers.Mapper;
import com.sonmez.dtos.product.ProductFAQDto;
import com.sonmez.entities.product.ProductFAQEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductFAQMapperImpl extends Mapper<ProductFAQEntity, ProductFAQDto> {


    public ProductFAQMapperImpl(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ProductFAQDto mapTo(ProductFAQEntity productFAQEntity) {
        return modelMapper.map(productFAQEntity,ProductFAQDto.class);
    }

    @Override
    public ProductFAQEntity mapFrom(ProductFAQDto productFAQDto) {
        return modelMapper.map(productFAQDto, ProductFAQEntity.class);
    }
}
