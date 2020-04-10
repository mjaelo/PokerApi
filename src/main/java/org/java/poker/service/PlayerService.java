package org.java.poker.service;

import org.java.poker.domain.Player;
import org.java.poker.domain.User;
import org.java.poker.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Player}.
 */
@Service
@Transactional
public class PlayerService {

    private final Logger log = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Save a player.
     *
     * @param player the entity to save.
     * @return the persisted entity.
     */
    public Player save(Player player) {
        log.debug("Request to save Player : {}", player);
        return playerRepository.save(player);
    }

    public void addPlayer(User user){
        Player player = new Player();
        player.setUser(user);
        player.setCash(Long.valueOf(1000));
        player.setNickname(user.getLogin());
        playerRepository.save(player);
    }

    /**
     * Get all the players.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Player> findAll(Pageable pageable) {
        log.debug("Request to get all Players");
        return playerRepository.findAll(pageable);
    }

    /**
     * Get one player by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Player> findOne(Long id) {
        log.debug("Request to get Player : {}", id);
        return playerRepository.findById(id);
    }

    /**
     * Delete the player by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Player : {}", id);
        playerRepository.deleteById(id);
    }

    public Optional<Player> findByUserId(Long userId) {
        return playerRepository.findByUserId(userId);
    }
}
