package com.edstem.gamegearstore.service;

import com.edstem.gamegearstore.contract.request.GameRequest;
import com.edstem.gamegearstore.contract.response.GameResponse;
import com.edstem.gamegearstore.model.Game;
import com.edstem.gamegearstore.repository.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;

    public GameResponse addGame(GameRequest request) {
        Game game = modelMapper.map(request, Game.class);
        Game savedGame = gameRepository.save(game);
        return modelMapper.map(savedGame, GameResponse.class);
    }

    public List<GameResponse> viewAllGames() {
        List<Game> games = (List<Game>) gameRepository.findAll();
        return games.stream()
                .map(topic -> modelMapper.map(topic, GameResponse.class))
                .collect(Collectors.toList());
    }

    public GameResponse viewGameById(Long id) {
        Game game =
                gameRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("game not found with id " + id));
        return modelMapper.map(game, GameResponse.class);
    }

    public GameResponse updateGameById(Long id, GameRequest request) {
        Game game =
                gameRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Game not found with id " + id));
        modelMapper.map(request, game);
        Game updatedGame = gameRepository.save(game);
        return modelMapper.map(updatedGame, GameResponse.class);
    }

    public String deleteGameById(Long id) {
        Game game =
                gameRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Game not found with id " + id));
        gameRepository.delete(game);
        return "game " + game.getName() + " has been deleted";
    }
}
