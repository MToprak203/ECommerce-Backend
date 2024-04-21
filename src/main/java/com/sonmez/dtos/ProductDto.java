package com.sonmez.dtos;

import com.sonmez.entities.ProductImageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    private String name;

    private String description;

    private String barcode;

    private String modelCode;

    private BigDecimal price;

    private Integer stock;

    private List<ProductImageDto> images;


}
