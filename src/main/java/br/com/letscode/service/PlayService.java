package br.com.letscode.service;

import br.com.letscode.domain.Play;
import br.com.letscode.repository.PlayRepository;
import br.com.letscode.service.dto.PlayRankDTO;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Play}.
 */
@Service
@Transactional
public class PlayService {

    private final Logger log = LoggerFactory.getLogger(PlayService.class);

    private final PlayRepository playRepository;

    public PlayService(PlayRepository playRepository) {
        this.playRepository = playRepository;
    }

    @Transactional(readOnly = true)
    public List<PlayRankDTO> rank() {
        return playRepository.rank();
    }

    public Play start(Play play) {
        log.debug("Request to start Play : {}", play);
        play.setStart(Instant.now());
        return playRepository.save(play);
    }

    public Play end(Play play) {
        log.debug("Request to end Play : {}", play);
        play.setEnd(Instant.now());
        return playRepository.save(play);
    }

    /**
     * Partially update a play.
     *
     * @param play the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Play> partialUpdate(Play play) {
        log.debug("Request to partially update Play : {}", play);

        return playRepository
            .findById(play.getId())
            .map(existingPlay -> {
                if (play.getScore() != null) {
                    existingPlay.setScore(play.getScore());
                }
                if (play.getStart() != null) {
                    existingPlay.setStart(play.getStart());
                }
                if (play.getEnd() != null) {
                    existingPlay.setEnd(play.getEnd());
                }
                if (play.getErrors() != null) {
                    existingPlay.setErrors(play.getErrors());
                }

                return existingPlay;
            })
            .map(playRepository::save);
    }

    /**
     * Get all the plays.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Play> findAll() {
        log.debug("Request to get all Plays");
        return playRepository.findAll();
    }

    /**
     * Get one play by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Play> findOne(String id) {
        log.debug("Request to get Play : {}", id);
        return playRepository.findById(id);
    }

    /**
     * Delete the play by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Play : {}", id);
        playRepository.deleteById(id);
    }
}
