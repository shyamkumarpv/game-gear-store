package com.edstem.gamegearstore.contract.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponse {
    private String name;
    private String shippingAddress;
    private String mobileNumber;
    private List<CartResponse> cartItems;
}
