package com.sonmez.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    private Long id;

    @NotNull
    @Size(min = 10, max = 300)
    private String address;

    @NotNull
    @NotBlank
    private String district;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String country;

    private Boolean isBillingAddress;

    private Boolean isDeliveryAddress;
}
