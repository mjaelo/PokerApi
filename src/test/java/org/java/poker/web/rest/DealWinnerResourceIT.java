package org.java.poker.web.rest;

import org.java.poker.PokerApp;
import org.java.poker.domain.DealWinner;
import org.java.poker.repository.DealWinnerRepository;
import org.java.poker.service.DealWinnerService;

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
 * Integration tests for the {@link DealWinnerResource} REST controller.
 */
@SpringBootTest(classes = PokerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class DealWinnerResourceIT {

    @Autowired
    private DealWinnerRepository dealWinnerRepository;

    @Autowired
    private DealWinnerService dealWinnerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDealWinnerMockMvc;

    private DealWinner dealWinner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealWinner createEntity(EntityManager em) {
        DealWinner dealWinner = new DealWinner();
        return dealWinner;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealWinner createUpdatedEntity(EntityManager em) {
        DealWinner dealWinner = new DealWinner();
        return dealWinner;
    }

    @BeforeEach
    public void initTest() {
        dealWinner = createEntity(em);
    }

    @Test
    @Transactional
    public void createDealWinner() throws Exception {
        int databaseSizeBeforeCreate = dealWinnerRepository.findAll().size();

        // Create the DealWinner
        restDealWinnerMockMvc.perform(post("/api/deal-winners").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dealWinner)))
            .andExpect(status().isCreated());

        // Validate the DealWinner in the database
        List<DealWinner> dealWinnerList = dealWinnerRepository.findAll();
        assertThat(dealWinnerList).hasSize(databaseSizeBeforeCreate + 1);
        DealWinner testDealWinner = dealWinnerList.get(dealWinnerList.size() - 1);
    }

    @Test
    @Transactional
    public void createDealWinnerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dealWinnerRepository.findAll().size();

        // Create the DealWinner with an existing ID
        dealWinner.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealWinnerMockMvc.perform(post("/api/deal-winners").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dealWinner)))
            .andExpect(status().isBadRequest());

        // Validate the DealWinner in the database
        List<DealWinner> dealWinnerList = dealWinnerRepository.findAll();
        assertThat(dealWinnerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDealWinners() throws Exception {
        // Initialize the database
        dealWinnerRepository.saveAndFlush(dealWinner);

        // Get all the dealWinnerList
        restDealWinnerMockMvc.perform(get("/api/deal-winners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealWinner.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDealWinner() throws Exception {
        // Initialize the database
        dealWinnerRepository.saveAndFlush(dealWinner);

        // Get the dealWinner
        restDealWinnerMockMvc.perform(get("/api/deal-winners/{id}", dealWinner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dealWinner.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDealWinner() throws Exception {
        // Get the dealWinner
        restDealWinnerMockMvc.perform(get("/api/deal-winners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDealWinner() throws Exception {
        // Initialize the database
        dealWinnerService.save(dealWinner);

        int databaseSizeBeforeUpdate = dealWinnerRepository.findAll().size();

        // Update the dealWinner
        DealWinner updatedDealWinner = dealWinnerRepository.findById(dealWinner.getId()).get();
        // Disconnect from session so that the updates on updatedDealWinner are not directly saved in db
        em.detach(updatedDealWinner);

        restDealWinnerMockMvc.perform(put("/api/deal-winners").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDealWinner)))
            .andExpect(status().isOk());

        // Validate the DealWinner in the database
        List<DealWinner> dealWinnerList = dealWinnerRepository.findAll();
        assertThat(dealWinnerList).hasSize(databaseSizeBeforeUpdate);
        DealWinner testDealWinner = dealWinnerList.get(dealWinnerList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingDealWinner() throws Exception {
        int databaseSizeBeforeUpdate = dealWinnerRepository.findAll().size();

        // Create the DealWinner

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealWinnerMockMvc.perform(put("/api/deal-winners").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dealWinner)))
            .andExpect(status().isBadRequest());

        // Validate the DealWinner in the database
        List<DealWinner> dealWinnerList = dealWinnerRepository.findAll();
        assertThat(dealWinnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDealWinner() throws Exception {
        // Initialize the database
        dealWinnerService.save(dealWinner);

        int databaseSizeBeforeDelete = dealWinnerRepository.findAll().size();

        // Delete the dealWinner
        restDealWinnerMockMvc.perform(delete("/api/deal-winners/{id}", dealWinner.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DealWinner> dealWinnerList = dealWinnerRepository.findAll();
        assertThat(dealWinnerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
