package com.nourish1709.project3_main_service.models.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDto implements Serializable {

    private String name;
    private String description;
    private String image;
    private Long accountId;
}
