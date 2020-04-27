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
    private PlayerRepository playerRepository;

    private Game createGame = new Game();

    public GameService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    public String joinPlayer(String playerId) {
        Player player = playerRepository.getOne(Long.parseLong(playerId));

        List<Game> games = gameRepository.findAll();

        if (games.isEmpty()) {
            Game game = new Game();
            game.setPlayer1Id(null);
            game.setPlayer2Id(null);
            games.add(game);
        }

        for (Game game : games) {
            if (game.getPlayer1Id() == null) {
                game.setPlayer1Id(player.getId());
                game.setPlayer1(player);
                gameRepository.save(game);
                return "Joined";
            } else if (game.getPlayer2Id() == null) {
                game.setPlayer2Id(player.getId());
                game.setPlayer2(player);
                gameRepository.save(game);
                return "Joined";
            }
        }
        return "Joined";
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
