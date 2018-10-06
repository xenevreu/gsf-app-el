package br.com.gsf.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.gsf.domain.ReinfControle;
import br.com.gsf.repository.ReinfControleRepository;
import br.com.gsf.repository.search.ReinfControleSearchRepository;
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
 * REST controller for managing ReinfControle.
 */
@RestController
@RequestMapping("/api")
public class ReinfControleResource {

    private final Logger log = LoggerFactory.getLogger(ReinfControleResource.class);

    private static final String ENTITY_NAME = "reinfControle";

    private final ReinfControleRepository reinfControleRepository;

    private final ReinfControleSearchRepository reinfControleSearchRepository;

    public ReinfControleResource(ReinfControleRepository reinfControleRepository, ReinfControleSearchRepository reinfControleSearchRepository) {
        this.reinfControleRepository = reinfControleRepository;
        this.reinfControleSearchRepository = reinfControleSearchRepository;
    }

    /**
     * POST  /reinf-controles : Create a new reinfControle.
     *
     * @param reinfControle the reinfControle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reinfControle, or with status 400 (Bad Request) if the reinfControle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reinf-controles")
    @Timed
    public ResponseEntity<ReinfControle> createReinfControle(@RequestBody ReinfControle reinfControle) throws URISyntaxException {
        log.debug("REST request to save ReinfControle : {}", reinfControle);
        if (reinfControle.getId() != null) {
            throw new BadRequestAlertException("A new reinfControle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReinfControle result = reinfControleRepository.save(reinfControle);
        reinfControleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/reinf-controles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reinf-controles : Updates an existing reinfControle.
     *
     * @param reinfControle the reinfControle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reinfControle,
     * or with status 400 (Bad Request) if the reinfControle is not valid,
     * or with status 500 (Internal Server Error) if the reinfControle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reinf-controles")
    @Timed
    public ResponseEntity<ReinfControle> updateReinfControle(@RequestBody ReinfControle reinfControle) throws URISyntaxException {
        log.debug("REST request to update ReinfControle : {}", reinfControle);
        if (reinfControle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReinfControle result = reinfControleRepository.save(reinfControle);
        reinfControleSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reinfControle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reinf-controles : get all the reinfControles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reinfControles in body
     */
    @GetMapping("/reinf-controles")
    @Timed
    public List<ReinfControle> getAllReinfControles() {
        log.debug("REST request to get all ReinfControles");
        return reinfControleRepository.findAll();
    }

    /**
     * GET  /reinf-controles/:id : get the "id" reinfControle.
     *
     * @param id the id of the reinfControle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reinfControle, or with status 404 (Not Found)
     */
    @GetMapping("/reinf-controles/{id}")
    @Timed
    public ResponseEntity<ReinfControle> getReinfControle(@PathVariable Long id) {
        log.debug("REST request to get ReinfControle : {}", id);
        Optional<ReinfControle> reinfControle = reinfControleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reinfControle);
    }

    /**
     * DELETE  /reinf-controles/:id : delete the "id" reinfControle.
     *
     * @param id the id of the reinfControle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reinf-controles/{id}")
    @Timed
    public ResponseEntity<Void> deleteReinfControle(@PathVariable Long id) {
        log.debug("REST request to delete ReinfControle : {}", id);

        reinfControleRepository.deleteById(id);
        reinfControleSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/reinf-controles?query=:query : search for the reinfControle corresponding
     * to the query.
     *
     * @param query the query of the reinfControle search
     * @return the result of the search
     */
    @GetMapping("/_search/reinf-controles")
    @Timed
    public List<ReinfControle> searchReinfControles(@RequestParam String query) {
        log.debug("REST request to search ReinfControles for query {}", query);
        return StreamSupport
            .stream(reinfControleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
