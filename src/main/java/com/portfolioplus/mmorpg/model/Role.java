package com.portfolioplus.mmorpg.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    PLAYER, ADMIN;

    @JsonCreator
    public static Role fromString(String key) {
        if (key == null) return PLAYER;
        return Role.valueOf(key.trim().toUpperCase());
    }
}
