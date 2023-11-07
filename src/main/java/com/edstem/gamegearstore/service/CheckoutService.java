package com.edstem.gamegearstore.service;

import com.edstem.gamegearstore.contract.request.CheckoutRequest;
import com.edstem.gamegearstore.contract.response.CheckoutResponse;
import com.edstem.gamegearstore.model.Checkout;
import com.edstem.gamegearstore.repository.CartRepository;
import com.edstem.gamegearstore.repository.CheckoutRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

   @Service
   @RequiredArgsConstructor
   public class CheckoutService {

        private final CheckoutRepository checkoutRepository;
        private final ModelMapper modelMapper;
        private final CartRepository cartRepository;

           public CheckoutResponse createCheckout(CheckoutRequest checkoutRequest) {
               Checkout checkout = modelMapper.map(checkoutRequest, Checkout.class);
               Checkout savedCheckout = checkoutRepository.save(checkout);
               CheckoutResponse checkoutResponse = modelMapper.map(savedCheckout, CheckoutResponse.class);
               return checkoutResponse;
           }

                  public CheckoutResponse getCheckout(Long id)
                  {Checkout checkout = checkoutRepository.findById(id).orElseThrow(() ->
                       new RuntimeException("Checkout not found with id " + id));
                   CheckoutResponse checkoutResponse = modelMapper.map(checkout, CheckoutResponse.class);

                   return checkoutResponse;
               }

           }
