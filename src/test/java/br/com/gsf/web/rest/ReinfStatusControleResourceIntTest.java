package br.com.gsf.web.rest;

import br.com.gsf.GsfappelApp;

import br.com.gsf.domain.ReinfStatusControle;
import br.com.gsf.repository.ReinfStatusControleRepository;
import br.com.gsf.repository.search.ReinfStatusControleSearchRepository;
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
 * Test class for the ReinfStatusControleResource REST controller.
 *
 * @see ReinfStatusControleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GsfappelApp.class)
public class ReinfStatusControleResourceIntTest {

    private static final String DEFAULT_COD_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_COD_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCR = "AAAAAAAAAA";
    private static final String UPDATED_DESCR = "BBBBBBBBBB";

    @Autowired
    private ReinfStatusControleRepository reinfStatusControleRepository;

    /**
     * This repository is mocked in the br.com.gsf.repository.search test package.
     *
     * @see br.com.gsf.repository.search.ReinfStatusControleSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReinfStatusControleSearchRepository mockReinfStatusControleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReinfStatusControleMockMvc;

    private ReinfStatusControle reinfStatusControle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReinfStatusControleResource reinfStatusControleResource = new ReinfStatusControleResource(reinfStatusControleRepository, mockReinfStatusControleSearchRepository);
        this.restReinfStatusControleMockMvc = MockMvcBuilders.standaloneSetup(reinfStatusControleResource)
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
    public static ReinfStatusControle createEntity(EntityManager em) {
        ReinfStatusControle reinfStatusControle = new ReinfStatusControle()
            .codStatus(DEFAULT_COD_STATUS)
            .descr(DEFAULT_DESCR);
        return reinfStatusControle;
    }

    @Before
    public void initTest() {
        reinfStatusControle = createEntity(em);
    }

    @Test
    @Transactional
    public void createReinfStatusControle() throws Exception {
        int databaseSizeBeforeCreate = reinfStatusControleRepository.findAll().size();

        // Create the ReinfStatusControle
        restReinfStatusControleMockMvc.perform(post("/api/reinf-status-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinfStatusControle)))
            .andExpect(status().isCreated());

        // Validate the ReinfStatusControle in the database
        List<ReinfStatusControle> reinfStatusControleList = reinfStatusControleRepository.findAll();
        assertThat(reinfStatusControleList).hasSize(databaseSizeBeforeCreate + 1);
        ReinfStatusControle testReinfStatusControle = reinfStatusControleList.get(reinfStatusControleList.size() - 1);
        assertThat(testReinfStatusControle.getCodStatus()).isEqualTo(DEFAULT_COD_STATUS);
        assertThat(testReinfStatusControle.getDescr()).isEqualTo(DEFAULT_DESCR);

        // Validate the ReinfStatusControle in Elasticsearch
        verify(mockReinfStatusControleSearchRepository, times(1)).save(testReinfStatusControle);
    }

    @Test
    @Transactional
    public void createReinfStatusControleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reinfStatusControleRepository.findAll().size();

        // Create the ReinfStatusControle with an existing ID
        reinfStatusControle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReinfStatusControleMockMvc.perform(post("/api/reinf-status-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinfStatusControle)))
            .andExpect(status().isBadRequest());

        // Validate the ReinfStatusControle in the database
        List<ReinfStatusControle> reinfStatusControleList = reinfStatusControleRepository.findAll();
        assertThat(reinfStatusControleList).hasSize(databaseSizeBeforeCreate);

        // Validate the ReinfStatusControle in Elasticsearch
        verify(mockReinfStatusControleSearchRepository, times(0)).save(reinfStatusControle);
    }

    @Test
    @Transactional
    public void checkCodStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = reinfStatusControleRepository.findAll().size();
        // set the field null
        reinfStatusControle.setCodStatus(null);

        // Create the ReinfStatusControle, which fails.

        restReinfStatusControleMockMvc.perform(post("/api/reinf-status-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinfStatusControle)))
            .andExpect(status().isBadRequest());

        List<ReinfStatusControle> reinfStatusControleList = reinfStatusControleRepository.findAll();
        assertThat(reinfStatusControleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReinfStatusControles() throws Exception {
        // Initialize the database
        reinfStatusControleRepository.saveAndFlush(reinfStatusControle);

        // Get all the reinfStatusControleList
        restReinfStatusControleMockMvc.perform(get("/api/reinf-status-controles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reinfStatusControle.getId().intValue())))
            .andExpect(jsonPath("$.[*].codStatus").value(hasItem(DEFAULT_COD_STATUS.toString())))
            .andExpect(jsonPath("$.[*].descr").value(hasItem(DEFAULT_DESCR.toString())));
    }
    
    @Test
    @Transactional
    public void getReinfStatusControle() throws Exception {
        // Initialize the database
        reinfStatusControleRepository.saveAndFlush(reinfStatusControle);

        // Get the reinfStatusControle
        restReinfStatusControleMockMvc.perform(get("/api/reinf-status-controles/{id}", reinfStatusControle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reinfStatusControle.getId().intValue()))
            .andExpect(jsonPath("$.codStatus").value(DEFAULT_COD_STATUS.toString()))
            .andExpect(jsonPath("$.descr").value(DEFAULT_DESCR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReinfStatusControle() throws Exception {
        // Get the reinfStatusControle
        restReinfStatusControleMockMvc.perform(get("/api/reinf-status-controles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReinfStatusControle() throws Exception {
        // Initialize the database
        reinfStatusControleRepository.saveAndFlush(reinfStatusControle);

        int databaseSizeBeforeUpdate = reinfStatusControleRepository.findAll().size();

        // Update the reinfStatusControle
        ReinfStatusControle updatedReinfStatusControle = reinfStatusControleRepository.findById(reinfStatusControle.getId()).get();
        // Disconnect from session so that the updates on updatedReinfStatusControle are not directly saved in db
        em.detach(updatedReinfStatusControle);
        updatedReinfStatusControle
            .codStatus(UPDATED_COD_STATUS)
            .descr(UPDATED_DESCR);

        restReinfStatusControleMockMvc.perform(put("/api/reinf-status-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReinfStatusControle)))
            .andExpect(status().isOk());

        // Validate the ReinfStatusControle in the database
        List<ReinfStatusControle> reinfStatusControleList = reinfStatusControleRepository.findAll();
        assertThat(reinfStatusControleList).hasSize(databaseSizeBeforeUpdate);
        ReinfStatusControle testReinfStatusControle = reinfStatusControleList.get(reinfStatusControleList.size() - 1);
        assertThat(testReinfStatusControle.getCodStatus()).isEqualTo(UPDATED_COD_STATUS);
        assertThat(testReinfStatusControle.getDescr()).isEqualTo(UPDATED_DESCR);

        // Validate the ReinfStatusControle in Elasticsearch
        verify(mockReinfStatusControleSearchRepository, times(1)).save(testReinfStatusControle);
    }

    @Test
    @Transactional
    public void updateNonExistingReinfStatusControle() throws Exception {
        int databaseSizeBeforeUpdate = reinfStatusControleRepository.findAll().size();

        // Create the ReinfStatusControle

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReinfStatusControleMockMvc.perform(put("/api/reinf-status-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinfStatusControle)))
            .andExpect(status().isBadRequest());

        // Validate the ReinfStatusControle in the database
        List<ReinfStatusControle> reinfStatusControleList = reinfStatusControleRepository.findAll();
        assertThat(reinfStatusControleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ReinfStatusControle in Elasticsearch
        verify(mockReinfStatusControleSearchRepository, times(0)).save(reinfStatusControle);
    }

    @Test
    @Transactional
    public void deleteReinfStatusControle() throws Exception {
        // Initialize the database
        reinfStatusControleRepository.saveAndFlush(reinfStatusControle);

        int databaseSizeBeforeDelete = reinfStatusControleRepository.findAll().size();

        // Get the reinfStatusControle
        restReinfStatusControleMockMvc.perform(delete("/api/reinf-status-controles/{id}", reinfStatusControle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReinfStatusControle> reinfStatusControleList = reinfStatusControleRepository.findAll();
        assertThat(reinfStatusControleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ReinfStatusControle in Elasticsearch
        verify(mockReinfStatusControleSearchRepository, times(1)).deleteById(reinfStatusControle.getId());
    }

    @Test
    @Transactional
    public void searchReinfStatusControle() throws Exception {
        // Initialize the database
        reinfStatusControleRepository.saveAndFlush(reinfStatusControle);
        when(mockReinfStatusControleSearchRepository.search(queryStringQuery("id:" + reinfStatusControle.getId())))
            .thenReturn(Collections.singletonList(reinfStatusControle));
        // Search the reinfStatusControle
        restReinfStatusControleMockMvc.perform(get("/api/_search/reinf-status-controles?query=id:" + reinfStatusControle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reinfStatusControle.getId().intValue())))
            .andExpect(jsonPath("$.[*].codStatus").value(hasItem(DEFAULT_COD_STATUS.toString())))
            .andExpect(jsonPath("$.[*].descr").value(hasItem(DEFAULT_DESCR.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReinfStatusControle.class);
        ReinfStatusControle reinfStatusControle1 = new ReinfStatusControle();
        reinfStatusControle1.setId(1L);
        ReinfStatusControle reinfStatusControle2 = new ReinfStatusControle();
        reinfStatusControle2.setId(reinfStatusControle1.getId());
        assertThat(reinfStatusControle1).isEqualTo(reinfStatusControle2);
        reinfStatusControle2.setId(2L);
        assertThat(reinfStatusControle1).isNotEqualTo(reinfStatusControle2);
        reinfStatusControle1.setId(null);
        assertThat(reinfStatusControle1).isNotEqualTo(reinfStatusControle2);
    }
}
