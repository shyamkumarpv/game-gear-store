package com.edstem.gamegearstore.controller;

import com.edstem.gamegearstore.contract.request.CheckoutRequest;
import com.edstem.gamegearstore.contract.response.CheckoutResponse;
import com.edstem.gamegearstore.model.Checkout;
import com.edstem.gamegearstore.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public CheckoutResponse createCheckout(@RequestBody CheckoutRequest checkoutRequest) {
        return checkoutService.createCheckout(checkoutRequest);
    }

    @GetMapping("/{id}")
    public CheckoutResponse getCheckout(@PathVariable Long id) {
        return checkoutService.getCheckout(id);
    }
}