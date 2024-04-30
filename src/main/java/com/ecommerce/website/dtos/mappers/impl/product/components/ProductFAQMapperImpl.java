package com.ecommerce.website.dtos.mappers.impl.product.components;

import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.dtos.product.components.ProductFAQDto;
import com.ecommerce.website.entities.product.components.ProductFAQ;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductFAQMapperImpl extends Mapper<ProductFAQ, ProductFAQDto> {


    public ProductFAQMapperImpl(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ProductFAQDto mapTo(ProductFAQ productFAQ) {
        return modelMapper.map(productFAQ,ProductFAQDto.class);
    }

    @Override
    public ProductFAQ mapFrom(ProductFAQDto productFAQDto) {
        return modelMapper.map(productFAQDto, ProductFAQ.class);
    }
}
