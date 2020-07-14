package org.java.poker.web.rest;

import org.java.poker.domain.DealWinner;
import org.java.poker.service.DealWinnerService;
import org.java.poker.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.java.poker.domain.DealWinner}.
 */
@RestController
@RequestMapping("/api")
public class DealWinnerResource {

    private final Logger log = LoggerFactory.getLogger(DealWinnerResource.class);

    private static final String ENTITY_NAME = "dealWinner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealWinnerService dealWinnerService;

    public DealWinnerResource(DealWinnerService dealWinnerService) {
        this.dealWinnerService = dealWinnerService;
    }

    /**
     * {@code POST  /deal-winners} : Create a new dealWinner.
     *
     * @param dealWinner the dealWinner to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dealWinner, or with status {@code 400 (Bad Request)} if the dealWinner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deal-winners")
    public ResponseEntity<DealWinner> createDealWinner(@RequestBody DealWinner dealWinner) throws URISyntaxException {
        log.debug("REST request to save DealWinner : {}", dealWinner);
        if (dealWinner.getId() != null) {
            throw new BadRequestAlertException("A new dealWinner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DealWinner result = dealWinnerService.save(dealWinner);
        return ResponseEntity.created(new URI("/api/deal-winners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deal-winners} : Updates an existing dealWinner.
     *
     * @param dealWinner the dealWinner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealWinner,
     * or with status {@code 400 (Bad Request)} if the dealWinner is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dealWinner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deal-winners")
    public ResponseEntity<DealWinner> updateDealWinner(@RequestBody DealWinner dealWinner) throws URISyntaxException {
        log.debug("REST request to update DealWinner : {}", dealWinner);
        if (dealWinner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DealWinner result = dealWinnerService.save(dealWinner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dealWinner.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /deal-winners} : get all the dealWinners.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dealWinners in body.
     */
    @GetMapping("/deal-winners")
    public List<DealWinner> getAllDealWinners() {
        log.debug("REST request to get all DealWinners");
        return dealWinnerService.findAll();
    }

    /**
     * {@code GET  /deal-winners/:id} : get the "id" dealWinner.
     *
     * @param id the id of the dealWinner to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dealWinner, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deal-winners/{id}")
    public ResponseEntity<DealWinner> getDealWinner(@PathVariable Long id) {
        log.debug("REST request to get DealWinner : {}", id);
        Optional<DealWinner> dealWinner = dealWinnerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealWinner);
    }

    /**
     * {@code DELETE  /deal-winners/:id} : delete the "id" dealWinner.
     *
     * @param id the id of the dealWinner to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deal-winners/{id}")
    public ResponseEntity<Void> deleteDealWinner(@PathVariable Long id) {
        log.debug("REST request to delete DealWinner : {}", id);
        dealWinnerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
