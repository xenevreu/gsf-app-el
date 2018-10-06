package br.com.gsf.repository.search;

import br.com.gsf.domain.ReinfStatusControle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ReinfStatusControle entity.
 */
public interface ReinfStatusControleSearchRepository extends ElasticsearchRepository<ReinfStatusControle, Long> {
}
