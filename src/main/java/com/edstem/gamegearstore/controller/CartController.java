package com.edstem.gamegearstore.controller;

import com.edstem.gamegearstore.contract.response.CartResponse;
import com.edstem.gamegearstore.contract.response.GameResponse;
import com.edstem.gamegearstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<GameResponse>> viewCart(@RequestParam Long userId) {
        List<GameResponse> gameResponses = cartService.viewCart(userId);
        return ResponseEntity.ok(gameResponses);
    }

    @PostMapping("/{gameId}/create")
    public ResponseEntity<CartResponse> addGameToCart(
            @PathVariable Long gameId, @RequestParam Long userId) {
        CartResponse response = cartService.addGameToCart(gameId, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<String> removeGameFromCart(
            @PathVariable Long gameId, @RequestParam Long userId) {
        try {
            cartService.removeGameFromCart(gameId, userId);
            return ResponseEntity.ok("Game successfully removed from the cart.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found in the cart.");
        }
    }
}
