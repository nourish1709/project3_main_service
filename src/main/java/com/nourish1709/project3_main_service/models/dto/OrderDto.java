package com.nourish1709.project3_main_service.models.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDto implements Serializable {
    private LocalDate date;
    private Long orderNumber;
    private Long accountId;
    private List<Long> items;
    private BigDecimal orderPrice;

}
