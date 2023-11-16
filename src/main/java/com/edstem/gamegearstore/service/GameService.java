package com.edstem.gamegearstore.service;

import com.edstem.gamegearstore.contract.request.CartRequest;
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

import java.util.ArrayList;
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


//    public CartResponse addGameToCart(CartRequest request, Long gameId,long userID) {
//        Game game =
//                gameRepository
//                        .findById(gameId)
//                        .orElseThrow(() -> new RuntimeException("Game not found"));
//
//        Cart cart = Cart.builder().game(game).count(request.getCount()).build();
//
//        Cart savedCart = cartRepository.save(cart);
//        CartResponse response = modelMapper.map(savedCart, CartResponse.class);
//
//        return response;


//    public CartResponse addGameToCart(CartRequest request, Long gameId, long userId) {
//        Game game =
//                gameRepository
//                        .findById(gameId)
//                        .orElseThrow(
//                                () ->
//                                        new RuntimeException(
//                                                "Game not found with id "
//                                                        + request.getGameId()));
//
//        User user = userRepository.findById(userId).orElseThrow(() ->
//                new EntityNotFoundException(
//                        "User not found on id " + userId));
//        Cart cart = user.getCart();
//
//
//        if (cart == null) {
//            // If the user doesn't have a cart, create a new one
//            cart = Cart.builder()
//                    .user(user)
//                    .game(new ArrayList<>())
//                    .count(0L)
//                    .build();
//        }
//
//        // Assuming a bidirectional relationship between Game and Cart
//        game.setCart(cart);
////        game.(request.getCount());
//
//        cart.getGame().add(game);
//        cart.setCount(cart.getGame().stream().mapToLong(Game::getCount).sum());
//
//        cart = cartRepository.save(cart);
//
//        CartResponse response = CartResponse.builder()
//                .id(cart.getId())
//                .game(game.getId())
//                .count(cart.getCount())
//                .build();
//
//        return response;
//    }
    public CartResponse addGameToCart(Long gameId,Long userId){
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        List<Game> games = new ArrayList<>();
        games.add(game);
        Cart cart = Cart.builder()
                .game(games)
                .user(user)
                .build();
        Cart savedCart = cartRepository.save(cart);
        return modelMapper.map(savedCart, CartResponse.class);
    }

//    public CartResponse addGameToCart(CartRequest request, Long gameId, Long userId) {
//        Game game = gameRepository.findById(gameId)
//                .orElseThrow(() -> new RuntimeException("Game not found with id " + gameId));
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
//
//        Cart cart = user.getCart();
//        if (cart != null) {
//            cart = Cart.builder()
//                    .user(user)
//                    .game(new ArrayList<>())
//                    .count(0L)
//                    .build();
//        }
//
//        assert cart != null;
//        Game existingGame = getExistingGameInCart(cart, gameId);
//
//        if (existingGame != null) {
//            existingGame.setCount(existingGame.getCount() + request.getCount());
//        } else {
//            game.setCart(cart);
//            game.setCount(request.getCount());
//            cart.getGame().add(game);
//        }
//
//        cart.setCount(cart.getGame().stream().mapToLong(Game::getCount).sum());
//
//        cart = cartRepository.save(cart);
//
//        CartResponse response = CartResponse.builder()
//                .id(cart.getId())
//                .game(game.getId())
//                .count(cart.getCount())
//                .build();
//
//        return response;
//    }

    private Game getExistingGameInCart(Cart cart, Long gameId) {
        return cart.getGame().stream()
                .filter(g -> g.getGameId().equals(gameId))
                .findFirst()
                .orElse(null);
    }

    public void removeGameFromCart(Long gameId, Long userId) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            List<Game> games = cart.getGame();

            // Remove the game with the given ID from the list
            games.removeIf(game -> game.getGameId().equals(gameId));

            // Recalculate the cart count
            cart.setCount(games.stream().mapToLong(Game::getCount).sum());

            // Save the updated cart
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("Cart not found for user with ID: " + userId);
        }
    }
    public List<CartResponse> viewCart(Long userId) {
        Optional<Cart> carts = cartRepository.findByUserId(userId);
        return carts.stream()
                .map(cart -> modelMapper.map(cart, CartResponse.class))
                .collect(Collectors.toList());
    }


//    public Cart viewCart(Long userId) {
//        return cartRepository.findByUserId(userId)
//                .orElseThrow(() -> new RuntimeException("Cart not found for user with ID: " + userId));
//    }
}


//    public void removeGameFromCart(Long gameId, long userID) {
//        Optional<Cart> cart = Optional.ofNullable(cartRepository.findByGameId(gameId));
//        if (cart.isPresent()) {
//            cartRepository.delete(cart.get());
//        } else {
//            throw new RuntimeException("Game not found in cart");
//        }
//    }
//}
//
//    public List<Cart> getAllCarts() {
//        return cartRepository.findAll();
//
//    }
//
//    private CartResponse createCartResponse(Cart cart) {
//        if (cart != null) {
//            return new CartResponse(cart.getId(), cart.getGame(), cart.getCount());
//        } else {
//            return new CartResponse(null, null, 0L);
//        }
//    }

//public CheckoutResponse getCheckoutDetails(@RequestParam long userId, CheckoutRequest checkoutRequest) {
//    User user = userRepository.findById(userId)
//            .orElseThrow(() -> new RuntimeException("User not found"));
//
//    Cart userCart = cartRepository.findByUserId(user.getId());
//
//    return CheckoutResponse.builder()
//            .name(user.getName())
//            .email(user.getEmail())
//            .mobile(user.getMobile())
//            .shippingAddress(user.getShippingAddress())
//            .cartItems(Cart.builder().build())
//            .user(user.getId())
//            .build();
//}
//}
//

