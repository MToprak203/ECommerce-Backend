package com.ecommerce.website.dtos.product;

import com.ecommerce.website.dtos.product.components.BrandDto;
import com.ecommerce.website.dtos.product.components.ProductImageDto;
import com.ecommerce.website.entities.product.components.Category;
import com.ecommerce.website.entities.product.components.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductMetadataDto {
    private Long id;

    private String name;

    private ProductImageDto thumbnail;

    private BrandDto brand;

    private Set<Category> categories;

    private BigDecimal price;

    private Integer stock;
}
