package com.edstem.gamegearstore.service;

import com.edstem.gamegearstore.contract.response.CartResponse;
import com.edstem.gamegearstore.contract.response.GameResponse;
import com.edstem.gamegearstore.model.Cart;
import com.edstem.gamegearstore.model.CartItem;
import com.edstem.gamegearstore.model.Game;
import com.edstem.gamegearstore.model.User;
import com.edstem.gamegearstore.repository.CartItemRepository;
import com.edstem.gamegearstore.repository.CartRepository;
import com.edstem.gamegearstore.repository.GameRepository;
import com.edstem.gamegearstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
    @RequiredArgsConstructor
    @Service
    public class CartService {
        private final CartRepository cartRepository;
        private final CartItemRepository cartItemRepository;
        private final GameRepository gameRepository;
        private final UserRepository userRepository;
        private final ModelMapper modelMapper;

        public CartResponse addGameToCart(Long gameId, Long userId) {
            Game game =
                    gameRepository
                            .findById(gameId)
                            .orElseThrow(() -> new RuntimeException("Game not found"));

            User user =
                    userRepository
                            .findById(userId)
                            .orElseThrow(
                                    () ->
                                            new EntityNotFoundException(
                                                    "User not found with id " + userId));

            Optional<Cart> existingCartOptional =
                    Optional.ofNullable(cartRepository.findByUserId(userId));

            if (existingCartOptional.isPresent()) {
                Cart existingCart = existingCartOptional.get();
                CartItem cartItem = CartItem.builder().game(game).cart(existingCart).count(1L).build();
                existingCart.getCartItems().add(cartItem);
                existingCart.setCount(
                        existingCart.getCartItems().stream().mapToLong(CartItem::getCount).sum());
                Cart savedCart = cartRepository.save(existingCart);
                cartItemRepository.save(cartItem);
                return modelMapper.map(savedCart, CartResponse.class);
            } else {
                Cart newCart = Cart.builder().user(user).cartItems(new ArrayList<>()).build();
                Cart savedCart = cartRepository.save(newCart);
                CartItem cartItem = CartItem.builder().game(game).cart(savedCart).count(1L).build();
                cartItemRepository.save(cartItem);
                return modelMapper.map(savedCart, CartResponse.class);
            }
        }

        public void removeGameFromCart(Long gameId, Long userId) {
            List<Cart> carts = cartRepository.findCartsByUserId(userId);

            if (!carts.isEmpty()) {
                for (Cart cart : carts) {
                    List<CartItem> cartItems = new ArrayList<>(cart.getCartItems());

                    for (CartItem cartItem : cartItems) {
                        if (cartItem.getGame().getGameId().equals(gameId)) {
                            cart.getCartItems().remove(cartItem);
                            cartItemRepository.delete(cartItem);
                        }
                    }
                    cart.setCount(cart.getCartItems().stream().mapToLong(CartItem::getCount).sum());
                    cartRepository.save(cart);
                }
            } else {
                throw new RuntimeException("Carts not found for user with ID: " + userId);
            }
        }


        public List<GameResponse> viewCart(Long userId) {
            Cart cart = cartRepository.findByUserId(userId);
            return cart.getCartItems().stream()
                    .map(cartItem -> modelMapper.map(cartItem.getGame(), GameResponse.class))
                    .collect(Collectors.toList());
        }

        public int getNumberOfItemsInCart(Long userId) {
            User user = userRepository.findById(userId).orElse(null);

            if (user != null && user.getCart() != null && user.getCart().getCartItems() != null) {
                return user.getCart().getCartItems().size();
            }
            return 0;

        }

    }


