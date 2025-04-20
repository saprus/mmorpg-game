package com.portfolioplus.mmorpg.repository;

import com.portfolioplus.mmorpg.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    List<Character> findByPlayerId(Long playerId);

}
