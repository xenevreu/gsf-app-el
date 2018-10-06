package br.com.gsf.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.gsf.domain.ReinfItemControle;
import br.com.gsf.repository.ReinfItemControleRepository;
import br.com.gsf.repository.search.ReinfItemControleSearchRepository;
import br.com.gsf.web.rest.errors.BadRequestAlertException;
import br.com.gsf.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ReinfItemControle.
 */
@RestController
@RequestMapping("/api")
public class ReinfItemControleResource {

    private final Logger log = LoggerFactory.getLogger(ReinfItemControleResource.class);

    private static final String ENTITY_NAME = "reinfItemControle";

    private final ReinfItemControleRepository reinfItemControleRepository;

    private final ReinfItemControleSearchRepository reinfItemControleSearchRepository;

    public ReinfItemControleResource(ReinfItemControleRepository reinfItemControleRepository, ReinfItemControleSearchRepository reinfItemControleSearchRepository) {
        this.reinfItemControleRepository = reinfItemControleRepository;
        this.reinfItemControleSearchRepository = reinfItemControleSearchRepository;
    }

    /**
     * POST  /reinf-item-controles : Create a new reinfItemControle.
     *
     * @param reinfItemControle the reinfItemControle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reinfItemControle, or with status 400 (Bad Request) if the reinfItemControle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reinf-item-controles")
    @Timed
    public ResponseEntity<ReinfItemControle> createReinfItemControle(@RequestBody ReinfItemControle reinfItemControle) throws URISyntaxException {
        log.debug("REST request to save ReinfItemControle : {}", reinfItemControle);
        if (reinfItemControle.getId() != null) {
            throw new BadRequestAlertException("A new reinfItemControle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReinfItemControle result = reinfItemControleRepository.save(reinfItemControle);
        reinfItemControleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/reinf-item-controles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reinf-item-controles : Updates an existing reinfItemControle.
     *
     * @param reinfItemControle the reinfItemControle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reinfItemControle,
     * or with status 400 (Bad Request) if the reinfItemControle is not valid,
     * or with status 500 (Internal Server Error) if the reinfItemControle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reinf-item-controles")
    @Timed
    public ResponseEntity<ReinfItemControle> updateReinfItemControle(@RequestBody ReinfItemControle reinfItemControle) throws URISyntaxException {
        log.debug("REST request to update ReinfItemControle : {}", reinfItemControle);
        if (reinfItemControle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReinfItemControle result = reinfItemControleRepository.save(reinfItemControle);
        reinfItemControleSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reinfItemControle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reinf-item-controles : get all the reinfItemControles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reinfItemControles in body
     */
    @GetMapping("/reinf-item-controles")
    @Timed
    public List<ReinfItemControle> getAllReinfItemControles() {
        log.debug("REST request to get all ReinfItemControles");
        return reinfItemControleRepository.findAll();
    }

    /**
     * GET  /reinf-item-controles/:id : get the "id" reinfItemControle.
     *
     * @param id the id of the reinfItemControle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reinfItemControle, or with status 404 (Not Found)
     */
    @GetMapping("/reinf-item-controles/{id}")
    @Timed
    public ResponseEntity<ReinfItemControle> getReinfItemControle(@PathVariable Long id) {
        log.debug("REST request to get ReinfItemControle : {}", id);
        Optional<ReinfItemControle> reinfItemControle = reinfItemControleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reinfItemControle);
    }

    /**
     * DELETE  /reinf-item-controles/:id : delete the "id" reinfItemControle.
     *
     * @param id the id of the reinfItemControle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reinf-item-controles/{id}")
    @Timed
    public ResponseEntity<Void> deleteReinfItemControle(@PathVariable Long id) {
        log.debug("REST request to delete ReinfItemControle : {}", id);

        reinfItemControleRepository.deleteById(id);
        reinfItemControleSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/reinf-item-controles?query=:query : search for the reinfItemControle corresponding
     * to the query.
     *
     * @param query the query of the reinfItemControle search
     * @return the result of the search
     */
    @GetMapping("/_search/reinf-item-controles")
    @Timed
    public List<ReinfItemControle> searchReinfItemControles(@RequestParam String query) {
        log.debug("REST request to search ReinfItemControles for query {}", query);
        return StreamSupport
            .stream(reinfItemControleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
