package br.com.letscode.repository;



import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.letscode.domain.Round;

/**
 * Spring Data SQL repository for the Round entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
	
	 public List<Round> findByPlayId(String id);
}
