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
import java.util.Random;

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
                shuffleAndStart(game, game.getPlayer1Id(), game.getPlayer2Id());
                return "Joined";
            }
        }
        return "Joined";
    }

    private void shuffleAndStart(Game game, Long player1Id, Long player2Id){
        int random = (int) (1 + (Math.random() * (52-1)));
        game.setCard1((long) random);
        random = (int) (1 + (Math.random() * (52-1)));
        game.setCard2((long) random);
        random = (int) (1 + (Math.random() * (52-1)));
        game.setCard3((long) random);
        random = (int) (1 + (Math.random() * (52-1)));
        game.setCard4((long) random);
        random = (int) (1 + (Math.random() * (52-1)));
        game.setCard5((long) random);

        Player player1 = playerRepository.getOne(player1Id);
        Player player2 = playerRepository.getOne(player2Id);

        player1.setCard1((long) (1 + (Math.random() * (52-1))));
        player1.setCard2((long) (1 + (Math.random() * (52-1))));
        player2.setCard1((long) (1 + (Math.random() * (52-1))));
        player2.setCard2((long) (1 + (Math.random() * (52-1))));
        playerRepository.save(player1);
        playerRepository.save(player2);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        gameRepository.save(game);
    }

    private void dealWinner(){

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

    @Transactional(readOnly = true)
    public Optional<Game> findByPlayerId(Long playerId) {
        if (gameRepository.findByPlayer1Id(playerId).isPresent()) {
            return gameRepository.findByPlayer1Id(playerId);
        } else if (gameRepository.findByPlayer2Id(playerId).isPresent()) {
            return gameRepository.findByPlayer2Id(playerId);
        } else {
            return null;
        }
    }
}
