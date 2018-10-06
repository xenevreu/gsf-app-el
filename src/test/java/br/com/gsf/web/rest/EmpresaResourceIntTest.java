package br.com.gsf.web.rest;

import br.com.gsf.GsfappelApp;

import br.com.gsf.domain.Empresa;
import br.com.gsf.repository.EmpresaRepository;
import br.com.gsf.repository.search.EmpresaSearchRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Test class for the EmpresaResource REST controller.
 *
 * @see EmpresaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GsfappelApp.class)
public class EmpresaResourceIntTest {

    private static final String DEFAULT_NOME_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_EMPRESA = "BBBBBBBBBB";

    private static final Instant DEFAULT_DT_INCLUSAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DT_INCLUSAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_USU_INCLUSAO = "AAAAAAAAAA";
    private static final String UPDATED_USU_INCLUSAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DT_ULT_ALTERACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DT_ULT_ALTERACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_USU_ULT_ALTERACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_USU_ULT_ALTERACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA_LA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA_LA = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEUDO_CODIGO = "A";
    private static final String UPDATED_CONTEUDO_CODIGO = "B";

    private static final Long DEFAULT_ID_UF = 1L;
    private static final Long UPDATED_ID_UF = 2L;

    private static final String DEFAULT_NOME_FANTASIA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_FANTASIA = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_LOCAL_CENTRAL = 1L;
    private static final Long UPDATED_ID_LOCAL_CENTRAL = 2L;

    private static final String DEFAULT_DS_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_DS_LOGO = "BBBBBBBBBB";

    private static final String DEFAULT_CAMINHO_LOG = "AAAAAAAAAA";
    private static final String UPDATED_CAMINHO_LOG = "BBBBBBBBBB";

    @Autowired
    private EmpresaRepository empresaRepository;

    /**
     * This repository is mocked in the br.com.gsf.repository.search test package.
     *
     * @see br.com.gsf.repository.search.EmpresaSearchRepositoryMockConfiguration
     */
    @Autowired
    private EmpresaSearchRepository mockEmpresaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmpresaMockMvc;

    private Empresa empresa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmpresaResource empresaResource = new EmpresaResource(empresaRepository, mockEmpresaSearchRepository);
        this.restEmpresaMockMvc = MockMvcBuilders.standaloneSetup(empresaResource)
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
    public static Empresa createEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .nomeEmpresa(DEFAULT_NOME_EMPRESA)
            .dtInclusao(DEFAULT_DT_INCLUSAO)
            .usuInclusao(DEFAULT_USU_INCLUSAO)
            .dtUltAlteracao(DEFAULT_DT_ULT_ALTERACAO)
            .usuUltAlteracao(DEFAULT_USU_ULT_ALTERACAO)
            .cnpj(DEFAULT_CNPJ)
            .siglaLa(DEFAULT_SIGLA_LA)
            .conteudoCodigo(DEFAULT_CONTEUDO_CODIGO)
            .idUf(DEFAULT_ID_UF)
            .nomeFantasia(DEFAULT_NOME_FANTASIA)
            .idLocalCentral(DEFAULT_ID_LOCAL_CENTRAL)
            .dsLogo(DEFAULT_DS_LOGO)
            .caminhoLog(DEFAULT_CAMINHO_LOG);
        return empresa;
    }

    @Before
    public void initTest() {
        empresa = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpresa() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // Create the Empresa
        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isCreated());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate + 1);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNomeEmpresa()).isEqualTo(DEFAULT_NOME_EMPRESA);
        assertThat(testEmpresa.getDtInclusao()).isEqualTo(DEFAULT_DT_INCLUSAO);
        assertThat(testEmpresa.getUsuInclusao()).isEqualTo(DEFAULT_USU_INCLUSAO);
        assertThat(testEmpresa.getDtUltAlteracao()).isEqualTo(DEFAULT_DT_ULT_ALTERACAO);
        assertThat(testEmpresa.getUsuUltAlteracao()).isEqualTo(DEFAULT_USU_ULT_ALTERACAO);
        assertThat(testEmpresa.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testEmpresa.getSiglaLa()).isEqualTo(DEFAULT_SIGLA_LA);
        assertThat(testEmpresa.getConteudoCodigo()).isEqualTo(DEFAULT_CONTEUDO_CODIGO);
        assertThat(testEmpresa.getIdUf()).isEqualTo(DEFAULT_ID_UF);
        assertThat(testEmpresa.getNomeFantasia()).isEqualTo(DEFAULT_NOME_FANTASIA);
        assertThat(testEmpresa.getIdLocalCentral()).isEqualTo(DEFAULT_ID_LOCAL_CENTRAL);
        assertThat(testEmpresa.getDsLogo()).isEqualTo(DEFAULT_DS_LOGO);
        assertThat(testEmpresa.getCaminhoLog()).isEqualTo(DEFAULT_CAMINHO_LOG);

        // Validate the Empresa in Elasticsearch
        verify(mockEmpresaSearchRepository, times(1)).save(testEmpresa);
    }

    @Test
    @Transactional
    public void createEmpresaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // Create the Empresa with an existing ID
        empresa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Empresa in Elasticsearch
        verify(mockEmpresaSearchRepository, times(0)).save(empresa);
    }

    @Test
    @Transactional
    public void checkNomeEmpresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setNomeEmpresa(null);

        // Create the Empresa, which fails.

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCnpjIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setCnpj(null);

        // Create the Empresa, which fails.

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmpresas() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList
        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeEmpresa").value(hasItem(DEFAULT_NOME_EMPRESA.toString())))
            .andExpect(jsonPath("$.[*].dtInclusao").value(hasItem(DEFAULT_DT_INCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].usuInclusao").value(hasItem(DEFAULT_USU_INCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].dtUltAlteracao").value(hasItem(DEFAULT_DT_ULT_ALTERACAO.toString())))
            .andExpect(jsonPath("$.[*].usuUltAlteracao").value(hasItem(DEFAULT_USU_ULT_ALTERACAO.toString())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].siglaLa").value(hasItem(DEFAULT_SIGLA_LA.toString())))
            .andExpect(jsonPath("$.[*].conteudoCodigo").value(hasItem(DEFAULT_CONTEUDO_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].idUf").value(hasItem(DEFAULT_ID_UF.intValue())))
            .andExpect(jsonPath("$.[*].nomeFantasia").value(hasItem(DEFAULT_NOME_FANTASIA.toString())))
            .andExpect(jsonPath("$.[*].idLocalCentral").value(hasItem(DEFAULT_ID_LOCAL_CENTRAL.intValue())))
            .andExpect(jsonPath("$.[*].dsLogo").value(hasItem(DEFAULT_DS_LOGO.toString())))
            .andExpect(jsonPath("$.[*].caminhoLog").value(hasItem(DEFAULT_CAMINHO_LOG.toString())));
    }
    
    @Test
    @Transactional
    public void getEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", empresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(empresa.getId().intValue()))
            .andExpect(jsonPath("$.nomeEmpresa").value(DEFAULT_NOME_EMPRESA.toString()))
            .andExpect(jsonPath("$.dtInclusao").value(DEFAULT_DT_INCLUSAO.toString()))
            .andExpect(jsonPath("$.usuInclusao").value(DEFAULT_USU_INCLUSAO.toString()))
            .andExpect(jsonPath("$.dtUltAlteracao").value(DEFAULT_DT_ULT_ALTERACAO.toString()))
            .andExpect(jsonPath("$.usuUltAlteracao").value(DEFAULT_USU_ULT_ALTERACAO.toString()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ.toString()))
            .andExpect(jsonPath("$.siglaLa").value(DEFAULT_SIGLA_LA.toString()))
            .andExpect(jsonPath("$.conteudoCodigo").value(DEFAULT_CONTEUDO_CODIGO.toString()))
            .andExpect(jsonPath("$.idUf").value(DEFAULT_ID_UF.intValue()))
            .andExpect(jsonPath("$.nomeFantasia").value(DEFAULT_NOME_FANTASIA.toString()))
            .andExpect(jsonPath("$.idLocalCentral").value(DEFAULT_ID_LOCAL_CENTRAL.intValue()))
            .andExpect(jsonPath("$.dsLogo").value(DEFAULT_DS_LOGO.toString()))
            .andExpect(jsonPath("$.caminhoLog").value(DEFAULT_CAMINHO_LOG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpresa() throws Exception {
        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa
        Empresa updatedEmpresa = empresaRepository.findById(empresa.getId()).get();
        // Disconnect from session so that the updates on updatedEmpresa are not directly saved in db
        em.detach(updatedEmpresa);
        updatedEmpresa
            .nomeEmpresa(UPDATED_NOME_EMPRESA)
            .dtInclusao(UPDATED_DT_INCLUSAO)
            .usuInclusao(UPDATED_USU_INCLUSAO)
            .dtUltAlteracao(UPDATED_DT_ULT_ALTERACAO)
            .usuUltAlteracao(UPDATED_USU_ULT_ALTERACAO)
            .cnpj(UPDATED_CNPJ)
            .siglaLa(UPDATED_SIGLA_LA)
            .conteudoCodigo(UPDATED_CONTEUDO_CODIGO)
            .idUf(UPDATED_ID_UF)
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .idLocalCentral(UPDATED_ID_LOCAL_CENTRAL)
            .dsLogo(UPDATED_DS_LOGO)
            .caminhoLog(UPDATED_CAMINHO_LOG);

        restEmpresaMockMvc.perform(put("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmpresa)))
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNomeEmpresa()).isEqualTo(UPDATED_NOME_EMPRESA);
        assertThat(testEmpresa.getDtInclusao()).isEqualTo(UPDATED_DT_INCLUSAO);
        assertThat(testEmpresa.getUsuInclusao()).isEqualTo(UPDATED_USU_INCLUSAO);
        assertThat(testEmpresa.getDtUltAlteracao()).isEqualTo(UPDATED_DT_ULT_ALTERACAO);
        assertThat(testEmpresa.getUsuUltAlteracao()).isEqualTo(UPDATED_USU_ULT_ALTERACAO);
        assertThat(testEmpresa.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testEmpresa.getSiglaLa()).isEqualTo(UPDATED_SIGLA_LA);
        assertThat(testEmpresa.getConteudoCodigo()).isEqualTo(UPDATED_CONTEUDO_CODIGO);
        assertThat(testEmpresa.getIdUf()).isEqualTo(UPDATED_ID_UF);
        assertThat(testEmpresa.getNomeFantasia()).isEqualTo(UPDATED_NOME_FANTASIA);
        assertThat(testEmpresa.getIdLocalCentral()).isEqualTo(UPDATED_ID_LOCAL_CENTRAL);
        assertThat(testEmpresa.getDsLogo()).isEqualTo(UPDATED_DS_LOGO);
        assertThat(testEmpresa.getCaminhoLog()).isEqualTo(UPDATED_CAMINHO_LOG);

        // Validate the Empresa in Elasticsearch
        verify(mockEmpresaSearchRepository, times(1)).save(testEmpresa);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Create the Empresa

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc.perform(put("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Empresa in Elasticsearch
        verify(mockEmpresaSearchRepository, times(0)).save(empresa);
    }

    @Test
    @Transactional
    public void deleteEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeDelete = empresaRepository.findAll().size();

        // Get the empresa
        restEmpresaMockMvc.perform(delete("/api/empresas/{id}", empresa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Empresa in Elasticsearch
        verify(mockEmpresaSearchRepository, times(1)).deleteById(empresa.getId());
    }

    @Test
    @Transactional
    public void searchEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        when(mockEmpresaSearchRepository.search(queryStringQuery("id:" + empresa.getId())))
            .thenReturn(Collections.singletonList(empresa));
        // Search the empresa
        restEmpresaMockMvc.perform(get("/api/_search/empresas?query=id:" + empresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeEmpresa").value(hasItem(DEFAULT_NOME_EMPRESA.toString())))
            .andExpect(jsonPath("$.[*].dtInclusao").value(hasItem(DEFAULT_DT_INCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].usuInclusao").value(hasItem(DEFAULT_USU_INCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].dtUltAlteracao").value(hasItem(DEFAULT_DT_ULT_ALTERACAO.toString())))
            .andExpect(jsonPath("$.[*].usuUltAlteracao").value(hasItem(DEFAULT_USU_ULT_ALTERACAO.toString())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].siglaLa").value(hasItem(DEFAULT_SIGLA_LA.toString())))
            .andExpect(jsonPath("$.[*].conteudoCodigo").value(hasItem(DEFAULT_CONTEUDO_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].idUf").value(hasItem(DEFAULT_ID_UF.intValue())))
            .andExpect(jsonPath("$.[*].nomeFantasia").value(hasItem(DEFAULT_NOME_FANTASIA.toString())))
            .andExpect(jsonPath("$.[*].idLocalCentral").value(hasItem(DEFAULT_ID_LOCAL_CENTRAL.intValue())))
            .andExpect(jsonPath("$.[*].dsLogo").value(hasItem(DEFAULT_DS_LOGO.toString())))
            .andExpect(jsonPath("$.[*].caminhoLog").value(hasItem(DEFAULT_CAMINHO_LOG.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Empresa.class);
        Empresa empresa1 = new Empresa();
        empresa1.setId(1L);
        Empresa empresa2 = new Empresa();
        empresa2.setId(empresa1.getId());
        assertThat(empresa1).isEqualTo(empresa2);
        empresa2.setId(2L);
        assertThat(empresa1).isNotEqualTo(empresa2);
        empresa1.setId(null);
        assertThat(empresa1).isNotEqualTo(empresa2);
    }
}
