package com.ecommerce.website.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductFilter {
    private String searchTerm;
    private String category;
    private Double minPrice;
    private Double maxPrice;
    private String brand;
    private int page;
    private int size;
    private String sortField;
    private String sortDirection;
}
