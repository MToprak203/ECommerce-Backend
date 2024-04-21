package com.sonmez.dtos.mappers.impl;

import com.sonmez.dtos.ProductDto;
import com.sonmez.dtos.ProductImageDto;
import com.sonmez.dtos.mappers.Mapper;
import com.sonmez.entities.ProductEntity;
import com.sonmez.entities.ProductImageEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductMapperImpl extends Mapper<ProductEntity, ProductDto> {

    private final Mapper<ProductImageEntity, ProductImageDto> productImageMapper;

    public ProductMapperImpl(ModelMapper modelMapper,
                             Mapper<ProductImageEntity, ProductImageDto> productImageMapper)
    {
        super(modelMapper);
        this.productImageMapper = productImageMapper;
    }

    @Override
    public ProductDto mapTo(ProductEntity productEntity) {
        ProductDto mapped = modelMapper.map(productEntity, ProductDto.class);

        if (mapped.getImages() != null)
        {
            mapped.setImages(productEntity
                    .getImages()
                    .stream()
                    .map(productImageMapper::mapTo)
                    .collect(Collectors.toList())
            );
        }

        return mapped;
    }

    @Override
    public ProductEntity mapFrom(ProductDto productDto) {
        ProductEntity mapped = modelMapper.map(productDto, ProductEntity.class);

        if (mapped.getImages() != null)
        {
            mapped.setImages(productDto
                    .getImages()
                    .stream()
                    .map(productImageMapper::mapFrom)
                    .collect(Collectors.toList())
            );

            mapped.getImages().forEach((i) -> i.setProduct(mapped));
        }

        return mapped;
    }
}
