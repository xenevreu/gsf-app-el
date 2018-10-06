package br.com.gsf.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ReinfStatusControleSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ReinfStatusControleSearchRepositoryMockConfiguration {

    @MockBean
    private ReinfStatusControleSearchRepository mockReinfStatusControleSearchRepository;

}
