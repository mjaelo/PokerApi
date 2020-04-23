package org.java.poker.web.rest;

import org.java.poker.PokerApp;
import org.java.poker.domain.Game;
import org.java.poker.repository.GameRepository;
import org.java.poker.service.GameService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GameResource} REST controller.
 */
@SpringBootTest(classes = PokerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class GameResourceIT {

    private static final Long DEFAULT_CARD_1 = 1L;
    private static final Long UPDATED_CARD_1 = 2L;

    private static final Long DEFAULT_CARD_2 = 1L;
    private static final Long UPDATED_CARD_2 = 2L;

    private static final Long DEFAULT_CARD_3 = 1L;
    private static final Long UPDATED_CARD_3 = 2L;

    private static final Long DEFAULT_CARD_4 = 1L;
    private static final Long UPDATED_CARD_4 = 2L;

    private static final Long DEFAULT_CARD_5 = 1L;
    private static final Long UPDATED_CARD_5 = 2L;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameMockMvc;

    private Game game;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Game createEntity(EntityManager em) {
        Game game = new Game()
            .card1(DEFAULT_CARD_1)
            .card2(DEFAULT_CARD_2)
            .card3(DEFAULT_CARD_3)
            .card4(DEFAULT_CARD_4)
            .card5(DEFAULT_CARD_5);
        return game;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Game createUpdatedEntity(EntityManager em) {
        Game game = new Game()
            .card1(UPDATED_CARD_1)
            .card2(UPDATED_CARD_2)
            .card3(UPDATED_CARD_3)
            .card4(UPDATED_CARD_4)
            .card5(UPDATED_CARD_5);
        return game;
    }

    @BeforeEach
    public void initTest() {
        game = createEntity(em);
    }

    @Test
    @Transactional
    public void createGame() throws Exception {
        int databaseSizeBeforeCreate = gameRepository.findAll().size();

        // Create the Game
        restGameMockMvc.perform(post("/api/games").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isCreated());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate + 1);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getCard1()).isEqualTo(DEFAULT_CARD_1);
        assertThat(testGame.getCard2()).isEqualTo(DEFAULT_CARD_2);
        assertThat(testGame.getCard3()).isEqualTo(DEFAULT_CARD_3);
        assertThat(testGame.getCard4()).isEqualTo(DEFAULT_CARD_4);
        assertThat(testGame.getCard5()).isEqualTo(DEFAULT_CARD_5);
    }

    @Test
    @Transactional
    public void createGameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameRepository.findAll().size();

        // Create the Game with an existing ID
        game.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameMockMvc.perform(post("/api/games").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGames() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get all the gameList
        restGameMockMvc.perform(get("/api/games?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(game.getId().intValue())))
            .andExpect(jsonPath("$.[*].card1").value(hasItem(DEFAULT_CARD_1.intValue())))
            .andExpect(jsonPath("$.[*].card2").value(hasItem(DEFAULT_CARD_2.intValue())))
            .andExpect(jsonPath("$.[*].card3").value(hasItem(DEFAULT_CARD_3.intValue())))
            .andExpect(jsonPath("$.[*].card4").value(hasItem(DEFAULT_CARD_4.intValue())))
            .andExpect(jsonPath("$.[*].card5").value(hasItem(DEFAULT_CARD_5.intValue())));
    }
    
    @Test
    @Transactional
    public void getGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get the game
        restGameMockMvc.perform(get("/api/games/{id}", game.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(game.getId().intValue()))
            .andExpect(jsonPath("$.card1").value(DEFAULT_CARD_1.intValue()))
            .andExpect(jsonPath("$.card2").value(DEFAULT_CARD_2.intValue()))
            .andExpect(jsonPath("$.card3").value(DEFAULT_CARD_3.intValue()))
            .andExpect(jsonPath("$.card4").value(DEFAULT_CARD_4.intValue()))
            .andExpect(jsonPath("$.card5").value(DEFAULT_CARD_5.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGame() throws Exception {
        // Get the game
        restGameMockMvc.perform(get("/api/games/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGame() throws Exception {
        // Initialize the database
        gameService.save(game);

        int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // Update the game
        Game updatedGame = gameRepository.findById(game.getId()).get();
        // Disconnect from session so that the updates on updatedGame are not directly saved in db
        em.detach(updatedGame);
        updatedGame
            .card1(UPDATED_CARD_1)
            .card2(UPDATED_CARD_2)
            .card3(UPDATED_CARD_3)
            .card4(UPDATED_CARD_4)
            .card5(UPDATED_CARD_5);

        restGameMockMvc.perform(put("/api/games").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGame)))
            .andExpect(status().isOk());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getCard1()).isEqualTo(UPDATED_CARD_1);
        assertThat(testGame.getCard2()).isEqualTo(UPDATED_CARD_2);
        assertThat(testGame.getCard3()).isEqualTo(UPDATED_CARD_3);
        assertThat(testGame.getCard4()).isEqualTo(UPDATED_CARD_4);
        assertThat(testGame.getCard5()).isEqualTo(UPDATED_CARD_5);
    }

    @Test
    @Transactional
    public void updateNonExistingGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // Create the Game

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameMockMvc.perform(put("/api/games").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(game)))
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGame() throws Exception {
        // Initialize the database
        gameService.save(game);

        int databaseSizeBeforeDelete = gameRepository.findAll().size();

        // Delete the game
        restGameMockMvc.perform(delete("/api/games/{id}", game.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
