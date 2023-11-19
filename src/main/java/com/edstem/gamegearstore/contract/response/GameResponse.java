package com.edstem.gamegearstore.contract.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameResponse {
    private Long gameId;
    private String name;
    private String description;
    private BigDecimal price;
    private Long count;
}
