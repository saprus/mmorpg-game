package com.portfolioplus.mmorpg.service;

import com.portfolioplus.mmorpg.model.Player;
import com.portfolioplus.mmorpg.model.Role;
import com.portfolioplus.mmorpg.repository.PlayerRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.portfolioplus.mmorpg.utils.Utils.isNullOrEmpty;

@Service
public class AuthContextService {

    private PlayerRepository playerRepository;

    @Autowired
    public AuthContextService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player getCurrentPlayerFromHeader(HttpServletRequest request) {
        // Verify the header
        String playerIdHeader = request.getHeader("X-Player-Id");
        if (isNullOrEmpty(playerIdHeader)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing X-Player-Id header");
        }

        try {
            Long playerId = Long.parseLong(playerIdHeader);
            return playerRepository.findById(playerId).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid X-Player-Id: Doesn't exist"
            ));

        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "X-Player-Id must be a number");
        }
    }

    // To check if current user is ADMIN
    public void requireAdmin(HttpServletRequest request) {
        Player currentPlayer = getCurrentPlayerFromHeader(request);
        if(currentPlayer.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only ADMIN can perform this action");
        }
    }

    // Since the URL has playerId as Path Variable
    // To make sure someone else cannot modify/delete others resources, this method is in place
    public void requireMatchOrAdmin(HttpServletRequest request, Long urlPlayerId) {
        Player currentPlayer = getCurrentPlayerFromHeader(request);

        if (!currentPlayer.getId().equals(urlPlayerId) && currentPlayer.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access another player's resources");
        }
    }

}
