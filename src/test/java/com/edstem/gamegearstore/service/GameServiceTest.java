package com.edstem.gamegearstore.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.edstem.gamegearstore.contract.request.GameRequest;
import com.edstem.gamegearstore.contract.response.GameResponse;
import com.edstem.gamegearstore.model.Game;
import com.edstem.gamegearstore.repository.GameRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class GameServiceTest {

    @InjectMocks private GameService gameService;

    @Mock private GameRepository gameRepository;

    @Mock private ModelMapper modelMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddGame() {
        GameRequest gameRequest = new GameRequest();
        Game game = new Game();
        GameResponse gameResponse = new GameResponse();

        when(modelMapper.map(any(GameRequest.class), any())).thenReturn(game);
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(modelMapper.map(any(Game.class), any())).thenReturn(gameResponse);

        GameResponse result = gameService.addGame(gameRequest);

        assertEquals(gameResponse, result);
    }

    @Test
    public void testViewAllGames() {
        List<Game> games = Arrays.asList(new Game(), new Game());
        List<GameResponse> gameResponses = Arrays.asList(new GameResponse(), new GameResponse());

        when(gameRepository.findAll()).thenReturn(games);
        when(modelMapper.map(any(Game.class), any()))
                .thenReturn(gameResponses.get(0), gameResponses.get(1));

        List<GameResponse> result = gameService.viewAllGames();

        assertEquals(gameResponses, result);
    }

    @Test
    public void testViewGameById() {
        Long id = 1L;
        Game game = new Game();
        GameResponse gameResponse = new GameResponse();

        when(gameRepository.findById(id)).thenReturn(Optional.of(game));
        when(modelMapper.map(any(Game.class), any())).thenReturn(gameResponse);

        GameResponse result = gameService.viewGameById(id);

        assertEquals(gameResponse, result);
    }

    @Test
    public void testDeleteGameById() {
        Long gameId = 1L;
        Game existingGame =
                Game.builder()
                        .gameId(gameId)
                        .name("Game1")
                        .description("Description")
                        .price(new BigDecimal("59.99"))
                        .imageUrl("http://example.com/image.png")
                        .count(100)
                        .build();
        when(gameRepository.findById(gameId)).thenReturn(java.util.Optional.of(existingGame));

        String result = gameService.deleteGameById(gameId);

        assertEquals("game Game1 has been deleted", result);
        verify(gameRepository, times(1)).findById(gameId);
        verify(gameRepository, times(1)).delete(existingGame);
    }
}
