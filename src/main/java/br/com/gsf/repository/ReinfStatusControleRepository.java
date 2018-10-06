package br.com.gsf.repository;

import br.com.gsf.domain.ReinfStatusControle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReinfStatusControle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReinfStatusControleRepository extends JpaRepository<ReinfStatusControle, Long> {

}
