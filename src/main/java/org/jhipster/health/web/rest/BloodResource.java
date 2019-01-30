package org.jhipster.health.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.health.domain.Blood;
import org.jhipster.health.repository.BloodRepository;
import org.jhipster.health.web.rest.errors.BadRequestAlertException;
import org.jhipster.health.web.rest.util.HeaderUtil;
import org.jhipster.health.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Blood.
 */
@RestController
@RequestMapping("/api")
public class BloodResource {

    private final Logger log = LoggerFactory.getLogger(BloodResource.class);

    private static final String ENTITY_NAME = "blood";

    private final BloodRepository bloodRepository;

    public BloodResource(BloodRepository bloodRepository) {
        this.bloodRepository = bloodRepository;
    }

    /**
     * POST  /blood : Create a new blood.
     *
     * @param blood the blood to create
     * @return the ResponseEntity with status 201 (Created) and with body the new blood, or with status 400 (Bad Request) if the blood has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/blood")
    @Timed
    public ResponseEntity<Blood> createBlood(@Valid @RequestBody Blood blood) throws URISyntaxException {
        log.debug("REST request to save Blood : {}", blood);
        if (blood.getId() != null) {
            throw new BadRequestAlertException("A new blood cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Blood result = bloodRepository.save(blood);
        return ResponseEntity.created(new URI("/api/blood/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /blood : Updates an existing blood.
     *
     * @param blood the blood to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated blood,
     * or with status 400 (Bad Request) if the blood is not valid,
     * or with status 500 (Internal Server Error) if the blood couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/blood")
    @Timed
    public ResponseEntity<Blood> updateBlood(@Valid @RequestBody Blood blood) throws URISyntaxException {
        log.debug("REST request to update Blood : {}", blood);
        if (blood.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Blood result = bloodRepository.save(blood);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, blood.getId().toString()))
            .body(result);
    }

    /**
     * GET  /blood : get all the blood.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of blood in body
     */
    @GetMapping("/blood")
    @Timed
    public ResponseEntity<List<Blood>> getAllBlood(Pageable pageable) {
        log.debug("REST request to get a page of Blood");
        Page<Blood> page = bloodRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blood");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /blood/:id : get the "id" blood.
     *
     * @param id the id of the blood to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the blood, or with status 404 (Not Found)
     */
    @GetMapping("/blood/{id}")
    @Timed
    public ResponseEntity<Blood> getBlood(@PathVariable Long id) {
        log.debug("REST request to get Blood : {}", id);
        Optional<Blood> blood = bloodRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(blood);
    }

    /**
     * DELETE  /blood/:id : delete the "id" blood.
     *
     * @param id the id of the blood to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/blood/{id}")
    @Timed
    public ResponseEntity<Void> deleteBlood(@PathVariable Long id) {
        log.debug("REST request to delete Blood : {}", id);

        bloodRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
