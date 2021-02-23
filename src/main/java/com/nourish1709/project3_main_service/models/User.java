package com.nourish1709.project3_main_service.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable
@Data
@NoArgsConstructor
public class User {
    private String username;
    private String email;
    private String password;
}
