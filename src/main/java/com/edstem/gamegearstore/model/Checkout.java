package com.edstem.gamegearstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class Checkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String shippingAddress;
    private String mobileNumber;

    //    @OneToMany
    //    private List<Cart> cartItems;
    //    @OneToMany(mappedBy = "checkout")
    //    private List<Cart> cartItems = new ArrayList<>();
}
