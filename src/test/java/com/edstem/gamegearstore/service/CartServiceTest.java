package com.edstem.gamegearstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.edstem.gamegearstore.contract.response.CartResponse;
import com.edstem.gamegearstore.contract.response.GameResponse;
import com.edstem.gamegearstore.model.*;
import com.edstem.gamegearstore.repository.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock private CartRepository cartRepository;
    @Mock private CartItemRepository cartItemRepository;
    @Mock private GameRepository gameRepository;
    @Mock private UserRepository userRepository;
    @Mock private ModelMapper modelMapper;
    @InjectMocks private CartService cartService;

    private Game game;
    private User user;
    private Cart cart;
    private CartItem cartItem;
    private CartResponse cartResponse;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        game = new Game();
        user = new User();
        cart = new Cart();
        cartItem = new CartItem();
        cartResponse = new CartResponse();
        authentication = mock(Authentication.class);
    }

    @Test
    public void testAddGameToCart() {
        Long gameId = 1L;
        Long userId = 1L;

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(userId)).thenReturn(null);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
        when(modelMapper.map(any(Cart.class), eq(CartResponse.class))).thenReturn(cartResponse);

        CartResponse result = cartService.addGameToCart(gameId, userId);

        assertNotNull(result);
        assertEquals(cartResponse, result);
    }

    @Test
    public void testRemoveGameFromCart() {
        Long gameId = 1L;
        Long userId = 1L;
        Game game = new Game();
        game.setGameId(gameId);
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setGame(game);
        List<Cart> carts = new ArrayList<>();
        carts.add(cart);
        cart.getCartItems().add(cartItem);

        when(cartRepository.findCartsByUserId(userId)).thenReturn(carts);
        doNothing().when(cartItemRepository).delete(cartItem);

        cartService.removeGameFromCart(gameId, userId);

        verify(cartItemRepository, times(1)).delete(cartItem);
        assertEquals(0, cart.getCartItems().size());
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    public void testViewCart() {
        Long userId = 1L;
        Authentication authentication = mock(Authentication.class);
        User user = mock(User.class);
        Cart cart = mock(Cart.class);
        List<CartItem> cartItems = new ArrayList<>();
        Game game = new Game();
        GameResponse gameResponse = new GameResponse();

        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getId()).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(cart);
        when(cart.getCartItems()).thenReturn(cartItems);
        when(modelMapper.map(any(Game.class), eq(GameResponse.class))).thenReturn(gameResponse);

        CartItem cartItem = new CartItem();
        cartItem.setGame(game);
        cartItems.add(cartItem);

        List<GameResponse> result = cartService.viewCart(authentication);

        assertNotNull(result);
        assertEquals(cartItems.size(), result.size());
        for (GameResponse response : result) {
            assertEquals(gameResponse, response);
        }
    }

    @Test
    public void testGetNumberOfItemsInCart_WithItems() {
        Long userId = 1L;
        User user = mock(User.class);
        Cart cart = mock(Cart.class);
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(mock(CartItem.class));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(user.getCart()).thenReturn(cart);
        when(cart.getCartItems()).thenReturn(cartItems);

        int itemCount = cartService.getNumberOfItemsInCart(userId);

        assertEquals(cartItems.size(), itemCount);
    }

    @Test
    public void testGetNumberOfItemsInCart_NoItems() {
        Long userId = 1L;
        User user = mock(User.class);
        Cart cart = mock(Cart.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(user.getCart()).thenReturn(cart);
        when(cart.getCartItems()).thenReturn(new ArrayList<>());

        int itemCount = cartService.getNumberOfItemsInCart(userId);

        assertEquals(0, itemCount);
    }
}
