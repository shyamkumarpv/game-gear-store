package com.edstem.gamegearstore.repository;

import com.edstem.gamegearstore.model.Cart;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);
    List<Cart> findCartsByUserId(Long userId);
//    Optional<Cart> findByIdAndFetchGames(Long cartId);
}
