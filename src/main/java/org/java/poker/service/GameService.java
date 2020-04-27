package org.java.poker.service;

import org.java.poker.domain.Game;
import org.java.poker.domain.Player;
import org.java.poker.repository.GameRepository;
import org.java.poker.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Game}.
 */
@Service
@Transactional
public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    private Game createGame = new Game();
//    private Player player = new Player();

    public GameService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    /**
     * Save a game.
     *
     * @param game the entity to save.
     * @return the persisted entity.
     */
    public Game save(Game game) {
        log.debug("Request to save Game : {}", game);
        return gameRepository.save(game);
    }

    public Game joinPlayer(long id) {
        Player player = playerRepository.getOne(id);

        if (createGame.getPlayer1() == null) {
            createGame.setPlayer1(player);
        }
        if ((createGame.getPlayer1() != null) && (createGame.getPlayer2() == null)) {
            createGame.setPlayer2(player);
            return gameRepository.save(createGame);
        }
        return null;
    }

    /**
     * Get all the games.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Game> findAll() {
        log.debug("Request to get all Games");
        return gameRepository.findAll();
    }

    /**
     * Get one game by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Game> findOne(Long id) {
        log.debug("Request to get Game : {}", id);
        return gameRepository.findById(id);
    }

    /**
     * Delete the game by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Game : {}", id);
        gameRepository.deleteById(id);
    }
}
