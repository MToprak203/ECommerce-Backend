package com.sonmez.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductMetadataDto {
    private Long id;

    private String name;

    private String thumbnailUrl;

    private BigDecimal price;

    private Integer stock;
}
