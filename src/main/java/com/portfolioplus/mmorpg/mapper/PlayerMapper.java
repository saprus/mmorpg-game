package com.portfolioplus.mmorpg.mapper;

import com.portfolioplus.mmorpg.dto.PlayerDTO;
import com.portfolioplus.mmorpg.model.Player;
import com.portfolioplus.mmorpg.model.Role;

public class PlayerMapper {

    // Going from Entity/DB to DTO
    public static PlayerDTO toDTO(Player player) {
        if (player == null) return null;
        return new PlayerDTO(
                player.getId(),
                player.getUsername(),
                player.getPassword(),
                player.getEmail(),
                player.getRole()
        );
    }

    // Coming from DTO to Entity/DB
    public static Player fromDTO(PlayerDTO playerDTO) {
        if (playerDTO == null) return null;
            Player player = new Player();
            player.setUsername(playerDTO.getUsername());
            player.setPassword(playerDTO.getPassword());
            player.setEmail(playerDTO.getEmail());
            player.setRole(playerDTO.getRole() != null ? playerDTO.getRole() : Role.PLAYER);
            return player;

    }
}
