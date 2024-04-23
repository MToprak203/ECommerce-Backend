package com.sonmez.dtos.mappers.impl.product;

import com.sonmez.dtos.product.ProductDto;
import com.sonmez.dtos.product.ProductImageDto;
import com.sonmez.dtos.mappers.Mapper;
import com.sonmez.entities.product.ProductEntity;
import com.sonmez.entities.product.ProductImageEntity;
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

        if (productEntity.getImages() != null)
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

        if (productDto.getImages() != null)
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
