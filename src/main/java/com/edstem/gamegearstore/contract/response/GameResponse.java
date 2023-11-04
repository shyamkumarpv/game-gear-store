package com.edstem.gamegearstore.contract.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String review;
}
