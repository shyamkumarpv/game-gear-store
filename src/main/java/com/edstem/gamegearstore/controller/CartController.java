package com.edstem.gamegearstore.controller;

import com.edstem.gamegearstore.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    @PostMapping("/add")
    public ResponseEntity<String> addCartItem(@RequestParam Long gameId, @RequestParam int quantity) {
        CartItem existingCartItem = findCartItemByGameId(gameId);
        return ResponseEntity.ok("Game added to the cart.");
    }

}