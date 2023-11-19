package com.edstem.gamegearstore.service;

import com.edstem.gamegearstore.contract.request.GameRequest;
import com.edstem.gamegearstore.contract.response.CartResponse;
import com.edstem.gamegearstore.contract.response.GameResponse;
import com.edstem.gamegearstore.model.Cart;
import com.edstem.gamegearstore.model.Game;
import com.edstem.gamegearstore.model.User;
import com.edstem.gamegearstore.repository.CartRepository;
import com.edstem.gamegearstore.repository.GameRepository;
import com.edstem.gamegearstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

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

//    public CartResponse addGameToCart(Long gameId, Long userId) {
//        Game game =
//                gameRepository
//                        .findById(gameId)
//                        .orElseThrow(() -> new RuntimeException("Game not found"));
//        User user =
//                userRepository
//                        .findById(userId)
//                        .orElseThrow(
//                                () ->
//                                        new EntityNotFoundException(
//                                                "User not found with id " + userId));
//        List<Game> games = new ArrayList<>();
//        games.add(game);
//        Cart cart = Cart.builder().game(games).user(user).build();
//        Cart savedCart = cartRepository.save(cart);
//        return modelMapper.map(savedCart, CartResponse.class);

    public CartResponse addGameToCart(Long gameId, Long userId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        Optional<Cart> existingCartOptional = cartRepository.findByUserId(userId);

        if (existingCartOptional.isPresent()) {
            Cart existingCart = existingCartOptional.get();
            List<Game> games = existingCart.getGame();
            games.add(game);
            existingCart.setCount(games.stream().mapToLong(Game::getCount).sum());
            Cart savedCart = cartRepository.save(existingCart);
            return modelMapper.map(savedCart, CartResponse.class);
        } else {
            // Create a new cart if none exists for the user
            List<Game> games = new ArrayList<>();
            games.add(game);
            Cart newCart = Cart.builder().game(games).user(user).build();
            Cart savedCart = cartRepository.save(newCart);
            return modelMapper.map(savedCart, CartResponse.class);
        }
    }


    public List<Cart> findCartsByUserId(Long userId) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);

        if (cartOptional.isPresent()) {
            return Collections.singletonList(cartOptional.get());
        } else {
            return Collections.emptyList();
        }
    }


    public void removeGameFromCart(Long gameId, Long userId) {
        List<Cart> carts = cartRepository.findCartsByUserId(userId);

        if (!carts.isEmpty()) {
            for (Cart cart : carts) {
                List<Game> games = cart.getGame();

                games.removeIf(game -> game.getGameId().equals(gameId));
                cart.setCount(games.stream().mapToLong(Game::getCount).sum());
                cartRepository.save(cart);
            }
        } else {
            throw new RuntimeException("Carts not found for user with ID: " + userId);
        }
    }

    public List<CartResponse> viewCarts(Long userId) {
        List<Cart> carts = cartRepository.findCartsByUserId(userId);

        if (!carts.isEmpty()) {
            return carts.stream()
                    .flatMap(cart -> cart.getGame().stream())
                    .map(game -> modelMapper.map(game, CartResponse.class))
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Carts not found for user with ID: " + userId);
        }
    }

//    public BigDecimal calculateTotalAmountByCartId(Long cartId) {
//        Cart cart = cartRepository.findById(cartId)
//                .orElseThrow(() -> new EntityNotFoundException("Cart not found with id: " + cartId));
//
//        List<Game> games = cart.getGame();
//
//        BigDecimal totalAmount = games.stream()
//                .map(Game::getPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        return totalAmount;
//    }


}


//        private Game getExistingGameInCart (Cart cart, Long gameId){
//            return cart.getGame().stream()
//                    .filter(g -> g.getGameId().equals(gameId))
//                    .findFirst()
//                    .orElse(null);
//        }

