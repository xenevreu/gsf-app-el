package br.com.gsf.repository;

import br.com.gsf.domain.ReinfItemControle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReinfItemControle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReinfItemControleRepository extends JpaRepository<ReinfItemControle, Long> {

}
