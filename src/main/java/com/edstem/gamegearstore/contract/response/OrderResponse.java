package com.edstem.gamegearstore.contract.response;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponse {
        private Long id;
        private String customerName;
        private String customerEmail;
        private String shippingAddress;
        private List<OrderItemResponse> orderItems;
        private BigDecimal total;
}
