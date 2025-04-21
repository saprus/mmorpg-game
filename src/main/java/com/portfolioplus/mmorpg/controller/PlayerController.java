package com.portfolioplus.mmorpg.controller;

import com.portfolioplus.mmorpg.dto.PlayerDTO;
import com.portfolioplus.mmorpg.model.Player;
import com.portfolioplus.mmorpg.repository.PlayerRepository;
import com.portfolioplus.mmorpg.service.PlayerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // Get all the players
    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<PlayerDTO> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    // Get a player by id
    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long playerId) {
        PlayerDTO playerDTO = playerService.getPlayerById(playerId);
        return ResponseEntity.ok(playerDTO);
    }

    // Create a player
    @PostMapping
    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody PlayerDTO playerDTO) {
        PlayerDTO createdPlayer = playerService.createPlayer(playerDTO);
        return ResponseEntity.status(201).body(createdPlayer);
    }

    // Update a player info
    @PatchMapping("/{playerId}")
    public ResponseEntity<PlayerDTO> updatePlayerById(@PathVariable Long playerId, @RequestBody PlayerDTO playerDTO, HttpServletRequest request) {
        PlayerDTO updatedPlayer = playerService.updatePlayerById(playerId, playerDTO, request);
        return ResponseEntity.ok(updatedPlayer);
    }

    // Delete a player
    @DeleteMapping("/{playerId}")
    public ResponseEntity<Void> deletePlayerById(@PathVariable Long playerId, HttpServletRequest request) {
        playerService.deletePlayerById(playerId, request);
        return ResponseEntity.noContent().build();
    }
}
