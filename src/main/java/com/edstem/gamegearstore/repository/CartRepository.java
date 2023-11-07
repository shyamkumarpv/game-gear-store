package com.edstem.gamegearstore.repository;

import com.edstem.gamegearstore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByGameId(Long gameId);

}
