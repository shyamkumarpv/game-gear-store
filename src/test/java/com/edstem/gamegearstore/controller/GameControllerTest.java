package com.edstem.gamegearstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.edstem.gamegearstore.contract.request.GameRequest;
import com.edstem.gamegearstore.contract.response.GameResponse;
import com.edstem.gamegearstore.service.GameService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Mock private GameService gameService;

    @InjectMocks private GameController gameController;

    private GameRequest gameRequest;
    private GameResponse gameResponse;

    @BeforeEach
    void setUp() {
        gameRequest = new GameRequest();

        gameResponse = new GameResponse();
    }

    @Test
    public void testAddGame() {
        when(gameService.addGame(any(GameRequest.class))).thenReturn(gameResponse);

        GameResponse result = gameController.addGame(gameRequest);

        assertEquals(gameResponse, result);
    }

    @Test
    public void testViewAllGames() {
        List<GameResponse> expectedResponses = new ArrayList<>();

        when(gameService.viewAllGames()).thenReturn(expectedResponses);

        List<GameResponse> result = gameController.viewAllGames();

        assertEquals(expectedResponses, result);
    }

    @Test
    public void testViewGameById() {
        Long gameId = 1L;
        GameResponse expectedResponse = new GameResponse();

        when(gameService.viewGameById(gameId)).thenReturn(expectedResponse);

        GameResponse result = gameController.viewGameById(gameId);

        assertEquals(expectedResponse, result);
    }

    @Test
    public void testUpdateGameById() {
        Long gameId = 1L;
        GameRequest gameRequest = new GameRequest();
        GameResponse expectedResponse = new GameResponse();

        when(gameService.updateGameById(eq(gameId), any(GameRequest.class)))
                .thenReturn(expectedResponse);

        GameResponse result = gameController.updateGameById(gameId, gameRequest);

        assertEquals(expectedResponse, result);
    }

    @Test
    public void testDeleteGameById() {
        Long gameId = 1L;
        String expectedMessage = "Game deleted successfully";

        when(gameService.deleteGameById(gameId)).thenReturn(expectedMessage);

        String result = gameController.deleteGameById(gameId);

        assertEquals(expectedMessage, result);
    }
}
