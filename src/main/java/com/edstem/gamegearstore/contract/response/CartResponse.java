package com.edstem.gamegearstore.contract.response;

import com.edstem.gamegearstore.model.Game;
import java.util.List;
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
public class CartResponse {
    public Long cartId;
    private List<Game> game;
}
