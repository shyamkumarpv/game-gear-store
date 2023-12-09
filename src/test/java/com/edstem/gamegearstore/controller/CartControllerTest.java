package com.edstem.gamegearstore.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.edstem.gamegearstore.contract.response.CartResponse;
import com.edstem.gamegearstore.repository.CartRepository;
import com.edstem.gamegearstore.service.CartService;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CartController.class})
@ExtendWith(SpringExtension.class)
public class CartControllerTest {
    @MockBean private CartRepository cartRepository;

    @Autowired private CartController cartController;

    @MockBean private CartService cartService;

    @Test
    void testviewCart() throws Exception {
        when(cartService.viewCart(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/games/carts");
        MockHttpServletRequestBuilder requestBuilder =
                getResult.param("userId", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testAddGameToCart() throws Exception {
        CartResponse buildResult = CartResponse.builder().cartId(1L).build();
        when(cartService.addGameToCart(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(buildResult);
        MockHttpServletRequestBuilder postResult =
                MockMvcRequestBuilders.post("/games/{gameId}/create", 1L);
        MockHttpServletRequestBuilder requestBuilder =
                postResult.param("userId", String.valueOf(1L));
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(cartController).build().perform(requestBuilder);
        actualPerformResult
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"cartId\":1}"));
    }

    @Test
    void testRemoveGameFromCart() throws Exception {
        doNothing().when(cartService).removeGameFromCart(Mockito.<Long>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder deleteResult =
                MockMvcRequestBuilders.delete("/games/carts/{gameId}", 1L);
        MockHttpServletRequestBuilder requestBuilder =
                deleteResult.param("userId", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content()
                                .contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(
                        MockMvcResultMatchers.content()
                                .string("Game successfully removed from the cart."));
    }
}
