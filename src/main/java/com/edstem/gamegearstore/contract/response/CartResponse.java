package com.edstem.gamegearstore.contract.response;

import com.edstem.gamegearstore.model.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private Game game;
    private long count;
}
