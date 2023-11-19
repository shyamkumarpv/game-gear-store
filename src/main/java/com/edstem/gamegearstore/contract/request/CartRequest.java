package com.edstem.gamegearstore.contract.request;

import com.edstem.gamegearstore.model.Game;
import java.util.List;
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
public class CartRequest {
    private List<Game> game;
}
