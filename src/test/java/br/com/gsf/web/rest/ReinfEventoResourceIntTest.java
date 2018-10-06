package br.com.gsf.web.rest;

import br.com.gsf.GsfappelApp;

import br.com.gsf.domain.ReinfEvento;
import br.com.gsf.repository.ReinfEventoRepository;
import br.com.gsf.repository.search.ReinfEventoSearchRepository;
import br.com.gsf.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static br.com.gsf.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReinfEventoResource REST controller.
 *
 * @see ReinfEventoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GsfappelApp.class)
public class ReinfEventoResourceIntTest {

    private static final String DEFAULT_COD_EVENTO = "AAAAAAAAAA";
    private static final String UPDATED_COD_EVENTO = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_EVENTO = "AAAAAAAAAA";
    private static final String UPDATED_DESC_EVENTO = "BBBBBBBBBB";

    @Autowired
    private ReinfEventoRepository reinfEventoRepository;

    /**
     * This repository is mocked in the br.com.gsf.repository.search test package.
     *
     * @see br.com.gsf.repository.search.ReinfEventoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReinfEventoSearchRepository mockReinfEventoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReinfEventoMockMvc;

    private ReinfEvento reinfEvento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReinfEventoResource reinfEventoResource = new ReinfEventoResource(reinfEventoRepository, mockReinfEventoSearchRepository);
        this.restReinfEventoMockMvc = MockMvcBuilders.standaloneSetup(reinfEventoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReinfEvento createEntity(EntityManager em) {
        ReinfEvento reinfEvento = new ReinfEvento()
            .codEvento(DEFAULT_COD_EVENTO)
            .descEvento(DEFAULT_DESC_EVENTO);
        return reinfEvento;
    }

    @Before
    public void initTest() {
        reinfEvento = createEntity(em);
    }

    @Test
    @Transactional
    public void createReinfEvento() throws Exception {
        int databaseSizeBeforeCreate = reinfEventoRepository.findAll().size();

        // Create the ReinfEvento
        restReinfEventoMockMvc.perform(post("/api/reinf-eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinfEvento)))
            .andExpect(status().isCreated());

        // Validate the ReinfEvento in the database
        List<ReinfEvento> reinfEventoList = reinfEventoRepository.findAll();
        assertThat(reinfEventoList).hasSize(databaseSizeBeforeCreate + 1);
        ReinfEvento testReinfEvento = reinfEventoList.get(reinfEventoList.size() - 1);
        assertThat(testReinfEvento.getCodEvento()).isEqualTo(DEFAULT_COD_EVENTO);
        assertThat(testReinfEvento.getDescEvento()).isEqualTo(DEFAULT_DESC_EVENTO);

        // Validate the ReinfEvento in Elasticsearch
        verify(mockReinfEventoSearchRepository, times(1)).save(testReinfEvento);
    }

    @Test
    @Transactional
    public void createReinfEventoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reinfEventoRepository.findAll().size();

        // Create the ReinfEvento with an existing ID
        reinfEvento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReinfEventoMockMvc.perform(post("/api/reinf-eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinfEvento)))
            .andExpect(status().isBadRequest());

        // Validate the ReinfEvento in the database
        List<ReinfEvento> reinfEventoList = reinfEventoRepository.findAll();
        assertThat(reinfEventoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ReinfEvento in Elasticsearch
        verify(mockReinfEventoSearchRepository, times(0)).save(reinfEvento);
    }

    @Test
    @Transactional
    public void getAllReinfEventos() throws Exception {
        // Initialize the database
        reinfEventoRepository.saveAndFlush(reinfEvento);

        // Get all the reinfEventoList
        restReinfEventoMockMvc.perform(get("/api/reinf-eventos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reinfEvento.getId().intValue())))
            .andExpect(jsonPath("$.[*].codEvento").value(hasItem(DEFAULT_COD_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].descEvento").value(hasItem(DEFAULT_DESC_EVENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getReinfEvento() throws Exception {
        // Initialize the database
        reinfEventoRepository.saveAndFlush(reinfEvento);

        // Get the reinfEvento
        restReinfEventoMockMvc.perform(get("/api/reinf-eventos/{id}", reinfEvento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reinfEvento.getId().intValue()))
            .andExpect(jsonPath("$.codEvento").value(DEFAULT_COD_EVENTO.toString()))
            .andExpect(jsonPath("$.descEvento").value(DEFAULT_DESC_EVENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReinfEvento() throws Exception {
        // Get the reinfEvento
        restReinfEventoMockMvc.perform(get("/api/reinf-eventos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReinfEvento() throws Exception {
        // Initialize the database
        reinfEventoRepository.saveAndFlush(reinfEvento);

        int databaseSizeBeforeUpdate = reinfEventoRepository.findAll().size();

        // Update the reinfEvento
        ReinfEvento updatedReinfEvento = reinfEventoRepository.findById(reinfEvento.getId()).get();
        // Disconnect from session so that the updates on updatedReinfEvento are not directly saved in db
        em.detach(updatedReinfEvento);
        updatedReinfEvento
            .codEvento(UPDATED_COD_EVENTO)
            .descEvento(UPDATED_DESC_EVENTO);

        restReinfEventoMockMvc.perform(put("/api/reinf-eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReinfEvento)))
            .andExpect(status().isOk());

        // Validate the ReinfEvento in the database
        List<ReinfEvento> reinfEventoList = reinfEventoRepository.findAll();
        assertThat(reinfEventoList).hasSize(databaseSizeBeforeUpdate);
        ReinfEvento testReinfEvento = reinfEventoList.get(reinfEventoList.size() - 1);
        assertThat(testReinfEvento.getCodEvento()).isEqualTo(UPDATED_COD_EVENTO);
        assertThat(testReinfEvento.getDescEvento()).isEqualTo(UPDATED_DESC_EVENTO);

        // Validate the ReinfEvento in Elasticsearch
        verify(mockReinfEventoSearchRepository, times(1)).save(testReinfEvento);
    }

    @Test
    @Transactional
    public void updateNonExistingReinfEvento() throws Exception {
        int databaseSizeBeforeUpdate = reinfEventoRepository.findAll().size();

        // Create the ReinfEvento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReinfEventoMockMvc.perform(put("/api/reinf-eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinfEvento)))
            .andExpect(status().isBadRequest());

        // Validate the ReinfEvento in the database
        List<ReinfEvento> reinfEventoList = reinfEventoRepository.findAll();
        assertThat(reinfEventoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ReinfEvento in Elasticsearch
        verify(mockReinfEventoSearchRepository, times(0)).save(reinfEvento);
    }

    @Test
    @Transactional
    public void deleteReinfEvento() throws Exception {
        // Initialize the database
        reinfEventoRepository.saveAndFlush(reinfEvento);

        int databaseSizeBeforeDelete = reinfEventoRepository.findAll().size();

        // Get the reinfEvento
        restReinfEventoMockMvc.perform(delete("/api/reinf-eventos/{id}", reinfEvento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReinfEvento> reinfEventoList = reinfEventoRepository.findAll();
        assertThat(reinfEventoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ReinfEvento in Elasticsearch
        verify(mockReinfEventoSearchRepository, times(1)).deleteById(reinfEvento.getId());
    }

    @Test
    @Transactional
    public void searchReinfEvento() throws Exception {
        // Initialize the database
        reinfEventoRepository.saveAndFlush(reinfEvento);
        when(mockReinfEventoSearchRepository.search(queryStringQuery("id:" + reinfEvento.getId())))
            .thenReturn(Collections.singletonList(reinfEvento));
        // Search the reinfEvento
        restReinfEventoMockMvc.perform(get("/api/_search/reinf-eventos?query=id:" + reinfEvento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reinfEvento.getId().intValue())))
            .andExpect(jsonPath("$.[*].codEvento").value(hasItem(DEFAULT_COD_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].descEvento").value(hasItem(DEFAULT_DESC_EVENTO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReinfEvento.class);
        ReinfEvento reinfEvento1 = new ReinfEvento();
        reinfEvento1.setId(1L);
        ReinfEvento reinfEvento2 = new ReinfEvento();
        reinfEvento2.setId(reinfEvento1.getId());
        assertThat(reinfEvento1).isEqualTo(reinfEvento2);
        reinfEvento2.setId(2L);
        assertThat(reinfEvento1).isNotEqualTo(reinfEvento2);
        reinfEvento1.setId(null);
        assertThat(reinfEvento1).isNotEqualTo(reinfEvento2);
    }
}
