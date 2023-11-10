package com.edstem.gamegearstore.service;

import com.edstem.gamegearstore.contract.request.CartRequest;
import com.edstem.gamegearstore.contract.request.GameRequest;
import com.edstem.gamegearstore.contract.response.CartResponse;
import com.edstem.gamegearstore.contract.response.GameResponse;
import com.edstem.gamegearstore.model.Cart;
import com.edstem.gamegearstore.model.Game;
import com.edstem.gamegearstore.repository.CartRepository;
import com.edstem.gamegearstore.repository.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;

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
        return "game" + game.getName() + "has been deleted";
    }

    public CartResponse addGameToCart(CartRequest request, Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));

        Cart cart = Cart.builder()
                .game(game)
                .count(request.getCount())
                .build();

        Cart savedCart = cartRepository.save(cart);
        CartResponse response = modelMapper.map(savedCart, CartResponse.class);

        return response;
    }
    public void removeGameFromCart(Long gameId) {
        Optional<Cart> cart = Optional.ofNullable(cartRepository.findByGameId(gameId));
        if (cart.isPresent()) {
            cartRepository.delete(cart.get());
        } else {
            throw new RuntimeException("Game not found in cart");
        }
    }
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
}
