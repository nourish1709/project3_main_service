package com.nourish1709.project3_main_service.models.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SkuDto implements Serializable {
    private String name;
    private BigDecimal price;
    private String description;
    private boolean availability;
    private Long categoryId;
    private Long accountId;
}
