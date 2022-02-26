package br.com.letscode.repository;

import br.com.letscode.domain.Play;
import br.com.letscode.service.dto.PlayRankDTO;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Play entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayRepository extends JpaRepository<Play, String> {
    @Query(
        "SELECT new br.com.letscode.service.dto.PlayRankDTO(MAX(P.score) AS score , P.player.id AS playerId) FROM Round R INNER JOIN Play P ON P.id = R.play.id GROUP BY P.player.id"
    )
    public List<PlayRankDTO> rank();
}
