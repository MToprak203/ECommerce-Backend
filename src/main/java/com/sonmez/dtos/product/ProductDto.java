package com.sonmez.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Product name can not be empty.")
    @NotBlank(message = "Product name can not be empty.")
    @Size(max = 150, message = "Product name can not be longer than 150 characters")
    private String name;

    @NotNull(message = "Product description can not be empty.")
    @NotBlank(message = "Product description can not be empty.")
    private String description;

    private String barcode;

    private String modelCode;

    @PositiveOrZero(message = "Product price can not be negative.")
    private BigDecimal price;

    @PositiveOrZero(message = "Product stock can not be negative.")
    private Integer stock;

    private List<ProductImageDto> images;

    private List<ProductFAQDto> faqs;
}
