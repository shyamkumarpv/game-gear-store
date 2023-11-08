package com.edstem.gamegearstore.service;

import com.edstem.gamegearstore.contract.request.CartRequest;
import com.edstem.gamegearstore.contract.response.CartResponse;
import com.edstem.gamegearstore.model.Cart;
import com.edstem.gamegearstore.model.Game;
import com.edstem.gamegearstore.repository.CartRepository;
import com.edstem.gamegearstore.repository.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final GameRepository gameRepository;

    public Long createCart() {

        Cart cart = new Cart();

        Cart savedCart = cartRepository.save(cart);

        return savedCart.getId();
    }


    public CartResponse addGameToCart(Long cartId, CartRequest cartRequest) {
        Cart cart =
                cartRepository
                        .findById(cartId)
                        .orElseThrow(() -> new RuntimeException("Cart not found"));
        int newCount = cart.getCount() + cartRequest.getCount();
        cart.setCount(newCount);

        cart.setGameId(cartRequest.getGameId());

        Cart savedCart = cartRepository.save(cart);
        return modelMapper.map(savedCart, CartResponse.class);
    }

    public List<CartResponse> viewAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream()
                .map(cart -> modelMapper.map(cart, CartResponse.class))
                .collect(Collectors.toList());
    }

    public void deleteFromCart(Long id) {
        Cart cart =
                cartRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Cart not found with id " + id));

        cartRepository.delete(cart);
    }
}
