package com.portfolioplus.mmorpg.controller;

import com.portfolioplus.mmorpg.dto.CharacterDTO;
import com.portfolioplus.mmorpg.model.Character;
import com.portfolioplus.mmorpg.service.CharacterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players/{playerId}/characters")
public class CharacterController {

    private CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    // List all characters of a player
    @GetMapping
    public ResponseEntity<List<CharacterDTO>> getCharactersByPlayer(@PathVariable Long playerId) {
        List<CharacterDTO> characters = characterService.getCharactersByPlayer(playerId);
        return ResponseEntity.ok(characters);
    }

    // Get a character by id
    @GetMapping("/{characterId}")
    public ResponseEntity<CharacterDTO> getCharacterById(@PathVariable Long playerId, @PathVariable Long characterId) {
        CharacterDTO characterDTO = characterService.getCharacterById(playerId, characterId);
        return ResponseEntity.ok(characterDTO);
    }

    // Create a new character for a player
    @PostMapping
    public ResponseEntity<CharacterDTO> createCharacter(@PathVariable Long playerId, @RequestBody CharacterDTO characterDTO) {
        CharacterDTO createdCharacter = characterService.createCharacter(playerId, characterDTO);
        return ResponseEntity.status(201).body(createdCharacter);
    }

    // Update character info
    @PatchMapping("/{characterId}")
    public ResponseEntity<CharacterDTO> updateCharacterById(@PathVariable Long playerId, @PathVariable Long characterId, @RequestBody CharacterDTO characterDTO, HttpServletRequest request) {
        CharacterDTO updatedCharacter = characterService.updateCharacterById(playerId, characterId, characterDTO, request);
        return ResponseEntity.ok(updatedCharacter);
    }

    // Delete a character
    @DeleteMapping("/{characterId}")
    public ResponseEntity<Void> deleteCharacterById(@PathVariable Long playerId, @PathVariable Long characterId, HttpServletRequest request) {
        characterService.deleteCharacterById(playerId, characterId, request);
        return ResponseEntity.noContent().build();
    }

}
