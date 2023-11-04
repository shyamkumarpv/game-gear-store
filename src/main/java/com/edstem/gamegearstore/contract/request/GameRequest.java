package com.edstem.gamegearstore.contract.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GameRequest {
    private String name;
    private String description;
    private String review;
    private BigDecimal price;
}

