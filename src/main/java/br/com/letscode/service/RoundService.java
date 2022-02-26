package br.com.letscode.service;

import br.com.letscode.domain.Movie;
import br.com.letscode.domain.Play;
import br.com.letscode.domain.Round;
import br.com.letscode.repository.MovieRepository;
import br.com.letscode.repository.PlayRepository;
import br.com.letscode.repository.RoundRepository;
import br.com.letscode.service.dto.RoundMoviesDTO;
import br.com.letscode.service.dto.RoundValidDTO;
import br.com.letscode.web.rest.errors.BadRequestAlertException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Round}.
 */
@Service
@Transactional
public class RoundService {

    private final Logger log = LoggerFactory.getLogger(RoundService.class);

    private final RoundRepository roundRepository;

    private final MovieRepository movieRepository;

    private final PlayRepository playRepository;

    public RoundService(RoundRepository roundRepository, MovieRepository movieRepository, PlayRepository playRepository) {
        this.roundRepository = roundRepository;
        this.movieRepository = movieRepository;
        this.playRepository = playRepository;
    }

    public interface MyEventConsumer {
        public Round generate();
    }

    public Round generateRandomRound(List<Movie> movies, List<Round> rounds, Play play) {
        Round round = new Round();
        round.setPlay(play);
        round.setDate(Instant.now());
        round.setHit(false);
        round.setPlayed(false);
        round.setPoints(0);
        int movieOneIndex = ThreadLocalRandom.current().nextInt(movies.size());
        int movieTwoIndex = ThreadLocalRandom.current().nextInt(movies.size());
        Movie movieOne = movies.get(movieOneIndex);
        Movie movieTwo = movies.get(movieTwoIndex);
        round.setMovieOne(movieOne);
        round.setMovieTwo(movieTwo);

        if (round.getMovieOne().getId() != round.getMovieTwo().getId()) {
            long count1 = rounds
                .stream()
                .filter(e ->
                    e.getMovieOne().getId().equals(round.getMovieOne().getId()) &&
                    e.getMovieTwo().getId().equals(round.getMovieTwo().getId())
                )
                .count();

            long count2 = rounds
                .stream()
                .filter(e ->
                    e.getMovieOne().getId().equals(round.getMovieTwo().getId()) &&
                    e.getMovieTwo().getId().equals(round.getMovieOne().getId())
                )
                .count();

            if (count1 > 0 || count2 > 0) {
                generateRandomRound(movies, rounds, play);
            }
        } else {
            generateRandomRound(movies, rounds, play);
        }

        return round;
    }

    public Round newRound(Play play) {
        Play playDB = playRepository.findById(play.getId()).get();

        if (!playRepository.existsById(play.getId())) {
            throw new BadRequestAlertException("Play not found", "", "idnotfound");
        }

        if (playDB.getEnd() != null) throw new BadRequestAlertException("Game over :/!!", "", "gameover");

        if (playDB.getErrors() >= 3) {
            playDB.setEnd(Instant.now());
            playRepository.save(playDB);
            throw new BadRequestAlertException("Game over :/!!", "", "gameover");
        }

        List<Movie> movies = movieRepository.findAll();
        
        // try {
        // 	List<RoundMoviesDTO> rounds1 = roundRepository.findByPlayId(play.getId());
		// } catch (Exception e2) {
		// 	System.out.println(e2);
		// }

        List<Round> rounds = roundRepository.findByPlayId(play.getId());
        
        

        // List<Round> rounds =  new ArrayList<>();
        
        Optional<Round> optional = rounds.stream().filter(e -> e.getPlayed().equals(false)).findFirst();

        Round round = null;

        if (!optional.isEmpty()) {
            round = optional.get();
        } else {
            round = generateRandomRound(movies, rounds, play);
        }

        return roundRepository.save(round);
    }

    public Round validMovies(RoundValidDTO roundDTO) {
        Round round = roundRepository.findById(roundDTO.getRoundId()).get();

        if (round.getPlayed()) {
            throw new BadRequestAlertException("Round played", "", "played");
        }

        Movie movieOne = round.getMovieOne();
        Movie movieTwo = round.getMovieTwo();

        Play play = playRepository.findById(round.getPlay().getId()).get();

        Double scoreMovieOne = (movieOne.getImdbRating() * movieOne.getImdbVotes());
        Double scoreMovieTwo = (movieTwo.getImdbRating() * movieTwo.getImdbVotes());

        if (
            (scoreMovieOne > scoreMovieTwo && movieOne.getId().equals(roundDTO.getMovieId())) ||
            (scoreMovieTwo > scoreMovieOne && movieTwo.getId().equals(roundDTO.getMovieId()))
        ) {
            round.setHit(true);
            round.setPoints(1);
            play.setScore(play.getScore() + 1);
        } else {
            play.setErrors(play.getErrors() + 1);
        }

        playRepository.save(play);

        round.setPlayed(true);
        round.setPlay(play);

        return roundRepository.save(round);
    }

    /**
     * Get all the rounds.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Round> findAll() {
        log.debug("Request to get all Rounds");
        return roundRepository.findAll();
    }

    /**
     * Get one round by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Round> findOne(Long id) {
        log.debug("Request to get Round : {}", id);
        return roundRepository.findById(id);
    }

    /**
     * Delete the round by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Round : {}", id);
        roundRepository.deleteById(id);
    }
}
