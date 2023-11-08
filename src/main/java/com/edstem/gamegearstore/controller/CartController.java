package com.edstem.gamegearstore.controller;

import com.edstem.gamegearstore.contract.request.CartRequest;
import com.edstem.gamegearstore.contract.response.CartResponse;
import com.edstem.gamegearstore.model.Cart;
import com.edstem.gamegearstore.repository.CartRepository;
import com.edstem.gamegearstore.service.CartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;

    @PostMapping("/create")
    public Long createCart() {
        Cart cart = new Cart();
        Cart savedCart = cartRepository.save(cart);
        return savedCart.getId();
    }


    @PostMapping("/{cartId}/addGame")
    public ResponseEntity<CartResponse> addGameToCart(
            @PathVariable Long cartId, @RequestBody CartRequest cartRequest) {
        CartResponse cartResponse = cartService.addGameToCart(cartId, cartRequest);
        return ResponseEntity.ok(cartResponse);
    }

    @GetMapping("/all")
    public List<CartResponse> viewAllCarts() {
        return cartService.viewAllCarts();
    }

    @DeleteMapping("/{id}")
    public void deleteFromCart(@PathVariable Long id) {
        cartService.deleteFromCart(id);
    }

    @GetMapping("/login")
    public String login() {
        return "login successfull";
    }
}
