package com.portfolioplus.mmorpg.repository;

import com.portfolioplus.mmorpg.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
