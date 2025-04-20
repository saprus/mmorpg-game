package com.portfolioplus.mmorpg.service;

import com.portfolioplus.mmorpg.dto.CharacterDTO;
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

import static com.portfolioplus.mmorpg.mapper.CharacterMapper.fromDTO;
import static com.portfolioplus.mmorpg.mapper.CharacterMapper.toDTO;
import static com.portfolioplus.mmorpg.utils.Utils.isNullOrEmpty;

@Service
public class CharacterService {
    private final PlayerRepository playerRepository;
    private final CharacterRepository characterRepository;
    private AuthContextService authContextService;

    @Autowired
    public CharacterService(PlayerRepository playerRepository, CharacterRepository characterRepository, AuthContextService authContextService) {
        this.playerRepository = playerRepository;
        this.characterRepository = characterRepository;
        this.authContextService = authContextService;
    }

    // List all characters of a player
    public List<CharacterDTO> getCharactersByPlayer(Long playerId) {
        playerRepository.findById(playerId).orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Player not found: " + playerId
        ));

        List<Character> characters = characterRepository.findByPlayerId(playerId);
        List<CharacterDTO> characterDTOs = new ArrayList<>();
        for (Character character : characters) {
            characterDTOs.add(toDTO(character));
        }
        return characterDTOs;
    }

    // Get a character by id
    public CharacterDTO getCharacterById(Long playerId, Long characterId) {
        Character existingCharacter = characterRepository.findById(characterId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Character not found: " + characterId
        ));
        validateCharacterOwnership(playerId, existingCharacter);
        return toDTO(existingCharacter);
    }

    // Create a new character for a player
    public CharacterDTO createCharacter(Long playerId, CharacterDTO characterDTO) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Player not found: " + playerId
        ));

        if (isNullOrEmpty(characterDTO.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Character Name cannot be null or empty");
        }
        if (characterDTO.getLevel() < 0 || characterDTO.getLevel() > 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Level has to be between 0 - 10");
        }
        Character character = fromDTO(characterDTO, player);
        Character savedCharacter = characterRepository.save(character);
        return toDTO(savedCharacter);
    }

    // Patch Update character
    public CharacterDTO updateCharacterById(Long playerId, Long characterId, CharacterDTO characterDTO, HttpServletRequest request) {
        authContextService.requireMatchOrAdmin(request, playerId);

        Character existingCharacter = characterRepository.findById(characterId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Character not found: " + characterId
        ));

        validateCharacterOwnership(playerId, existingCharacter);

        // Update only the fields that are not null in the DTO
        if (!isNullOrEmpty(characterDTO.getName())) {
            existingCharacter.setName(characterDTO.getName());
        }
        if (characterDTO.getLevel() != null && characterDTO.getLevel() >= 0 && characterDTO.getLevel() <= 10) {
            existingCharacter.setLevel(characterDTO.getLevel());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Level has to be between 0 - 10");
        }
        if (characterDTO.getCharacterClass() != null) {
            existingCharacter.setCharacterClass(characterDTO.getCharacterClass());
        }

        Character updatedCharacter = characterRepository.save(existingCharacter);
        return toDTO(updatedCharacter);
    }

    // Delete a character
    public void deleteCharacterById(Long playerId, Long characterId, HttpServletRequest request) {
        authContextService.requireMatchOrAdmin(request, playerId);

        Character existingCharacter = characterRepository.findById(characterId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Character not found: " + characterId
        ));
        validateCharacterOwnership(playerId, existingCharacter);

        characterRepository.delete(existingCharacter);
        System.out.println("Character deleted: " + characterId);
    }

    // Helper method to check if the character actually belongs to playerId
    private void validateCharacterOwnership(Long playerId, Character character) {
        if (!character.getPlayer().getId().equals(playerId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Character does not belong to the specified player: " + playerId
            );
        }
    }
}