package com.sonmez.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonmez.TestDataUtil;
import com.sonmez.dtos.ProductDto;
import com.sonmez.entities.ProductEntity;
import com.sonmez.entities.ProductImageEntity;
import com.sonmez.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ProductControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final ProductService productService;


    @Autowired
    public ProductControllerIntegrationTests(MockMvc mockMvc, ProductService productService)
    {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.productService = productService;
    }

    @Test
    public void testThatCreateProductSuccessfullyReturnsHttp201Created() throws Exception {
        ProductEntity product1 = TestDataUtil.createTestProduct1();
        product1.setId(null);
        String productJson = objectMapper.writeValueAsString(product1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatingProductAlsoCreatesProductImages()
    {
        ProductEntity productEntity1 = TestDataUtil.createTestProduct1();
        ProductImageEntity productImageEntity1 = TestDataUtil.createTestProductImage1();
        ProductImageEntity productImageEntity2 = TestDataUtil.createTestProductImage2();

        List<ProductImageEntity> images = new ArrayList<>();
        images.add(productImageEntity1);
        images.add(productImageEntity2);

        productEntity1.setImages(images);
        ProductEntity createdProduct = productService.save(productEntity1);

        assertThat(createdProduct.getImages().size()).isEqualTo(2);
    }

    @Test
    public void testThatListProductsReturnHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListProductsReturnsListOfAuthors() throws Exception {
        ProductEntity productEntity1 = TestDataUtil.createTestProduct1();
        productService.save(productEntity1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].name").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].description").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].barcode").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].modelCode").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].price").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].images").isArray()
        );
    }

    @Test
    public void testThatProductReturnsHttpStatus200WhenProductExists() throws Exception {
        ProductEntity productEntity1 = TestDataUtil.createTestProduct1();
        productService.save(productEntity1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/" + productEntity1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatProductReturnsHttpStatus404WhenProductNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/" + 99)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetProductReturnsProductWhenProductExistsWithoutImages() throws Exception {
        ProductEntity productEntity1 = TestDataUtil.createTestProduct1();
        productService.save(productEntity1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/" + productEntity1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("product1")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value("product1 description")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.barcode").value("product1 barcode")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.modelCode").value("product1 modelcode")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal.valueOf(1000.15).setScale(2))
        );
    }

    @Test
    public void testThatGetProductReturnsProductImagesWhenProductExists() throws Exception {
        ProductEntity productEntity1 = TestDataUtil.createTestProduct1();

        ProductImageEntity productImageEntity1 = TestDataUtil.createTestProductImage1();
        productImageEntity1.setProduct(productEntity1);

        ProductImageEntity productImageEntity2 = TestDataUtil.createTestProductImage2();
        productImageEntity2.setProduct(productEntity1);

        List<ProductImageEntity> images = new ArrayList<>();
        images.add(productImageEntity1);
        images.add(productImageEntity2);

        productEntity1.setImages(images);
        productService.save(productEntity1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/" + productEntity1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.images.length()").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.images.[0].id").value(productImageEntity1.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.images.[0].imageUrl").value(productImageEntity1.getImageUrl())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.images.[1].imageUrl").value(productImageEntity2.getImageUrl())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.images.[1].id").value(productImageEntity2.getId())
        );
    }

    @Test
    public void testThatProductReturnsHttpStatus200WhenProductUpdatedSuccessfully() throws Exception {
        ProductEntity productEntity1 = TestDataUtil.createTestProduct1();
        ProductEntity savedProduct = productService.save(productEntity1);

        ProductDto productDto1 = TestDataUtil.createTestProductDto1();
        String productDtoJson = objectMapper.writeValueAsString(productDto1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/products/" + savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatProductReturnsHttpStatus400WhenProductNotFoundWhileUpdating() throws Exception {
        ProductEntity productEntity1 = TestDataUtil.createTestProduct1();
        productService.save(productEntity1);

        ProductDto productDto1 = TestDataUtil.createTestProductDto1();
        String productDtoJson = objectMapper.writeValueAsString(productDto1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/products/" + 99)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatProductDropsImagesIfTheyUpdatedToRemove() throws Exception {
        ProductEntity productEntity1 = TestDataUtil.createTestProduct1();
        ProductImageEntity productImageEntity1 = TestDataUtil.createTestProductImage1();

        List<ProductImageEntity> images = new ArrayList<>();
        images.add(productImageEntity1);

        productEntity1.setImages(images);
        productService.save(productEntity1);

        ProductDto productDto1 = TestDataUtil.createTestProductDto1();
        productDto1.setId(productEntity1.getId());
        String productDtoJson = objectMapper.writeValueAsString(productDto1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/products/" + productDto1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.images.length()").value(0)
        );
    }

    @Test
    public void testThatDeleteProductReturnsHttpStatus204() throws Exception {

        ProductEntity productEntity1 = TestDataUtil.createTestProduct1();
        productService.save(productEntity1);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/products/" + productEntity1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatPageReturnsCorrectAmountOfProducts() throws Exception {
        for(int i = 0; i < 50; i++)
        {
            ProductEntity productEntity1 = TestDataUtil.createTestProduct1();
            productEntity1.setId(null);
            productService.save(productEntity1);
        }

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(
                                MockMvcResultMatchers.status().isOk()
                        ).andExpect(
                                MockMvcResultMatchers.jsonPath("$.content").isArray()
                        ).andExpect(
                                MockMvcResultMatchers.jsonPath("$.content.length()").value(10)
                );
    }

    @Test
    public void testThatPatchReturnsHttpStatus404IfProductNotFound() throws Exception {
        ProductDto productDto1 = TestDataUtil.createTestProductDto1();
        String productDtoJson = objectMapper.writeValueAsString(productDto1);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/products/" + 99)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}
