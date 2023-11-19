package com.edstem.gamegearstore.contract.response;

import com.edstem.gamegearstore.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutResponse {
    private String name;
    private String email;
    private String mobile;
    private String shippingAddress;
    private Cart cartItems;
    private long user;
}
