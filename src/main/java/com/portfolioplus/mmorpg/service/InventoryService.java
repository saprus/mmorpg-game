package com.portfolioplus.mmorpg.service;

import com.portfolioplus.mmorpg.dto.InventoryDTO;
import com.portfolioplus.mmorpg.model.Character;
import com.portfolioplus.mmorpg.model.Inventory;
import com.portfolioplus.mmorpg.repository.CharacterRepository;
import com.portfolioplus.mmorpg.repository.InventoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static com.portfolioplus.mmorpg.mapper.InventoryMapper.fromDTO;
import static com.portfolioplus.mmorpg.mapper.InventoryMapper.toDTO;
import static com.portfolioplus.mmorpg.utils.Utils.isNullOrEmpty;

@Service
public class InventoryService {

    private InventoryRepository inventoryRepository;
    private CharacterRepository characterRepository;
    private AuthContextService authContextService;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, CharacterRepository characterRepository, AuthContextService authContextService) {
        this.inventoryRepository = inventoryRepository;
        this.characterRepository = characterRepository;
        this.authContextService = authContextService;
    }

    // Gets an inventory item by its ID and validate ownership
    public InventoryDTO getItemById(Long characterId, Long inventoryId) {
        Inventory item = inventoryRepository.findById(inventoryId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Inventory item not found, inventoryId: " + inventoryId
        ));
        validateInventoryOwnership(characterId, item);
        return toDTO(item);
    }

    // Gets all inventory items for a specific character
    public List<InventoryDTO> getAllItemsByCharacterId(Long characterId) {
        // Checking whether characterId exists
        characterRepository.findById(characterId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Character not found, characterId: " + characterId
        ));
        List<Inventory> itemList = inventoryRepository.findByCharacterId(characterId);
        List<InventoryDTO> itemListDTO = new ArrayList<>();
        for(Inventory item : itemList) {
            itemListDTO.add(toDTO(item));
        }
        return itemListDTO;
    }

    // Creates a new inventory item for a specific character
    public InventoryDTO createItemByCharacterId(Long characterId, InventoryDTO inventoryDTO) {
        // Checking whether characterId even exists
        Character character = characterRepository.findById(characterId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Character not found, characterId: " + characterId
        ));
        if (isNullOrEmpty(inventoryDTO.getItemName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ItemName cannot be empty");
        }
        Inventory savedItem = inventoryRepository.save(fromDTO(inventoryDTO, character));
        return toDTO(savedItem);
    }

    // Deletes an inventory item by its ID after validating ownership and permissions
    public void deleteItemById(Long characterId, Long inventoryId, HttpServletRequest request) {
        Inventory item = inventoryRepository.findById(inventoryId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Inventory item not found, inventoryId: " + inventoryId
        ));
        authContextService.requireMatchOrAdmin(request, item.getCharacter().getPlayer().getId());

        validateInventoryOwnership(characterId, item);
        inventoryRepository.delete(item);
    }

    // Helper method to check if the Inventory item belongs to characterId
    private void validateInventoryOwnership(Long characterId, Inventory item) {
        if (!item.getCharacter().getId().equals(characterId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Inventory does not belong to the specified character, characterId: " + characterId
            );
        }
    }
}


















