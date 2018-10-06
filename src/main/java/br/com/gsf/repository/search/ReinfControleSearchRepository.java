package br.com.gsf.repository.search;

import br.com.gsf.domain.ReinfControle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ReinfControle entity.
 */
public interface ReinfControleSearchRepository extends ElasticsearchRepository<ReinfControle, Long> {
}
