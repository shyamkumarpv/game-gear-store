package com.edstem.gamegearstore.contract.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameRequest {
    private String name;
    private String description;
    private String review;
    private BigDecimal price;
}
