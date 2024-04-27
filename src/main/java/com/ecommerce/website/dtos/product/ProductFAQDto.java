package com.ecommerce.website.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductFAQDto {
    private Long id;

    private String question;

    private String answer;
}
