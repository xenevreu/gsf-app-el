package br.com.gsf.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.gsf.domain.ReinfStatusControle;
import br.com.gsf.repository.ReinfStatusControleRepository;
import br.com.gsf.repository.search.ReinfStatusControleSearchRepository;
import br.com.gsf.web.rest.errors.BadRequestAlertException;
import br.com.gsf.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ReinfStatusControle.
 */
@RestController
@RequestMapping("/api")
public class ReinfStatusControleResource {

    private final Logger log = LoggerFactory.getLogger(ReinfStatusControleResource.class);

    private static final String ENTITY_NAME = "reinfStatusControle";

    private final ReinfStatusControleRepository reinfStatusControleRepository;

    private final ReinfStatusControleSearchRepository reinfStatusControleSearchRepository;

    public ReinfStatusControleResource(ReinfStatusControleRepository reinfStatusControleRepository, ReinfStatusControleSearchRepository reinfStatusControleSearchRepository) {
        this.reinfStatusControleRepository = reinfStatusControleRepository;
        this.reinfStatusControleSearchRepository = reinfStatusControleSearchRepository;
    }

    /**
     * POST  /reinf-status-controles : Create a new reinfStatusControle.
     *
     * @param reinfStatusControle the reinfStatusControle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reinfStatusControle, or with status 400 (Bad Request) if the reinfStatusControle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reinf-status-controles")
    @Timed
    public ResponseEntity<ReinfStatusControle> createReinfStatusControle(@Valid @RequestBody ReinfStatusControle reinfStatusControle) throws URISyntaxException {
        log.debug("REST request to save ReinfStatusControle : {}", reinfStatusControle);
        if (reinfStatusControle.getId() != null) {
            throw new BadRequestAlertException("A new reinfStatusControle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReinfStatusControle result = reinfStatusControleRepository.save(reinfStatusControle);
        reinfStatusControleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/reinf-status-controles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reinf-status-controles : Updates an existing reinfStatusControle.
     *
     * @param reinfStatusControle the reinfStatusControle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reinfStatusControle,
     * or with status 400 (Bad Request) if the reinfStatusControle is not valid,
     * or with status 500 (Internal Server Error) if the reinfStatusControle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reinf-status-controles")
    @Timed
    public ResponseEntity<ReinfStatusControle> updateReinfStatusControle(@Valid @RequestBody ReinfStatusControle reinfStatusControle) throws URISyntaxException {
        log.debug("REST request to update ReinfStatusControle : {}", reinfStatusControle);
        if (reinfStatusControle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReinfStatusControle result = reinfStatusControleRepository.save(reinfStatusControle);
        reinfStatusControleSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reinfStatusControle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reinf-status-controles : get all the reinfStatusControles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reinfStatusControles in body
     */
    @GetMapping("/reinf-status-controles")
    @Timed
    public List<ReinfStatusControle> getAllReinfStatusControles() {
        log.debug("REST request to get all ReinfStatusControles");
        return reinfStatusControleRepository.findAll();
    }

    /**
     * GET  /reinf-status-controles/:id : get the "id" reinfStatusControle.
     *
     * @param id the id of the reinfStatusControle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reinfStatusControle, or with status 404 (Not Found)
     */
    @GetMapping("/reinf-status-controles/{id}")
    @Timed
    public ResponseEntity<ReinfStatusControle> getReinfStatusControle(@PathVariable Long id) {
        log.debug("REST request to get ReinfStatusControle : {}", id);
        Optional<ReinfStatusControle> reinfStatusControle = reinfStatusControleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reinfStatusControle);
    }

    /**
     * DELETE  /reinf-status-controles/:id : delete the "id" reinfStatusControle.
     *
     * @param id the id of the reinfStatusControle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reinf-status-controles/{id}")
    @Timed
    public ResponseEntity<Void> deleteReinfStatusControle(@PathVariable Long id) {
        log.debug("REST request to delete ReinfStatusControle : {}", id);

        reinfStatusControleRepository.deleteById(id);
        reinfStatusControleSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/reinf-status-controles?query=:query : search for the reinfStatusControle corresponding
     * to the query.
     *
     * @param query the query of the reinfStatusControle search
     * @return the result of the search
     */
    @GetMapping("/_search/reinf-status-controles")
    @Timed
    public List<ReinfStatusControle> searchReinfStatusControles(@RequestParam String query) {
        log.debug("REST request to search ReinfStatusControles for query {}", query);
        return StreamSupport
            .stream(reinfStatusControleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
