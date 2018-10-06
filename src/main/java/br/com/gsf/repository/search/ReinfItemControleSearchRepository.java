package br.com.gsf.repository.search;

import br.com.gsf.domain.ReinfItemControle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ReinfItemControle entity.
 */
public interface ReinfItemControleSearchRepository extends ElasticsearchRepository<ReinfItemControle, Long> {
}
