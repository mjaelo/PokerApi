package org.java.poker.repository;

import org.java.poker.domain.DealWinner;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DealWinner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealWinnerRepository extends JpaRepository<DealWinner, Long> {
}
