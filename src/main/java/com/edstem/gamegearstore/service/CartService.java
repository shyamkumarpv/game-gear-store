package com.edstem.gamegearstore.service;

import com.edstem.gamegearstore.contract.request.CartRequest;
import com.edstem.gamegearstore.contract.response.CartResponse;
import com.edstem.gamegearstore.model.Cart;
import com.edstem.gamegearstore.model.Game;
import com.edstem.gamegearstore.repository.CartRepository;
import com.edstem.gamegearstore.repository.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final GameRepository gameRepository;

public CartResponse createCart(Long gameId, CartRequest request) {
    Game game = gameRepository.findById(gameId)
            .orElseThrow(() -> new RuntimeException("Game not found"));

    Cart cart = new Cart();
    cart.setGameId(game.getId());
    cart.setCount(request.getCount());

    Cart savedCart = cartRepository.save(cart);

    return modelMapper.map(savedCart, CartResponse.class);
}

    public CartResponse addGameToCart(Long cartId, CartRequest cartRequest) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        int newCount = cart.getCount() + cartRequest.getCount();
        cart.setCount(newCount);

        cart.setGameId(cartRequest.getGameId());

        Cart savedCart = cartRepository.save(cart);
        return modelMapper.map(savedCart, CartResponse.class);
    }


//    public List<Cart>getAllCart() {
//    return cartRepository.findAll();
//    }

    public void deleteFromCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found with id " + id));

        cartRepository.delete(cart);

    }


}








