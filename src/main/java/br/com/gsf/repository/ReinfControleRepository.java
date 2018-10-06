package br.com.gsf.repository;

import br.com.gsf.domain.ReinfControle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReinfControle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReinfControleRepository extends JpaRepository<ReinfControle, Long> {

}
