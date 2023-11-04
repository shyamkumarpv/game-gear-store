package com.edstem.gamegearstore.contract.request;

import java.util.List;

public class OrderRequest {
        private String customerName;
        private String customerEmail;
        private String shippingAddress;
        private List<OrderItemRequest> orderItems;
}
