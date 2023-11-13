package com.edstem.gamegearstore.repository;

import com.edstem.gamegearstore.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {}
