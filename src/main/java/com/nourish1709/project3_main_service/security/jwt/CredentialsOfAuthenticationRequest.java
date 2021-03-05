package com.nourish1709.project3_main_service.security.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredentialsOfAuthenticationRequest {
    private String username;
    private String password;
}
