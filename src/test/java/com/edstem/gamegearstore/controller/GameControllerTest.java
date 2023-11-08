package com.edstem.gamegearstore.controller;

import com.edstem.gamegearstore.contract.request.GameRequest;
import com.edstem.gamegearstore.contract.response.GameResponse;
import com.edstem.gamegearstore.service.GameService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GameControllerTest {

    @InjectMocks
    private GameController gameController;

    @Mock
    private GameService gameService;

    @Test
    public void testAddGame() {
        GameRequest request = new GameRequest();
        request.setName("Test Game");
        request.setDescription("Test Description");
        request.setPrice(BigDecimal.valueOf(29.99));

        GameResponse expectedResponse = new GameResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("Test Game");
        expectedResponse.setDescription("Test Description");
        expectedResponse.setPrice(BigDecimal.valueOf(29.99));

        when(gameService.addGame(any(GameRequest.class))).thenReturn(expectedResponse);

        GameResponse response = gameController.addGame(request);

        assertThat(response).isEqualToComparingFieldByField(expectedResponse);
    }

    @Test
    public void testViewAllGames() {
        List<GameResponse> expectedResponseList = Arrays.asList(
                createSampleGameResponse(1L, "Game A", "Description A", BigDecimal.valueOf(29.99)),
                createSampleGameResponse(2L, "Game B", "Description B", BigDecimal.valueOf(19.99))
        );

        when(gameService.viewAllGames()).thenReturn(expectedResponseList);
        List<GameResponse> responseList = gameController.viewAllGames();
        assertThat(responseList).isEqualTo(expectedResponseList);
    }

    private GameResponse createSampleGameResponse(Long id, String name, String description, BigDecimal price) {
        GameResponse response = new GameResponse();
        response.setId(id);
        response.setName(name);
        response.setDescription(description);
        response.setPrice(price);
        return response;
    }

    @Test
    public void testViewGameById() {
        Long gameId = 1L;
        GameResponse expectedResponse = createSampleGameResponse(gameId, "Game A", "Description A", BigDecimal.valueOf(29.99));
        when(gameService.viewGameById(any(Long.class))).thenReturn(expectedResponse);
        GameResponse response = gameController.viewGameById(gameId);
        assertThat(response).isEqualToComparingFieldByField(expectedResponse);
    }


    }

