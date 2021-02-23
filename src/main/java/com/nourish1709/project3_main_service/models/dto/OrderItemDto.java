package com.nourish1709.project3_main_service.models.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderItemDto implements Serializable {

    private String name;
    private int quantity;
    private BigDecimal price;

}
