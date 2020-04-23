package org.java.poker.service;

import org.java.poker.domain.DealWinner;
import org.java.poker.repository.DealWinnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DealWinner}.
 */
@Service
@Transactional
public class DealWinnerService {

    private final Logger log = LoggerFactory.getLogger(DealWinnerService.class);

    private final DealWinnerRepository dealWinnerRepository;

    public DealWinnerService(DealWinnerRepository dealWinnerRepository) {
        this.dealWinnerRepository = dealWinnerRepository;
    }

    /**
     * Save a dealWinner.
     *
     * @param dealWinner the entity to save.
     * @return the persisted entity.
     */
    public DealWinner save(DealWinner dealWinner) {
        log.debug("Request to save DealWinner : {}", dealWinner);
        return dealWinnerRepository.save(dealWinner);
    }

    /**
     * Get all the dealWinners.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DealWinner> findAll() {
        log.debug("Request to get all DealWinners");
        return dealWinnerRepository.findAll();
    }

    /**
     * Get one dealWinner by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DealWinner> findOne(Long id) {
        log.debug("Request to get DealWinner : {}", id);
        return dealWinnerRepository.findById(id);
    }

    /**
     * Delete the dealWinner by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DealWinner : {}", id);
        dealWinnerRepository.deleteById(id);
    }
}
