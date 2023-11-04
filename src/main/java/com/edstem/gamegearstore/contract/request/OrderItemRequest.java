package com.edstem.gamegearstore.contract.request;

import com.edstem.gamegearstore.contract.response.GameResponse;

public class OrderItemRequest {
    private Long id;
    private GameResponse product;
    private int quantity;

}
