package com.portfolioplus.mmorpg.mapper;

import com.portfolioplus.mmorpg.dto.CharacterDTO;
import com.portfolioplus.mmorpg.model.Character;
import com.portfolioplus.mmorpg.model.CharacterClass;
import com.portfolioplus.mmorpg.model.Player;

public class CharacterMapper {

    // Going from Entity/DB to DTO
    public static CharacterDTO toDTO(Character character) {
        if (character == null) return null;
        return new CharacterDTO(
                character.getId(),
                character.getName(),
                character.getCharacterClass(),
                character.getLevel(),
                character.getPlayer().getId()
                //character.getPlayer().getUsername()
        );
    }

    // Coming from DTO to Entity/DB
    public static Character fromDTO(CharacterDTO characterDTO, Player player) {
        if (characterDTO == null) return null;
        Character character = new Character();
        character.setName(characterDTO.getName());
        character.setCharacterClass(characterDTO.getCharacterClass() != null ? characterDTO.getCharacterClass() : CharacterClass.NOOB);
        character.setLevel(characterDTO.getLevel() != null ? characterDTO.getLevel() : 0);
        character.setPlayer(player);
        return character;
    }
}
