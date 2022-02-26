package br.com.letscode.repository;

import br.com.letscode.domain.Player;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Player} entity.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<Player> findOneByActivationKey(String activationKey);

    List<Player> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Optional<Player> findOneByResetKey(String resetKey);

    Optional<Player> findOneByEmailIgnoreCase(String email);

    Optional<Player> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<Player> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<Player> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<Player> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);
}
