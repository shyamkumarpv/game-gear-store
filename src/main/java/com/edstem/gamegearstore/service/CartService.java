package com.edstem.gamegearstore.service;

import com.edstem.gamegearstore.model.CartItem;
import com.edstem.gamegearstore.repository.CartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CartService {
    private final CartRepository cartRepository;
    private List<CartItem> cartItem = new ArrayList<>();
}
    public void addCartItem(Long gameId, int quantity) {
        CartItem existingCartItem = findCartItemByGameId(gameId);
