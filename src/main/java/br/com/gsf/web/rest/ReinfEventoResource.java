package br.com.gsf.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.gsf.domain.ReinfEvento;
import br.com.gsf.repository.ReinfEventoRepository;
import br.com.gsf.repository.search.ReinfEventoSearchRepository;
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
 * REST controller for managing ReinfEvento.
 */
@RestController
@RequestMapping("/api")
public class ReinfEventoResource {

    private final Logger log = LoggerFactory.getLogger(ReinfEventoResource.class);

    private static final String ENTITY_NAME = "reinfEvento";

    private final ReinfEventoRepository reinfEventoRepository;

    private final ReinfEventoSearchRepository reinfEventoSearchRepository;

    public ReinfEventoResource(ReinfEventoRepository reinfEventoRepository, ReinfEventoSearchRepository reinfEventoSearchRepository) {
        this.reinfEventoRepository = reinfEventoRepository;
        this.reinfEventoSearchRepository = reinfEventoSearchRepository;
    }

    /**
     * POST  /reinf-eventos : Create a new reinfEvento.
     *
     * @param reinfEvento the reinfEvento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reinfEvento, or with status 400 (Bad Request) if the reinfEvento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reinf-eventos")
    @Timed
    public ResponseEntity<ReinfEvento> createReinfEvento(@Valid @RequestBody ReinfEvento reinfEvento) throws URISyntaxException {
        log.debug("REST request to save ReinfEvento : {}", reinfEvento);
        if (reinfEvento.getId() != null) {
            throw new BadRequestAlertException("A new reinfEvento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReinfEvento result = reinfEventoRepository.save(reinfEvento);
        reinfEventoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/reinf-eventos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reinf-eventos : Updates an existing reinfEvento.
     *
     * @param reinfEvento the reinfEvento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reinfEvento,
     * or with status 400 (Bad Request) if the reinfEvento is not valid,
     * or with status 500 (Internal Server Error) if the reinfEvento couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reinf-eventos")
    @Timed
    public ResponseEntity<ReinfEvento> updateReinfEvento(@Valid @RequestBody ReinfEvento reinfEvento) throws URISyntaxException {
        log.debug("REST request to update ReinfEvento : {}", reinfEvento);
        if (reinfEvento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReinfEvento result = reinfEventoRepository.save(reinfEvento);
        reinfEventoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reinfEvento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reinf-eventos : get all the reinfEventos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reinfEventos in body
     */
    @GetMapping("/reinf-eventos")
    @Timed
    public List<ReinfEvento> getAllReinfEventos() {
        log.debug("REST request to get all ReinfEventos");
        return reinfEventoRepository.findAll();
    }

    /**
     * GET  /reinf-eventos/:id : get the "id" reinfEvento.
     *
     * @param id the id of the reinfEvento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reinfEvento, or with status 404 (Not Found)
     */
    @GetMapping("/reinf-eventos/{id}")
    @Timed
    public ResponseEntity<ReinfEvento> getReinfEvento(@PathVariable Long id) {
        log.debug("REST request to get ReinfEvento : {}", id);
        Optional<ReinfEvento> reinfEvento = reinfEventoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reinfEvento);
    }

    /**
     * DELETE  /reinf-eventos/:id : delete the "id" reinfEvento.
     *
     * @param id the id of the reinfEvento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reinf-eventos/{id}")
    @Timed
    public ResponseEntity<Void> deleteReinfEvento(@PathVariable Long id) {
        log.debug("REST request to delete ReinfEvento : {}", id);

        reinfEventoRepository.deleteById(id);
        reinfEventoSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/reinf-eventos?query=:query : search for the reinfEvento corresponding
     * to the query.
     *
     * @param query the query of the reinfEvento search
     * @return the result of the search
     */
    @GetMapping("/_search/reinf-eventos")
    @Timed
    public List<ReinfEvento> searchReinfEventos(@RequestParam String query) {
        log.debug("REST request to search ReinfEventos for query {}", query);
        return StreamSupport
            .stream(reinfEventoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
