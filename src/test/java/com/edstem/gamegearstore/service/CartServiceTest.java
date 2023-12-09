package com.edstem.gamegearstore.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.gamegearstore.repository.CartItemRepository;
import com.edstem.gamegearstore.repository.CartRepository;
import com.edstem.gamegearstore.repository.GameRepository;
import com.edstem.gamegearstore.repository.UserRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CartService.class})
@ExtendWith(SpringExtension.class)
class CartServiceTest {
    @MockBean private CartItemRepository cartItemRepository;

    @MockBean private CartRepository cartRepository;

    @Autowired private CartService cartService;

    @MockBean private GameRepository gameRepository;

    @MockBean private ModelMapper modelMapper;

    @MockBean private UserRepository userRepository;

    /** Method under test: {@link CartService#removeGameFromCart(Long, Long)} */
    @Test
    void testRemoveGameFromCart() {
        when(cartRepository.findCartsByUserId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        assertThrows(RuntimeException.class, () -> cartService.removeGameFromCart(1L, 1L));
        verify(cartRepository).findCartsByUserId(Mockito.<Long>any());
    }
}
