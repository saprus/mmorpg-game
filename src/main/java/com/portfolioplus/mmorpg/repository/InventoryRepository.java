package com.portfolioplus.mmorpg.repository;

import com.portfolioplus.mmorpg.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByCharacterId(Long characterId);
}
