package com.portfolioplus.mmorpg.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ItemType {
    SWORD, GUN, POTION, ARMOUR;

    @JsonCreator
    public static ItemType fromString(String key) {
        if (key == null) return SWORD;
        return ItemType.valueOf(key.trim().toUpperCase());
    }
}
