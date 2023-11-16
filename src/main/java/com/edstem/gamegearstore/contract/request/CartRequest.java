package com.edstem.gamegearstore.contract.request;

import com.edstem.gamegearstore.model.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
    private List<Game> game;
}
