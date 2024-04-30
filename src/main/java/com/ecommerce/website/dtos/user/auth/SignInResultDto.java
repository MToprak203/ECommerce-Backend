package com.ecommerce.website.dtos.user.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInResultDto {

    private Long id;

    private String email;

    private String token;

    private String type;

    private Set<String> roles;
}
