package com.portfolioplus.mmorpg.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.portfolioplus.mmorpg.model.ItemType;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryDTO {

    private Long id;
    private String itemName;
    private ItemType itemType;
    private Long characterId;

    public InventoryDTO() {}

    public InventoryDTO(Long id, String itemName, ItemType itemType, Long characterId) {
        this.id = id;
        this.itemName = itemName;
        this.itemType = itemType;
        this.characterId = characterId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Long characterId) {
        this.characterId = characterId;
    }
}
