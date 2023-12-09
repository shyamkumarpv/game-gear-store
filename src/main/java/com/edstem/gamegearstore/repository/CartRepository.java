package com.edstem.gamegearstore.repository;

import com.edstem.gamegearstore.model.Cart;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);

    List<Cart> findCartsByUserId(Long userId);
}
