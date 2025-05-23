package com.portfolioplus.mmorpg.service;

import com.portfolioplus.mmorpg.dto.PlayerDTO;
import com.portfolioplus.mmorpg.model.Character;
import com.portfolioplus.mmorpg.model.Player;
import com.portfolioplus.mmorpg.repository.CharacterRepository;
import com.portfolioplus.mmorpg.repository.PlayerRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static com.portfolioplus.mmorpg.mapper.PlayerMapper.fromDTO;
import static com.portfolioplus.mmorpg.mapper.PlayerMapper.toDTO;
import static com.portfolioplus.mmorpg.utils.Utils.isNullOrEmpty;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;
    private CharacterRepository characterRepository;
    private AuthContextService authContextService;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, CharacterRepository characterRepository, AuthContextService authContextService) {
        this.playerRepository = playerRepository;
        this.characterRepository = characterRepository;
        this.authContextService = authContextService;
    }

    // Gets a list of all players and converts them to DTOs
    public List<PlayerDTO> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        List<PlayerDTO> playerDTOs = new ArrayList<>();
        for (Player player : players) {
            playerDTOs.add(toDTO(player));
        }
        return playerDTOs;
    }
    
    // Gets a player by their ID and converts it to a DTO
    public PlayerDTO getPlayerById(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Player not found, playerId: " + playerId
        ));
        return toDTO(player);
    }

    // Creates a new player from a DTO and saves it to the repository
    public PlayerDTO createPlayer(PlayerDTO playerDTO) {
        if (isNullOrEmpty(playerDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username cannot be empty");
        }
        if (isNullOrEmpty(playerDTO.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be empty");
        }
        if (isNullOrEmpty(playerDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be empty");
        }
        Player newPlayer = fromDTO(playerDTO);
        Player savedPlayer = playerRepository.save(newPlayer);
        return toDTO(savedPlayer);

    }

    // Updates an existing player's details by their ID using a DTO (PATCH mapping)
    public PlayerDTO updatePlayerById(Long playerId, PlayerDTO playerDTO, HttpServletRequest request) {
        authContextService.requireMatchOrAdmin(request, playerId);

        Player existingPlayer = playerRepository.findById(playerId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Player not found, playerId: " + playerId
        ));

        // Update only the fields that are not null in the DTO
        if (!isNullOrEmpty(playerDTO.getUsername())) {
            existingPlayer.setUsername(playerDTO.getUsername());
        }
        if (!isNullOrEmpty(playerDTO.getPassword())) {
            existingPlayer.setPassword(playerDTO.getPassword());
        }
        if (!isNullOrEmpty(playerDTO.getEmail())) {
            existingPlayer.setEmail(playerDTO.getEmail());
        }
        if (playerDTO.getRole() != null) {
            existingPlayer.setRole(playerDTO.getRole());
        }

        Player updatedCharacter = playerRepository.save(existingPlayer);
        return toDTO(updatedCharacter);
    }

    // Deletes a player by their ID after ensuring they have no associated characters
    public void deletePlayerById(Long playerId, HttpServletRequest request) {
        //authContextService.requireMatchOrAdmin(request, playerId);
        authContextService.requireAdmin(request);

        Player existingPlayer = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found, playerId: " + playerId));

        List<Character> characters = characterRepository.findByPlayerId(playerId);
        if (!characters.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete Player when its Characters exist; delete Characters first");
        }
        playerRepository.delete(existingPlayer);
        System.out.println("Player deleted, playerId: " + playerId);
    }
}

