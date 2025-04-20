package com.portfolioplus.mmorpg.mapper;

import com.portfolioplus.mmorpg.dto.InventoryDTO;
import com.portfolioplus.mmorpg.model.Inventory;
import com.portfolioplus.mmorpg.model.Character;
import com.portfolioplus.mmorpg.model.ItemType;

public class InventoryMapper {

    // Going from Entity/DB to DTO
    public static InventoryDTO toDTO(Inventory inventory) {
        if (inventory == null) return null;
        return new InventoryDTO(
                inventory.getId(),
                inventory.getItemName(),
                inventory.getItemType(),
                inventory.getCharacter().getId()
        );
    }

    // Coming from DTO to Entity/DB
    public static Inventory fromDTO(InventoryDTO inventoryDTO, Character character) {
        if (inventoryDTO == null) return null;
        Inventory item = new Inventory();
        item.setItemName(inventoryDTO.getItemName());
        item.setItemType(inventoryDTO.getItemType() != null ? inventoryDTO.getItemType() : ItemType.SWORD);
        item.setCharacter(character);
        return item;
    }
}
