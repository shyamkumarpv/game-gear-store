package com.edstem.gamegearstore.controller;

import com.edstem.gamegearstore.contract.response.CartResponse;
import com.edstem.gamegearstore.contract.response.GameResponse;
import com.edstem.gamegearstore.model.Cart;
import com.edstem.gamegearstore.model.User;
import com.edstem.gamegearstore.repository.CartRepository;
import com.edstem.gamegearstore.service.CartService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;

    @GetMapping
    List<GameResponse> viewCart(Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        Cart cart = cartRepository.findByUserId(userId);
        return cart.getCartItems().stream()
                .map(cartItem -> modelMapper.map(cartItem.getGame(), GameResponse.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/{gameId}/create")
    public ResponseEntity<CartResponse> addGameToCart(
            @PathVariable Long gameId, Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        CartResponse response = cartService.addGameToCart(gameId, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<String> removeGameFromCart(
            @PathVariable Long gameId, Authentication authentication) {
        try {
            Long userId = ((User) authentication.getPrincipal()).getId();
            cartService.removeGameFromCart(gameId, userId);
            return ResponseEntity.ok("Game successfully removed from the cart.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found in the cart.");
        }
    }

    @GetMapping("/items")
    public ResponseEntity<Integer> getNumberOfItemsInCart(Authentication authentication) {
        try {
            Long userId = ((User) authentication.getPrincipal()).getId();
            int numberOfItems = cartService.getNumberOfItemsInCart(userId);
            return ResponseEntity.ok(numberOfItems);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }
}
