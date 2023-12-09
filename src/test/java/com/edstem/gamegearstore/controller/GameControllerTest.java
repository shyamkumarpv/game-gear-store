package com.edstem.gamegearstore.controller;

import static org.mockito.Mockito.when;

import com.edstem.gamegearstore.contract.request.GameRequest;
import com.edstem.gamegearstore.contract.response.GameResponse;
import com.edstem.gamegearstore.repository.CartRepository;
import com.edstem.gamegearstore.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {GameController.class})
@ExtendWith(SpringExtension.class)
class GameControllerTest {
    @MockBean private CartRepository cartRepository;

    @Autowired private GameController gameController;

    @MockBean private GameService gameService;

    @Test
    void testAddGame() throws Exception {
        when(gameService.viewAllGames()).thenReturn(new ArrayList<>());

        GameRequest gameRequest = new GameRequest();
        gameRequest.setCount(3L);
        gameRequest.setDescription("The characteristics of someone or something");
        gameRequest.setImageUrl("https://example.org/example");
        gameRequest.setName("Name");
        gameRequest.setPrice(new BigDecimal("2.3"));
        String content = (new ObjectMapper()).writeValueAsString(gameRequest);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
        MockMvcBuilders.standaloneSetup(gameController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testViewAllGames() throws Exception {
        when(gameService.viewAllGames()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/games");
        MockMvcBuilders.standaloneSetup(gameController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testViewGameById() throws Exception {
        GameResponse.GameResponseBuilder nameResult =
                GameResponse.builder()
                        .count(3L)
                        .description("The characteristics of someone or something")
                        .gameId(1L)
                        .imageUrl("https://example.org/example")
                        .name("Name");
        GameResponse buildResult = nameResult.price(new BigDecimal("2.3")).build();
        when(gameService.viewGameById(Mockito.<Long>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/games/{id}", 1L);
        MockMvcBuilders.standaloneSetup(gameController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content()
                                .string(
                                        "{\"gameId\":1,\"name\":\"Name\",\"description\":\"The"
                                            + " characteristics of someone or"
                                            + " something\",\"price\":2.3,"
                                            + "\"count\":3,\"imageUrl\":\"https://example.org/example\"}"));
    }

    @Test
    void testUpdateGameById() throws Exception {
        GameResponse.GameResponseBuilder nameResult =
                GameResponse.builder()
                        .count(3L)
                        .description("The characteristics of someone or something")
                        .gameId(1L)
                        .imageUrl("https://example.org/example")
                        .name("Name");
        GameResponse buildResult = nameResult.price(new BigDecimal("2.3")).build();
        when(gameService.updateGameById(Mockito.<Long>any(), Mockito.<GameRequest>any()))
                .thenReturn(buildResult);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setCount(3L);
        gameRequest.setDescription("The characteristics of someone or something");
        gameRequest.setImageUrl("https://example.org/example");
        gameRequest.setName("Name");
        gameRequest.setPrice(new BigDecimal("2.3"));
        String content = (new ObjectMapper()).writeValueAsString(gameRequest);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.put("/games/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
        MockMvcBuilders.standaloneSetup(gameController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content()
                                .string(
                                        "{\"gameId\":1,\"name\":\"Name\",\"description\":\"The"
                                            + " characteristics of someone or"
                                            + " something\",\"price\":2.3,"
                                            + "\"count\":3,\"imageUrl\":\"https://example.org/example\"}"));
    }

    @Test
    void testDeleteGameById() throws Exception {
        when(gameService.deleteGameById(Mockito.<Long>any())).thenReturn("42");
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.delete("/games/{id}", 1L);
        MockMvcBuilders.standaloneSetup(gameController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content()
                                .contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("42"));
    }
    //    @Test
    //    void testGetGamesInCart() throws Exception {
    //        when(gameService.getGamesInCart(Mockito.<Long>any())).thenReturn(new ArrayList<>());
    //        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/games/carts");
    //        MockHttpServletRequestBuilder requestBuilder = getResult.param("userId",
    // String.valueOf(1L));
    //        MockMvcBuilders.standaloneSetup(gameController)
    //                .build()
    //                .perform(requestBuilder)
    //                .andExpect(MockMvcResultMatchers.status().isOk())
    //                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
    //                .andExpect(MockMvcResultMatchers.content().string("[]"));
}
//    @Test
//    void testAddGameToCart() throws Exception {
//        CartResponse buildResult = CartResponse.builder().cartId(1L).build();
//        when(gameService.addGameToCart(Mockito.<Long>any(),
// Mockito.<Long>any())).thenReturn(buildResult);
//        MockHttpServletRequestBuilder postResult =
// MockMvcRequestBuilders.post("/games/{gameId}/create", 1L);
//        MockHttpServletRequestBuilder requestBuilder = postResult.param("userId",
// String.valueOf(1L));
//        ResultActions actualPerformResult =
// MockMvcBuilders.standaloneSetup(gameController).build().perform(requestBuilder);
//        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content().string("{\"cartId\":1}"));
//    }
//    @Test
//    void testRemoveGameFromCart() throws Exception {
//        doNothing().when(gameService).removeGameFromCart(Mockito.<Long>any(),
// Mockito.<Long>any());
//        MockHttpServletRequestBuilder deleteResult =
// MockMvcRequestBuilders.delete("/games/carts/{gameId}", 1L);
//        MockHttpServletRequestBuilder requestBuilder = deleteResult.param("userId",
// String.valueOf(1L));
//        MockMvcBuilders.standaloneSetup(gameController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//
// .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
//                .andExpect(MockMvcResultMatchers.content().string("Game successfully removed from
// the cart."));
//    }
