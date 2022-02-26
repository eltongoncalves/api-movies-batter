package br.com.letscode.service;

import br.com.letscode.domain.Movie;
import br.com.letscode.repository.MovieRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Movie}.
 */
@Service
@Transactional
public class MovieService {

    private final Logger log = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Save a movie.
     *
     * @param movie the entity to save.
     * @return the persisted entity.
     */
    public Movie save(Movie movie) {
        log.debug("Request to save Movie : {}", movie);
        return movieRepository.save(movie);
    }

    /**
     * Partially update a movie.
     *
     * @param movie the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Movie> partialUpdate(Movie movie) {
        log.debug("Request to partially update Movie : {}", movie);

        return movieRepository
            .findById(movie.getId())
            .map(existingMovie -> {
                if (movie.getImdbID() != null) {
                    existingMovie.setImdbID(movie.getImdbID());
                }
                if (movie.getImdbRating() != null) {
                    existingMovie.setImdbRating(movie.getImdbRating());
                }
                if (movie.getImdbVotes() != null) {
                    existingMovie.setImdbVotes(movie.getImdbVotes());
                }
                if (movie.getTitle() != null) {
                    existingMovie.setTitle(movie.getTitle());
                }
                if (movie.getYear() != null) {
                    existingMovie.setYear(movie.getYear());
                }
                if (movie.getRated() != null) {
                    existingMovie.setRated(movie.getRated());
                }
                if (movie.getReleased() != null) {
                    existingMovie.setReleased(movie.getReleased());
                }
                if (movie.getRuntime() != null) {
                    existingMovie.setRuntime(movie.getRuntime());
                }
                if (movie.getGenre() != null) {
                    existingMovie.setGenre(movie.getGenre());
                }
                if (movie.getDirector() != null) {
                    existingMovie.setDirector(movie.getDirector());
                }
                if (movie.getWriter() != null) {
                    existingMovie.setWriter(movie.getWriter());
                }
                if (movie.getActors() != null) {
                    existingMovie.setActors(movie.getActors());
                }
                if (movie.getPlot() != null) {
                    existingMovie.setPlot(movie.getPlot());
                }
                if (movie.getLanguage() != null) {
                    existingMovie.setLanguage(movie.getLanguage());
                }
                if (movie.getCountry() != null) {
                    existingMovie.setCountry(movie.getCountry());
                }
                if (movie.getAwards() != null) {
                    existingMovie.setAwards(movie.getAwards());
                }
                if (movie.getPoster() != null) {
                    existingMovie.setPoster(movie.getPoster());
                }
                if (movie.getMetascore() != null) {
                    existingMovie.setMetascore(movie.getMetascore());
                }
                if (movie.getType() != null) {
                    existingMovie.setType(movie.getType());
                }
                if (movie.getDvd() != null) {
                    existingMovie.setDvd(movie.getDvd());
                }
                if (movie.getBoxOffice() != null) {
                    existingMovie.setBoxOffice(movie.getBoxOffice());
                }
                if (movie.getProduction() != null) {
                    existingMovie.setProduction(movie.getProduction());
                }
                if (movie.getWebsite() != null) {
                    existingMovie.setWebsite(movie.getWebsite());
                }
                if (movie.getResponse() != null) {
                    existingMovie.setResponse(movie.getResponse());
                }

                return existingMovie;
            })
            .map(movieRepository::save);
    }

    /**
     * Get all the movies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Movie> findAll() {
        log.debug("Request to get all Movies");
        return movieRepository.findAll();
    }

    /**
     * Get one movie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Movie> findOne(Long id) {
        log.debug("Request to get Movie : {}", id);
        return movieRepository.findById(id);
    }

    /**
     * Delete the movie by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Movie : {}", id);
        movieRepository.deleteById(id);
    }
}
