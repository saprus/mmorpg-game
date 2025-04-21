package com.portfolioplus.mmorpg.controller;

import com.portfolioplus.mmorpg.dto.InventoryDTO;
import com.portfolioplus.mmorpg.service.InventoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters/{characterId}/inventory")
public class InventoryController {

    private InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Get item by inventoryId
    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryDTO> getItemById(@PathVariable Long characterId, @PathVariable Long inventoryId) {
        InventoryDTO inventoryItem = inventoryService.getItemById(characterId, inventoryId);
        return ResponseEntity.ok(inventoryItem);
    }

    // Get all items by characterId
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllItemsByCharacterId(@PathVariable Long characterId) {
        List<InventoryDTO> inventoryItems = inventoryService.getAllItemsByCharacterId(characterId);
        return ResponseEntity.ok(inventoryItems);
    }

    // Create a new item by characterId
    @PostMapping
    public ResponseEntity<InventoryDTO> createItemByCharacterId(@PathVariable Long characterId, @RequestBody InventoryDTO inventoryDTO) {
        System.out.println("POST /characters/" + characterId + "/inventory hit");
        InventoryDTO newItem = inventoryService.createItemByCharacterId(characterId, inventoryDTO);
        return ResponseEntity.status(201).body(newItem);
    }

    // Delete the item by inventoryId
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Void> deleteItemById(@PathVariable Long characterId, @PathVariable Long inventoryId, HttpServletRequest request) {
        inventoryService.deleteItemById(characterId, inventoryId, request);
        return ResponseEntity.noContent().build();
    }

}
