package com.portfolioplus.mmorpg.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CharacterClass {
    NOOB, BARBARIAN, WIZARD, ARCHER, HEALER;

    @JsonCreator
    public static CharacterClass fromString(String key) {
        if (key == null) return NOOB;
        return CharacterClass.valueOf(key.trim().toUpperCase());
    }
}
