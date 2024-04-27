package com.ecommerce.website.controllers;


import com.ecommerce.website.UserLoginUtil;
import com.ecommerce.website.dtos.user.SignInResultDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ecommerce.website.TestDataUtil;
import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.dtos.product.ProductDto;
import com.ecommerce.website.entities.product.ProductEntity;
import com.ecommerce.website.entities.product.ProductFAQEntity;
import com.ecommerce.website.entities.product.ProductImageEntity;
import com.ecommerce.website.services.product.ProductService;
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

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Mapper<ProductEntity, ProductDto> productMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserLoginUtil userLoginUtil;


    @Test
    public void createProductTest() throws Exception {
        ProductEntity product = TestDataUtil.createTestProduct();
        ProductImageEntity image = TestDataUtil.createTestProductImage();
        ProductFAQEntity faq = TestDataUtil.createTestProductFAQ();
        product.getImages().add(image);
        product.getFaqs().add(faq);

        ProductDto productDto = productMapper.mapTo(product);
        String productDtoJson = objectMapper.writeValueAsString(productDto);


        // Authorization is required
        mockMvc.perform(
                MockMvcRequestBuilders.post("/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isUnauthorized()
        );

        SignInResultDto userSignIn = userLoginUtil.TestUserLogin();

        // User can not add product
        mockMvc.perform(
                MockMvcRequestBuilders.post("/admin/products")
                        .header("Authorization",
                                userSignIn.getType() + " " + userSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );

        SignInResultDto adminSignIn = userLoginUtil.TestAdminLogin();

        // Admin can add product
        mockMvc.perform(
                MockMvcRequestBuilders.post("/admin/products")
                        .header("Authorization",
                                adminSignIn.getType() + " " + adminSignIn.getToken())
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

        // cant add a product more than one time
        mockMvc.perform(
                MockMvcRequestBuilders.post("/admin/products")
                        .header("Authorization",
                                adminSignIn.getType() + " " + adminSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isConflict()
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

        // Everyone can access the products

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

        // Everyone can access a product detail
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
        product.setId(null);
        productService.create(product);

        ProductDto productDto = productMapper.mapTo(product);
        productDto.setName("test");
        String productDtoJson = objectMapper.writeValueAsString(productDto);

        // Authorization is required
        mockMvc.perform(
                MockMvcRequestBuilders.put("/admin/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isUnauthorized()
        );

        SignInResultDto userSignIn = userLoginUtil.TestUserLogin();

        // User can not update a product
        mockMvc.perform(
                MockMvcRequestBuilders.put("/admin/products/" + product.getId())
                        .header("Authorization",
                                userSignIn.getType() + " " + userSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );

        SignInResultDto adminSignIn = userLoginUtil.TestAdminLogin();

        // Admin can update a product
        mockMvc.perform(
                MockMvcRequestBuilders.put("/admin/products/" + product.getId())
                        .header("Authorization",
                                adminSignIn.getType() + " " + adminSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("test")
        );

        mockMvc.perform(
                MockMvcRequestBuilders.put("/admin/products/" + 99)
                        .header("Authorization",
                                adminSignIn.getType() + " " + adminSignIn.getToken())
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

        // Authorization is required
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/admin/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isUnauthorized()
        );

        SignInResultDto userSignIn = userLoginUtil.TestUserLogin();

        // User can not update a product
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/admin/products/" + product.getId())
                        .header("Authorization",
                                userSignIn.getType() + " " + userSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );

        SignInResultDto adminSignIn = userLoginUtil.TestAdminLogin();

        // Admin can update a product

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/admin/products/" + product.getId())
                        .header("Authorization",
                                adminSignIn.getType() + " " + adminSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/admin/products/" + 99)
                        .header("Authorization",
                                adminSignIn.getType() + " " + adminSignIn.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }


}
