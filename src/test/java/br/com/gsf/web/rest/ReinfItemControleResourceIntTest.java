package br.com.gsf.web.rest;

import br.com.gsf.GsfappelApp;

import br.com.gsf.domain.ReinfItemControle;
import br.com.gsf.repository.ReinfItemControleRepository;
import br.com.gsf.repository.search.ReinfItemControleSearchRepository;
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
 * Test class for the ReinfItemControleResource REST controller.
 *
 * @see ReinfItemControleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GsfappelApp.class)
public class ReinfItemControleResourceIntTest {

    @Autowired
    private ReinfItemControleRepository reinfItemControleRepository;

    /**
     * This repository is mocked in the br.com.gsf.repository.search test package.
     *
     * @see br.com.gsf.repository.search.ReinfItemControleSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReinfItemControleSearchRepository mockReinfItemControleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReinfItemControleMockMvc;

    private ReinfItemControle reinfItemControle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReinfItemControleResource reinfItemControleResource = new ReinfItemControleResource(reinfItemControleRepository, mockReinfItemControleSearchRepository);
        this.restReinfItemControleMockMvc = MockMvcBuilders.standaloneSetup(reinfItemControleResource)
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
    public static ReinfItemControle createEntity(EntityManager em) {
        ReinfItemControle reinfItemControle = new ReinfItemControle();
        return reinfItemControle;
    }

    @Before
    public void initTest() {
        reinfItemControle = createEntity(em);
    }

    @Test
    @Transactional
    public void createReinfItemControle() throws Exception {
        int databaseSizeBeforeCreate = reinfItemControleRepository.findAll().size();

        // Create the ReinfItemControle
        restReinfItemControleMockMvc.perform(post("/api/reinf-item-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinfItemControle)))
            .andExpect(status().isCreated());

        // Validate the ReinfItemControle in the database
        List<ReinfItemControle> reinfItemControleList = reinfItemControleRepository.findAll();
        assertThat(reinfItemControleList).hasSize(databaseSizeBeforeCreate + 1);
        ReinfItemControle testReinfItemControle = reinfItemControleList.get(reinfItemControleList.size() - 1);

        // Validate the ReinfItemControle in Elasticsearch
        verify(mockReinfItemControleSearchRepository, times(1)).save(testReinfItemControle);
    }

    @Test
    @Transactional
    public void createReinfItemControleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reinfItemControleRepository.findAll().size();

        // Create the ReinfItemControle with an existing ID
        reinfItemControle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReinfItemControleMockMvc.perform(post("/api/reinf-item-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinfItemControle)))
            .andExpect(status().isBadRequest());

        // Validate the ReinfItemControle in the database
        List<ReinfItemControle> reinfItemControleList = reinfItemControleRepository.findAll();
        assertThat(reinfItemControleList).hasSize(databaseSizeBeforeCreate);

        // Validate the ReinfItemControle in Elasticsearch
        verify(mockReinfItemControleSearchRepository, times(0)).save(reinfItemControle);
    }

    @Test
    @Transactional
    public void getAllReinfItemControles() throws Exception {
        // Initialize the database
        reinfItemControleRepository.saveAndFlush(reinfItemControle);

        // Get all the reinfItemControleList
        restReinfItemControleMockMvc.perform(get("/api/reinf-item-controles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reinfItemControle.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getReinfItemControle() throws Exception {
        // Initialize the database
        reinfItemControleRepository.saveAndFlush(reinfItemControle);

        // Get the reinfItemControle
        restReinfItemControleMockMvc.perform(get("/api/reinf-item-controles/{id}", reinfItemControle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reinfItemControle.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingReinfItemControle() throws Exception {
        // Get the reinfItemControle
        restReinfItemControleMockMvc.perform(get("/api/reinf-item-controles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReinfItemControle() throws Exception {
        // Initialize the database
        reinfItemControleRepository.saveAndFlush(reinfItemControle);

        int databaseSizeBeforeUpdate = reinfItemControleRepository.findAll().size();

        // Update the reinfItemControle
        ReinfItemControle updatedReinfItemControle = reinfItemControleRepository.findById(reinfItemControle.getId()).get();
        // Disconnect from session so that the updates on updatedReinfItemControle are not directly saved in db
        em.detach(updatedReinfItemControle);

        restReinfItemControleMockMvc.perform(put("/api/reinf-item-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReinfItemControle)))
            .andExpect(status().isOk());

        // Validate the ReinfItemControle in the database
        List<ReinfItemControle> reinfItemControleList = reinfItemControleRepository.findAll();
        assertThat(reinfItemControleList).hasSize(databaseSizeBeforeUpdate);
        ReinfItemControle testReinfItemControle = reinfItemControleList.get(reinfItemControleList.size() - 1);

        // Validate the ReinfItemControle in Elasticsearch
        verify(mockReinfItemControleSearchRepository, times(1)).save(testReinfItemControle);
    }

    @Test
    @Transactional
    public void updateNonExistingReinfItemControle() throws Exception {
        int databaseSizeBeforeUpdate = reinfItemControleRepository.findAll().size();

        // Create the ReinfItemControle

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReinfItemControleMockMvc.perform(put("/api/reinf-item-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinfItemControle)))
            .andExpect(status().isBadRequest());

        // Validate the ReinfItemControle in the database
        List<ReinfItemControle> reinfItemControleList = reinfItemControleRepository.findAll();
        assertThat(reinfItemControleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ReinfItemControle in Elasticsearch
        verify(mockReinfItemControleSearchRepository, times(0)).save(reinfItemControle);
    }

    @Test
    @Transactional
    public void deleteReinfItemControle() throws Exception {
        // Initialize the database
        reinfItemControleRepository.saveAndFlush(reinfItemControle);

        int databaseSizeBeforeDelete = reinfItemControleRepository.findAll().size();

        // Get the reinfItemControle
        restReinfItemControleMockMvc.perform(delete("/api/reinf-item-controles/{id}", reinfItemControle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReinfItemControle> reinfItemControleList = reinfItemControleRepository.findAll();
        assertThat(reinfItemControleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ReinfItemControle in Elasticsearch
        verify(mockReinfItemControleSearchRepository, times(1)).deleteById(reinfItemControle.getId());
    }

    @Test
    @Transactional
    public void searchReinfItemControle() throws Exception {
        // Initialize the database
        reinfItemControleRepository.saveAndFlush(reinfItemControle);
        when(mockReinfItemControleSearchRepository.search(queryStringQuery("id:" + reinfItemControle.getId())))
            .thenReturn(Collections.singletonList(reinfItemControle));
        // Search the reinfItemControle
        restReinfItemControleMockMvc.perform(get("/api/_search/reinf-item-controles?query=id:" + reinfItemControle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reinfItemControle.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReinfItemControle.class);
        ReinfItemControle reinfItemControle1 = new ReinfItemControle();
        reinfItemControle1.setId(1L);
        ReinfItemControle reinfItemControle2 = new ReinfItemControle();
        reinfItemControle2.setId(reinfItemControle1.getId());
        assertThat(reinfItemControle1).isEqualTo(reinfItemControle2);
        reinfItemControle2.setId(2L);
        assertThat(reinfItemControle1).isNotEqualTo(reinfItemControle2);
        reinfItemControle1.setId(null);
        assertThat(reinfItemControle1).isNotEqualTo(reinfItemControle2);
    }
}
