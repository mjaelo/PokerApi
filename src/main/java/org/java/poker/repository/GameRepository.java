package org.java.poker.repository;

import org.java.poker.domain.Game;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Game entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByPlayer1Id(Long playerId);
    Optional<Game> findByPlayer2Id(Long playerId);
}
