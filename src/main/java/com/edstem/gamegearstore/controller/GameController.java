package com.edstem.gamegearstore.controller;

import com.edstem.gamegearstore.contract.request.CartRequest;
import com.edstem.gamegearstore.contract.request.GameRequest;
import com.edstem.gamegearstore.contract.response.CartResponse;
import com.edstem.gamegearstore.contract.response.GameResponse;
import com.edstem.gamegearstore.model.Cart;
import com.edstem.gamegearstore.service.GameService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public GameResponse addGame(@RequestBody GameRequest request) {
        return this.gameService.addGame(request);
    }

    @GetMapping
    public List<GameResponse> viewAllGames() {
        return gameService.viewAllGames();
    }

    @GetMapping("/{id}")
    public GameResponse viewGameById(@PathVariable Long id) {
        return gameService.viewGameById(id);
    }

    @PutMapping("/{id}")
    public GameResponse updateGamesById(@PathVariable Long id, @RequestBody GameRequest request) {
        return gameService.updateGameById(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteGameById(@PathVariable Long id) {
        return gameService.deleteGameById(id);
    }

    @PostMapping("/{gameId}/create")
    public ResponseEntity<CartResponse> addGameToCart(
            @RequestBody CartRequest request, @PathVariable Long gameId) {
        CartResponse response = gameService.addGameToCart(request, gameId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/carts/{gameId}")
    public void removeGameFromCart(@PathVariable Long gameId) {
        gameService.removeGameFromCart(gameId);
    }

    @GetMapping("/carts")
    public List<Cart> getAllCarts() {
        return gameService.getAllCarts();
    }
}
