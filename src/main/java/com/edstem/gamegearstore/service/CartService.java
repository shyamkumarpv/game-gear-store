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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartItems(new ArrayList<>());
            cart = cartRepository.save(cart);
        }

        CartItem cartItem = new CartItem();
        cartItem.setGame(game);
        cartItem.setCart(cart);
        cartItem.setCount(1L);
        cartItem = cartItemRepository.save(cartItem);

        cart.getCartItems().add(cartItem);
        cart.setCount(cart.getCartItems().stream().mapToLong(CartItem::getCount).sum());
        cart = cartRepository.save(cart);

        return modelMapper.map(cart, CartResponse.class);
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

    public List<GameResponse> viewCart(Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();

        Cart cart = cartRepository.findByUserId(userId);
        return cart.getCartItems().stream()
                .map(cartItem -> modelMapper.map(cartItem.getGame(), GameResponse.class))
                .collect(Collectors.toList());
    }

    public int getNumberOfItemsInCart(Long userId) {
        User user = getUserById(userId);
        Cart cart = user.getCart();
        if (cart != null && cart.getCartItems() != null) {
            return cart.getCartItems().size();
        }
        return 0;
    }

    private User getUserById(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }
}
