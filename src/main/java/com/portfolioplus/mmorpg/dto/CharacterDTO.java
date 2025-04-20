package com.portfolioplus.mmorpg.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.portfolioplus.mmorpg.model.CharacterClass;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterDTO {
    private Long id;
    private String name;
    private CharacterClass characterClass;
    private Integer level;
    private Long playerId;
    //private String playerName;

    public CharacterDTO() {
    }

    public CharacterDTO(Long id, String name, CharacterClass characterClass, Integer level, Long playerId) {
        this.id = id;
        this.name = name;
        this.characterClass = characterClass;
        this.level = level;
        this.playerId = playerId;
        //this.playerName = playerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

//    public String getPlayerName() {
//        return playerName;
//    }
//
//    public void setPlayerName(String playerName) {
//        this.playerName = playerName;
//    }
}