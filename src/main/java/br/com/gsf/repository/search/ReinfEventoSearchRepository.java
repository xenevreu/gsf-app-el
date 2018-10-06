package br.com.gsf.repository.search;

import br.com.gsf.domain.ReinfEvento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ReinfEvento entity.
 */
public interface ReinfEventoSearchRepository extends ElasticsearchRepository<ReinfEvento, Long> {
}
