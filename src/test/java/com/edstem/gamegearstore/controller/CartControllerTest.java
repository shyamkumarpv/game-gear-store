package com.edstem.gamegearstore.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.edstem.gamegearstore.contract.response.CartResponse;
import com.edstem.gamegearstore.contract.response.GameResponse;
import com.edstem.gamegearstore.model.Cart;
import com.edstem.gamegearstore.model.CartItem;
import com.edstem.gamegearstore.model.User;
import com.edstem.gamegearstore.repository.CartRepository;
import com.edstem.gamegearstore.service.CartService;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CartControllerTest {

    @InjectMocks private CartController cartController;

    @Mock private CartService cartService;

    @Mock private ModelMapper modelMapper;

    @Mock private CartRepository cartRepository;

    @Mock private Authentication authentication;

    @Mock private User user;

    @Before
    public void setUp() {
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getId()).thenReturn(1L);
    }

    @Test
    public void testViewCart() {
        Cart cart = new Cart();
        cart.setCartItems(Arrays.asList(new CartItem(), new CartItem()));
        when(cartRepository.findByUserId(1L)).thenReturn(cart);

        List<GameResponse> gameResponses = cartController.viewCart(authentication);

        verify(cartRepository).findByUserId(1L);
        assertEquals("Expected number of cart items", 2, gameResponses.size());
    }

    @Test
    public void testAddGameToCart() {
        CartResponse cartResponse = new CartResponse();
        when(cartService.addGameToCart(anyLong(), anyLong())).thenReturn(cartResponse);

        ResponseEntity<CartResponse> response = cartController.addGameToCart(1L, authentication);

        verify(cartService).addGameToCart(1L, 1L);
        assertEquals("Expected HTTP status", HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testRemoveGameFromCart() {
        doNothing().when(cartService).removeGameFromCart(anyLong(), anyLong());

        ResponseEntity<String> response = cartController.removeGameFromCart(1L, authentication);

        verify(cartService).removeGameFromCart(1L, 1L);
        assertEquals("Expected HTTP status", HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetNumberOfItemsInCart_Success() {
        when(cartService.getNumberOfItemsInCart(1L)).thenReturn(3);

        ResponseEntity<Integer> response = cartController.getNumberOfItemsInCart(authentication);

        verify(cartService).getNumberOfItemsInCart(1L);
        assertEquals("Expected number of items in cart", Integer.valueOf(3), response.getBody());
    }
}
