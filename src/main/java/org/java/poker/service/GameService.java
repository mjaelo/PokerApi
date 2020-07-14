package org.java.poker.service;

import org.java.poker.domain.Game;
import org.java.poker.domain.Player;
import org.java.poker.lib.cards.card.Card;
import org.java.poker.lib.cards.hand.Hand;
import org.java.poker.lib.cards.hand.HandStrength;
import org.java.poker.repository.GameRepository;
import org.java.poker.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Game}.
 */
@Service
@Transactional
public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    private static final long NON_ACTIVE_CARD = 56;
    private static final long sum = 10;

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
                shuffleAndStart(game, game.getPlayer1Id(), game.getPlayer2Id());
                return "Joined";
            }
        }
        return "Joined";
    }

    private boolean isCardInDeck(long cardId, Player p1, Player p2, Game g) {
        if (p1.getCard1() != NON_ACTIVE_CARD && cardId == p1.getCard1()) return false;
        if (p1.getCard2() != NON_ACTIVE_CARD && cardId == p1.getCard2()) return false;
        if (p2.getCard1() != NON_ACTIVE_CARD && cardId == p2.getCard1()) return false;
        if (p2.getCard2() != NON_ACTIVE_CARD && cardId == p2.getCard2()) return false;

        if (g.getCard1() != NON_ACTIVE_CARD && g.getCard1() == cardId) return false;
        if (g.getCard2() != NON_ACTIVE_CARD && g.getCard2() == cardId) return false;
        if (g.getCard3() != NON_ACTIVE_CARD && g.getCard3() == cardId) return false;
        if (g.getCard4() != NON_ACTIVE_CARD && g.getCard4() == cardId) return false;
        if (g.getCard5() != NON_ACTIVE_CARD && g.getCard5() == cardId) return false;
        return true;
    }

    private long getRandom() {
        return (long) (1 + (Math.random() * (52 - 1)));
    }

    public void shuffleAndStart(Game game, Long player1Id, Long player2Id) {
        Player player1 = playerRepository.getOne(player1Id);
        Player player2 = playerRepository.getOne(player2Id);

        player1.setCard1(NON_ACTIVE_CARD);
        player1.setCard2(NON_ACTIVE_CARD);
        player2.setCard1(NON_ACTIVE_CARD);
        player2.setCard2(NON_ACTIVE_CARD);

        long random = getRandom();
        game.setCard1(NON_ACTIVE_CARD);
        game.setCard2(NON_ACTIVE_CARD);
        game.setCard3(NON_ACTIVE_CARD);
        game.setCard4(NON_ACTIVE_CARD);
        game.setCard5(NON_ACTIVE_CARD);

        game.setP1Pot(null);
        game.setP2Pot(null);
        game.setPot((long) 0);

        while (!isCardInDeck(random, player1, player2, game)) random = getRandom();
        player1.setCard1((long) random);
        while (!isCardInDeck(random, player1, player2, game)) random = getRandom();
        player1.setCard2((long) random);
        while (!isCardInDeck(random, player1, player2, game)) random = getRandom();
        player2.setCard1((long) random);
        while (!isCardInDeck(random, player1, player2, game)) random = getRandom();
        player2.setCard2((long) random);

        playerRepository.save(player1);
        playerRepository.save(player2);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        gameRepository.save(game);
    }

    // Sprawdzanie czy powinno się dodać następną kartę
    private boolean isNextCard(Game game) {
        if (game.getP1Pot() != null && game.getP2Pot() != null && game.getP1Pot() == game.getP2Pot() && game.getCard5() == NON_ACTIVE_CARD)
            return true;
        return false;
    }

    // Sprawdzanie czy koniec rundy (rozstrzygniecie)
    private boolean isRoundEnd(Game game) {
        if (game.getP1Pot() != null && game.getP2Pot() != null && game.getP1Pot() == game.getP2Pot() && game.getCard5() != NON_ACTIVE_CARD)
            return true;
        return false;
    }

    private void setNextCard(Game game, Long player1Id, Long player2Id) {
        Player player1 = playerRepository.getOne(player1Id);
        Player player2 = playerRepository.getOne(player2Id);


        // Dodanie pul
        game.setPot(game.getPot() + game.getP1Pot() + game.getP2Pot());
        game.setP1Pot(null);
        game.setP2Pot(null);

        long random = getRandom();
        while (!isCardInDeck(random, player1, player2, game)) random = getRandom();
        if (game.getCard1() == NON_ACTIVE_CARD) {
            game.setCard1(random);
            while (!isCardInDeck(random, player1, player2, game)) random = getRandom();
            game.setCard2(random);
            while (!isCardInDeck(random, player1, player2, game)) random = getRandom();
            game.setCard3(random);
        } else if (game.getCard4() == NON_ACTIVE_CARD) game.setCard4(random);
        else if (game.getCard5() == NON_ACTIVE_CARD) game.setCard5(random);

        gameRepository.save(game);
    }

    public void check(Game game, long playerId) {
        if (playerId == game.getPlayer1Id()) game.setP1Pot((long) 0);
        else if (playerId == game.getPlayer2Id()) game.setP2Pot((long) 0);
        else throw new IllegalStateException("Zły ID gracza");
        gameRepository.save(game);
    }

    public void fold(Game game, long playerId) {
        if (playerId == game.getPlayer1Id()) game.setP1Pot((long) -1);
        else if (playerId == game.getPlayer2Id()) game.setP2Pot((long) -1);
        else throw new IllegalStateException("Zły ID gracza");
        gameRepository.save(game);
    }

    public void raise(Game game, long playerId, Long sum) {
        if (playerId == game.getPlayer1Id()) {
            if (game.getP1Pot() == null) game.setP1Pot((long) 0);
            game.setP1Pot(game.getP1Pot() + sum);
            Player player1 = playerRepository.getOne(playerId);
            player1.setCash(player1.getCash() - sum);
            playerRepository.save(player1);
            game.setPlayer1(player1);
        } else if (playerId == game.getPlayer2Id()) {
            if (game.getP2Pot() == null) game.setP2Pot((long) 0);
            game.setP2Pot(game.getP2Pot() + sum);
            Player player2 = playerRepository.getOne(playerId);
            player2.setCash(player2.getCash() - sum);
            playerRepository.save(player2);
            game.setPlayer2(player2);
        } else throw new IllegalStateException("Zły ID gracza");
        gameRepository.save(game);
    }

    private long getDealWinnerId(Game game) {
        if (game.getP1Pot() != null && game.getP1Pot() == -1) return game.getPlayer2Id();
        else if (game.getP2Pot() != null && game.getP2Pot() == -1) return game.getPlayer1Id();
        else {
            // Koniec rozgrywki, porównanie rąk
            Card[] p1Hand = new Card[2];
            p1Hand[0] = Card.getCard(game.getPlayer1().getCard1());
            p1Hand[1] = Card.getCard(game.getPlayer1().getCard2());

            Card[] p2Hand = new Card[2];
            p2Hand[0] = Card.getCard(game.getPlayer2().getCard1());
            p2Hand[1] = Card.getCard(game.getPlayer2().getCard2());

            Card[] table = new Card[5];
            table[0] = Card.getCard(game.getCard1());
            table[1] = Card.getCard(game.getCard2());
            table[2] = Card.getCard(game.getCard3());
            table[3] = Card.getCard(game.getCard4());
            table[4] = Card.getCard(game.getCard5());

            Hand hand1 = new Hand(p1Hand, table);
            Hand hand2 = new Hand(p2Hand, table);

            Card[] cards1 = HandStrength.getBestHand(hand1);
            int str1 = HandStrength.getCombinationStrength(cards1);
            /*System.out.println("Gracz 1 ma:");
            System.out.println(HandStrength.getHandName(str1) + " " + Arrays.toString(cards1));
            System.out.println();*/

            Card[] cards2 = HandStrength.getBestHand(hand2);
            int str2 = HandStrength.getCombinationStrength(cards2);
            /*System.out.println("Gracz 2 ma:");
            System.out.println(HandStrength.getHandName(str2) + " " + Arrays.toString(cards2));
            System.out.println();*/

            /*System.out.println("Wygrywa gracz: " + (str1 == str2? "REMIS": (str1 > str2? "1" : "2")));
            System.out.println();*/

            if (str1 == str2) return 0; // REMIS
            else if (str1 > str2) return game.getPlayer1Id(); // WIN P1
            else return game.getPlayer2Id(); // WIN P2
        }
    }

    public String getGameWinnerLog(Game game) {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        if (game.getP1Pot() != null && game.getP1Pot() == -1)
            return "Wygrał gracz: " + player2.getNickname() + " po spasowaniu gracza: " + player1.getNickname();
        else if (game.getP2Pot() != null && game.getP2Pot() == -1)
            return "Wygrał gracz: " + player1.getNickname() + " po spasowaniu gracza: " + player2.getNickname();
        else {
            // Koniec rozgrywki, porównanie rąk
            Card[] p1Hand = new Card[2];
            p1Hand[0] = Card.getCard(game.getPlayer1().getCard1());
            p1Hand[1] = Card.getCard(game.getPlayer1().getCard2());

            Card[] p2Hand = new Card[2];
            p2Hand[0] = Card.getCard(game.getPlayer2().getCard1());
            p2Hand[1] = Card.getCard(game.getPlayer2().getCard2());

            Card[] table = new Card[5];
            table[0] = Card.getCard(game.getCard1());
            table[1] = Card.getCard(game.getCard2());
            table[2] = Card.getCard(game.getCard3());
            table[3] = Card.getCard(game.getCard4());
            table[4] = Card.getCard(game.getCard5());

            Hand hand1 = new Hand(p1Hand, table);
            Hand hand2 = new Hand(p2Hand, table);

            Card[] cards1 = HandStrength.getBestHand(hand1);
            int str1 = HandStrength.getCombinationStrength(cards1);
            /*System.out.println("Gracz 1 ma:");
            System.out.println(HandStrength.getHandName(str1) + " " + Arrays.toString(cards1));
            System.out.println();*/

            Card[] cards2 = HandStrength.getBestHand(hand2);
            int str2 = HandStrength.getCombinationStrength(cards2);
            /*System.out.println("Gracz 2 ma:");
            System.out.println(HandStrength.getHandName(str2) + " " + Arrays.toString(cards2));
            System.out.println();*/

            /*System.out.println("Wygrywa gracz: " + (str1 == str2? "REMIS": (str1 > str2? "1" : "2")));
            System.out.println();*/
            return "Gracz " + player1.getNickname() + " ma: " + HandStrength.getHandName(str1) + " " + Arrays.toString(cards1) + "<br>" +
                "Gracz " + player2.getNickname() + " ma: " + HandStrength.getHandName(str2) + " " + Arrays.toString(cards2) + "<br>" +
                "Wygrywa gracz: " + (str1 == str2 ? "REMIS" : (str1 > str2 ? player1.getNickname() : player2.getNickname()));
        }
    }


    public Optional<Game> refreshGame(Long gameId) {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            if (isNextCard(game)) {
                setNextCard(game, game.getPlayer1Id(), game.getPlayer2Id());
            } else {
                return gameRepository.findById(gameId);
            }
        }
        return gameRepository.findById(gameId);
    }

    /**
     * Metoda sprawdza, czy trwa rozdanie. Jeżeli nie, należy sprawdzić zwycięzcę
     *
     * @param game
     */
    private boolean isDealInProgress(Game game) {
        if (game.getP1Pot() != null && game.getP1Pot() == -1) return false;
        else if (game.getP2Pot() != null && game.getP2Pot() == -1) return false;
        else if (game.getCard1() != NON_ACTIVE_CARD && game.getCard2() != NON_ACTIVE_CARD &&
            game.getCard3() != NON_ACTIVE_CARD && game.getCard4() != NON_ACTIVE_CARD && game.getCard5() != NON_ACTIVE_CARD &&
            game.getP1Pot() != null && game.getP2Pot() != null && game.getP1Pot() == game.getP2Pot()) return false;
        else return true;
    }

    /**
     * Sprawdza czy jest w trakcie gry, jeżeli nie to wyłania zwycięzcę
     */
    public boolean getDealProgress(Long gameId) {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            if (isDealInProgress(game)) {
                return true;
            } else {
                return false;
            }
        } else return false;
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
