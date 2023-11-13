package com.edstem.gamegearstore.contract.request;

import com.edstem.gamegearstore.model.Cart;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {
    private String name;
    private String shippingAddress;
    private String mobileNumber;
    private List<Cart> cartItems;
}
