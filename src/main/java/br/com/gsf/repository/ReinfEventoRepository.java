package br.com.gsf.repository;

import br.com.gsf.domain.ReinfEvento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReinfEvento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReinfEventoRepository extends JpaRepository<ReinfEvento, Long> {

}
