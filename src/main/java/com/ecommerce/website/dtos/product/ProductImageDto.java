package com.ecommerce.website.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDto {

    private Long id;

    private String imageUrl;
}
