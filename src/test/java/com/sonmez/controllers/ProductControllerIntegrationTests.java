package com.sonmez.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonmez.TestDataUtil;
import com.sonmez.dtos.mappers.Mapper;
import com.sonmez.dtos.product.ProductDto;
import com.sonmez.entities.product.ProductEntity;
import com.sonmez.entities.product.ProductFAQEntity;
import com.sonmez.entities.product.ProductImageEntity;
import com.sonmez.services.product.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ProductControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final Mapper<ProductEntity, ProductDto> productMapper;
    private final ProductService productService;


    @Autowired
    public ProductControllerIntegrationTests(MockMvc mockMvc,
                                             ProductService productService,
                                             Mapper<ProductEntity, ProductDto> productMapper)
    {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @Test
    public void createProductTest() throws Exception {
        ProductEntity product = TestDataUtil.createTestProduct();
        ProductImageEntity image = TestDataUtil.createTestProductImage();
        ProductFAQEntity faq = TestDataUtil.createTestProductFAQ();
        product.getImages().add(image);
        product.getFaqs().add(faq);

        ProductDto productDto = productMapper.mapTo(product);
        String productDtoJson = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(product.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(product.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value(product.getDescription())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.thumbnailUrl").value(product.getThumbnailUrl())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.barcode").value(product.getBarcode())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.modelCode").value(product.getModelCode())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.price").value(product.getPrice())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.stock").value(product.getStock())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.images[0].id").value(product.getImages().get(0).getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.images[0].imageUrl").value(product.getImages().get(0).getImageUrl())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.faqs[0].id").value(product.getFaqs().get(0).getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.faqs[0].question").value(product.getFaqs().get(0).getQuestion())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.faqs[0].answer").value(product.getFaqs().get(0).getAnswer())
        );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errorMessage").isString()
        );
    }

    @Test
    public void listProductsTest() throws Exception {
        for (int i = 0; i < 5; i++)
        {
            ProductEntity product = TestDataUtil.createTestProduct();
            product.setId(null);
            product.setBarcode(String.valueOf(i));
            productService.create(product);
        }

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .param("page", "0")
                        .param("size", "4")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content.length()").value(4)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].name").value("product")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].thumbnailUrl").value("product thumbnailUrl")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].price").value(BigDecimal.valueOf(1000.15).setScale(2))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].stock").value(10)
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .param("page", "1")
                        .param("size", "4")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content.length()").value(1)
        );
    }

    @Test
    public void getProductTest() throws Exception {
        ProductEntity product = TestDataUtil.createTestProduct();
        ProductImageEntity image = TestDataUtil.createTestProductImage();
        image.setProduct(product);
        ProductFAQEntity faq = TestDataUtil.createTestProductFAQ();
        faq.setProduct(product);
        product.getImages().add(image);
        product.getFaqs().add(faq);
        productService.create(product);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(product.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(product.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value(product.getDescription())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.thumbnailUrl").value(product.getThumbnailUrl())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.barcode").value(product.getBarcode())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.modelCode").value(product.getModelCode())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.price").value(product.getPrice())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.stock").value(product.getStock())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.images[0].id").value(product.getImages().get(0).getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.images[0].imageUrl").value(product.getImages().get(0).getImageUrl())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.faqs[0].id").value(product.getFaqs().get(0).getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.faqs[0].question").value(product.getFaqs().get(0).getQuestion())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.faqs[0].answer").value(product.getFaqs().get(0).getAnswer())
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/" + 99)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void updateProductTest() throws Exception {
        ProductEntity product = TestDataUtil.createTestProduct();
        productService.create(product);

        ProductDto productDto = productMapper.mapTo(product);
        productDto.setName("test");
        String productDtoJson = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("test")
        );

        mockMvc.perform(
                MockMvcRequestBuilders.put("/products/" + 99)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void deleteProductTest() throws Exception {
        ProductEntity product = TestDataUtil.createTestProduct();
        productService.create(product);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/products/" + 99)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }


}
