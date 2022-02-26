package br.com.letscode.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.letscode.IntegrationTest;
import br.com.letscode.domain.Movie;
import br.com.letscode.repository.MovieRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MovieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MovieResourceIT {

    private static final String DEFAULT_IMDB_ID = "AAAAAAAAAA";
    private static final String UPDATED_IMDB_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_IMDB_RATING = 1D;
    private static final Double UPDATED_IMDB_RATING = 2D;

    private static final Integer DEFAULT_IMDB_VOTES = 1;
    private static final Integer UPDATED_IMDB_VOTES = 2;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_RATED = "AAAAAAAAAA";
    private static final String UPDATED_RATED = "BBBBBBBBBB";

    private static final String DEFAULT_RELEASED = "AAAAAAAAAA";
    private static final String UPDATED_RELEASED = "BBBBBBBBBB";

    private static final String DEFAULT_RUNTIME = "AAAAAAAAAA";
    private static final String UPDATED_RUNTIME = "BBBBBBBBBB";

    private static final String DEFAULT_GENRE = "AAAAAAAAAA";
    private static final String UPDATED_GENRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECTOR = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTOR = "BBBBBBBBBB";

    private static final String DEFAULT_WRITER = "AAAAAAAAAA";
    private static final String UPDATED_WRITER = "BBBBBBBBBB";

    private static final String DEFAULT_ACTORS = "AAAAAAAAAA";
    private static final String UPDATED_ACTORS = "BBBBBBBBBB";

    private static final String DEFAULT_PLOT = "AAAAAAAAAA";
    private static final String UPDATED_PLOT = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_AWARDS = "AAAAAAAAAA";
    private static final String UPDATED_AWARDS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTER = "AAAAAAAAAA";
    private static final String UPDATED_POSTER = "BBBBBBBBBB";

    private static final String DEFAULT_METASCORE = "AAAAAAAAAA";
    private static final String UPDATED_METASCORE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DVD = "AAAAAAAAAA";
    private static final String UPDATED_DVD = "BBBBBBBBBB";

    private static final String DEFAULT_BOX_OFFICE = "AAAAAAAAAA";
    private static final String UPDATED_BOX_OFFICE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RESPONSE = false;
    private static final Boolean UPDATED_RESPONSE = true;

    private static final String ENTITY_API_URL = "/api/movies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovieMockMvc;

    private Movie movie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movie createEntity(EntityManager em) {
        Movie movie = new Movie()
            .imdbID(DEFAULT_IMDB_ID)
            .imdbRating(DEFAULT_IMDB_RATING)
            .imdbVotes(DEFAULT_IMDB_VOTES)
            .title(DEFAULT_TITLE)
            .year(DEFAULT_YEAR)
            .rated(DEFAULT_RATED)
            .released(DEFAULT_RELEASED)
            .runtime(DEFAULT_RUNTIME)
            .genre(DEFAULT_GENRE)
            .director(DEFAULT_DIRECTOR)
            .writer(DEFAULT_WRITER)
            .actors(DEFAULT_ACTORS)
            .plot(DEFAULT_PLOT)
            .language(DEFAULT_LANGUAGE)
            .country(DEFAULT_COUNTRY)
            .awards(DEFAULT_AWARDS)
            .poster(DEFAULT_POSTER)
            .metascore(DEFAULT_METASCORE)
            .type(DEFAULT_TYPE)
            .dvd(DEFAULT_DVD)
            .boxOffice(DEFAULT_BOX_OFFICE)
            .production(DEFAULT_PRODUCTION)
            .website(DEFAULT_WEBSITE)
            .response(DEFAULT_RESPONSE);
        return movie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movie createUpdatedEntity(EntityManager em) {
        Movie movie = new Movie()
            .imdbID(UPDATED_IMDB_ID)
            .imdbRating(UPDATED_IMDB_RATING)
            .imdbVotes(UPDATED_IMDB_VOTES)
            .title(UPDATED_TITLE)
            .year(UPDATED_YEAR)
            .rated(UPDATED_RATED)
            .released(UPDATED_RELEASED)
            .runtime(UPDATED_RUNTIME)
            .genre(UPDATED_GENRE)
            .director(UPDATED_DIRECTOR)
            .writer(UPDATED_WRITER)
            .actors(UPDATED_ACTORS)
            .plot(UPDATED_PLOT)
            .language(UPDATED_LANGUAGE)
            .country(UPDATED_COUNTRY)
            .awards(UPDATED_AWARDS)
            .poster(UPDATED_POSTER)
            .metascore(UPDATED_METASCORE)
            .type(UPDATED_TYPE)
            .dvd(UPDATED_DVD)
            .boxOffice(UPDATED_BOX_OFFICE)
            .production(UPDATED_PRODUCTION)
            .website(UPDATED_WEBSITE)
            .response(UPDATED_RESPONSE);
        return movie;
    }

    @BeforeEach
    public void initTest() {
        movie = createEntity(em);
    }

    @Test
    @Transactional
    void createMovie() throws Exception {
        int databaseSizeBeforeCreate = movieRepository.findAll().size();
        // Create the Movie
        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movie)))
            .andExpect(status().isCreated());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeCreate + 1);
        Movie testMovie = movieList.get(movieList.size() - 1);
        assertThat(testMovie.getImdbID()).isEqualTo(DEFAULT_IMDB_ID);
        assertThat(testMovie.getImdbRating()).isEqualTo(DEFAULT_IMDB_RATING);
        assertThat(testMovie.getImdbVotes()).isEqualTo(DEFAULT_IMDB_VOTES);
        assertThat(testMovie.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMovie.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testMovie.getRated()).isEqualTo(DEFAULT_RATED);
        assertThat(testMovie.getReleased()).isEqualTo(DEFAULT_RELEASED);
        assertThat(testMovie.getRuntime()).isEqualTo(DEFAULT_RUNTIME);
        assertThat(testMovie.getGenre()).isEqualTo(DEFAULT_GENRE);
        assertThat(testMovie.getDirector()).isEqualTo(DEFAULT_DIRECTOR);
        assertThat(testMovie.getWriter()).isEqualTo(DEFAULT_WRITER);
        assertThat(testMovie.getActors()).isEqualTo(DEFAULT_ACTORS);
        assertThat(testMovie.getPlot()).isEqualTo(DEFAULT_PLOT);
        assertThat(testMovie.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testMovie.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testMovie.getAwards()).isEqualTo(DEFAULT_AWARDS);
        assertThat(testMovie.getPoster()).isEqualTo(DEFAULT_POSTER);
        assertThat(testMovie.getMetascore()).isEqualTo(DEFAULT_METASCORE);
        assertThat(testMovie.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMovie.getDvd()).isEqualTo(DEFAULT_DVD);
        assertThat(testMovie.getBoxOffice()).isEqualTo(DEFAULT_BOX_OFFICE);
        assertThat(testMovie.getProduction()).isEqualTo(DEFAULT_PRODUCTION);
        assertThat(testMovie.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testMovie.getResponse()).isEqualTo(DEFAULT_RESPONSE);
    }

    @Test
    @Transactional
    void createMovieWithExistingId() throws Exception {
        // Create the Movie with an existing ID
        movie.setId(1L);

        int databaseSizeBeforeCreate = movieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movie)))
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMovies() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList
        restMovieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movie.getId().intValue())))
            .andExpect(jsonPath("$.[*].imdbID").value(hasItem(DEFAULT_IMDB_ID)))
            .andExpect(jsonPath("$.[*].imdbRating").value(hasItem(DEFAULT_IMDB_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].imdbVotes").value(hasItem(DEFAULT_IMDB_VOTES)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].rated").value(hasItem(DEFAULT_RATED)))
            .andExpect(jsonPath("$.[*].released").value(hasItem(DEFAULT_RELEASED)))
            .andExpect(jsonPath("$.[*].runtime").value(hasItem(DEFAULT_RUNTIME)))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE)))
            .andExpect(jsonPath("$.[*].director").value(hasItem(DEFAULT_DIRECTOR)))
            .andExpect(jsonPath("$.[*].writer").value(hasItem(DEFAULT_WRITER)))
            .andExpect(jsonPath("$.[*].actors").value(hasItem(DEFAULT_ACTORS)))
            .andExpect(jsonPath("$.[*].plot").value(hasItem(DEFAULT_PLOT)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].awards").value(hasItem(DEFAULT_AWARDS)))
            .andExpect(jsonPath("$.[*].poster").value(hasItem(DEFAULT_POSTER)))
            .andExpect(jsonPath("$.[*].metascore").value(hasItem(DEFAULT_METASCORE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].dvd").value(hasItem(DEFAULT_DVD)))
            .andExpect(jsonPath("$.[*].boxOffice").value(hasItem(DEFAULT_BOX_OFFICE)))
            .andExpect(jsonPath("$.[*].production").value(hasItem(DEFAULT_PRODUCTION)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].response").value(hasItem(DEFAULT_RESPONSE.booleanValue())));
    }

    @Test
    @Transactional
    void getMovie() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get the movie
        restMovieMockMvc
            .perform(get(ENTITY_API_URL_ID, movie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movie.getId().intValue()))
            .andExpect(jsonPath("$.imdbID").value(DEFAULT_IMDB_ID))
            .andExpect(jsonPath("$.imdbRating").value(DEFAULT_IMDB_RATING.doubleValue()))
            .andExpect(jsonPath("$.imdbVotes").value(DEFAULT_IMDB_VOTES))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.rated").value(DEFAULT_RATED))
            .andExpect(jsonPath("$.released").value(DEFAULT_RELEASED))
            .andExpect(jsonPath("$.runtime").value(DEFAULT_RUNTIME))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE))
            .andExpect(jsonPath("$.director").value(DEFAULT_DIRECTOR))
            .andExpect(jsonPath("$.writer").value(DEFAULT_WRITER))
            .andExpect(jsonPath("$.actors").value(DEFAULT_ACTORS))
            .andExpect(jsonPath("$.plot").value(DEFAULT_PLOT))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.awards").value(DEFAULT_AWARDS))
            .andExpect(jsonPath("$.poster").value(DEFAULT_POSTER))
            .andExpect(jsonPath("$.metascore").value(DEFAULT_METASCORE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.dvd").value(DEFAULT_DVD))
            .andExpect(jsonPath("$.boxOffice").value(DEFAULT_BOX_OFFICE))
            .andExpect(jsonPath("$.production").value(DEFAULT_PRODUCTION))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.response").value(DEFAULT_RESPONSE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMovie() throws Exception {
        // Get the movie
        restMovieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMovie() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        int databaseSizeBeforeUpdate = movieRepository.findAll().size();

        // Update the movie
        Movie updatedMovie = movieRepository.findById(movie.getId()).get();
        // Disconnect from session so that the updates on updatedMovie are not directly saved in db
        em.detach(updatedMovie);
        updatedMovie
            .imdbID(UPDATED_IMDB_ID)
            .imdbRating(UPDATED_IMDB_RATING)
            .imdbVotes(UPDATED_IMDB_VOTES)
            .title(UPDATED_TITLE)
            .year(UPDATED_YEAR)
            .rated(UPDATED_RATED)
            .released(UPDATED_RELEASED)
            .runtime(UPDATED_RUNTIME)
            .genre(UPDATED_GENRE)
            .director(UPDATED_DIRECTOR)
            .writer(UPDATED_WRITER)
            .actors(UPDATED_ACTORS)
            .plot(UPDATED_PLOT)
            .language(UPDATED_LANGUAGE)
            .country(UPDATED_COUNTRY)
            .awards(UPDATED_AWARDS)
            .poster(UPDATED_POSTER)
            .metascore(UPDATED_METASCORE)
            .type(UPDATED_TYPE)
            .dvd(UPDATED_DVD)
            .boxOffice(UPDATED_BOX_OFFICE)
            .production(UPDATED_PRODUCTION)
            .website(UPDATED_WEBSITE)
            .response(UPDATED_RESPONSE);

        restMovieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMovie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMovie))
            )
            .andExpect(status().isOk());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
        Movie testMovie = movieList.get(movieList.size() - 1);
        assertThat(testMovie.getImdbID()).isEqualTo(UPDATED_IMDB_ID);
        assertThat(testMovie.getImdbRating()).isEqualTo(UPDATED_IMDB_RATING);
        assertThat(testMovie.getImdbVotes()).isEqualTo(UPDATED_IMDB_VOTES);
        assertThat(testMovie.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMovie.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testMovie.getRated()).isEqualTo(UPDATED_RATED);
        assertThat(testMovie.getReleased()).isEqualTo(UPDATED_RELEASED);
        assertThat(testMovie.getRuntime()).isEqualTo(UPDATED_RUNTIME);
        assertThat(testMovie.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testMovie.getDirector()).isEqualTo(UPDATED_DIRECTOR);
        assertThat(testMovie.getWriter()).isEqualTo(UPDATED_WRITER);
        assertThat(testMovie.getActors()).isEqualTo(UPDATED_ACTORS);
        assertThat(testMovie.getPlot()).isEqualTo(UPDATED_PLOT);
        assertThat(testMovie.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testMovie.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testMovie.getAwards()).isEqualTo(UPDATED_AWARDS);
        assertThat(testMovie.getPoster()).isEqualTo(UPDATED_POSTER);
        assertThat(testMovie.getMetascore()).isEqualTo(UPDATED_METASCORE);
        assertThat(testMovie.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMovie.getDvd()).isEqualTo(UPDATED_DVD);
        assertThat(testMovie.getBoxOffice()).isEqualTo(UPDATED_BOX_OFFICE);
        assertThat(testMovie.getProduction()).isEqualTo(UPDATED_PRODUCTION);
        assertThat(testMovie.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testMovie.getResponse()).isEqualTo(UPDATED_RESPONSE);
    }

    @Test
    @Transactional
    void putNonExistingMovie() throws Exception {
        int databaseSizeBeforeUpdate = movieRepository.findAll().size();
        movie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, movie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(movie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMovie() throws Exception {
        int databaseSizeBeforeUpdate = movieRepository.findAll().size();
        movie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(movie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMovie() throws Exception {
        int databaseSizeBeforeUpdate = movieRepository.findAll().size();
        movie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(movie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMovieWithPatch() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        int databaseSizeBeforeUpdate = movieRepository.findAll().size();

        // Update the movie using partial update
        Movie partialUpdatedMovie = new Movie();
        partialUpdatedMovie.setId(movie.getId());

        partialUpdatedMovie
            .imdbVotes(UPDATED_IMDB_VOTES)
            .title(UPDATED_TITLE)
            .year(UPDATED_YEAR)
            .rated(UPDATED_RATED)
            .released(UPDATED_RELEASED)
            .genre(UPDATED_GENRE)
            .director(UPDATED_DIRECTOR)
            .language(UPDATED_LANGUAGE)
            .dvd(UPDATED_DVD)
            .boxOffice(UPDATED_BOX_OFFICE)
            .production(UPDATED_PRODUCTION)
            .response(UPDATED_RESPONSE);

        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMovie))
            )
            .andExpect(status().isOk());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
        Movie testMovie = movieList.get(movieList.size() - 1);
        assertThat(testMovie.getImdbID()).isEqualTo(DEFAULT_IMDB_ID);
        assertThat(testMovie.getImdbRating()).isEqualTo(DEFAULT_IMDB_RATING);
        assertThat(testMovie.getImdbVotes()).isEqualTo(UPDATED_IMDB_VOTES);
        assertThat(testMovie.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMovie.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testMovie.getRated()).isEqualTo(UPDATED_RATED);
        assertThat(testMovie.getReleased()).isEqualTo(UPDATED_RELEASED);
        assertThat(testMovie.getRuntime()).isEqualTo(DEFAULT_RUNTIME);
        assertThat(testMovie.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testMovie.getDirector()).isEqualTo(UPDATED_DIRECTOR);
        assertThat(testMovie.getWriter()).isEqualTo(DEFAULT_WRITER);
        assertThat(testMovie.getActors()).isEqualTo(DEFAULT_ACTORS);
        assertThat(testMovie.getPlot()).isEqualTo(DEFAULT_PLOT);
        assertThat(testMovie.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testMovie.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testMovie.getAwards()).isEqualTo(DEFAULT_AWARDS);
        assertThat(testMovie.getPoster()).isEqualTo(DEFAULT_POSTER);
        assertThat(testMovie.getMetascore()).isEqualTo(DEFAULT_METASCORE);
        assertThat(testMovie.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMovie.getDvd()).isEqualTo(UPDATED_DVD);
        assertThat(testMovie.getBoxOffice()).isEqualTo(UPDATED_BOX_OFFICE);
        assertThat(testMovie.getProduction()).isEqualTo(UPDATED_PRODUCTION);
        assertThat(testMovie.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testMovie.getResponse()).isEqualTo(UPDATED_RESPONSE);
    }

    @Test
    @Transactional
    void fullUpdateMovieWithPatch() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        int databaseSizeBeforeUpdate = movieRepository.findAll().size();

        // Update the movie using partial update
        Movie partialUpdatedMovie = new Movie();
        partialUpdatedMovie.setId(movie.getId());

        partialUpdatedMovie
            .imdbID(UPDATED_IMDB_ID)
            .imdbRating(UPDATED_IMDB_RATING)
            .imdbVotes(UPDATED_IMDB_VOTES)
            .title(UPDATED_TITLE)
            .year(UPDATED_YEAR)
            .rated(UPDATED_RATED)
            .released(UPDATED_RELEASED)
            .runtime(UPDATED_RUNTIME)
            .genre(UPDATED_GENRE)
            .director(UPDATED_DIRECTOR)
            .writer(UPDATED_WRITER)
            .actors(UPDATED_ACTORS)
            .plot(UPDATED_PLOT)
            .language(UPDATED_LANGUAGE)
            .country(UPDATED_COUNTRY)
            .awards(UPDATED_AWARDS)
            .poster(UPDATED_POSTER)
            .metascore(UPDATED_METASCORE)
            .type(UPDATED_TYPE)
            .dvd(UPDATED_DVD)
            .boxOffice(UPDATED_BOX_OFFICE)
            .production(UPDATED_PRODUCTION)
            .website(UPDATED_WEBSITE)
            .response(UPDATED_RESPONSE);

        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMovie))
            )
            .andExpect(status().isOk());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
        Movie testMovie = movieList.get(movieList.size() - 1);
        assertThat(testMovie.getImdbID()).isEqualTo(UPDATED_IMDB_ID);
        assertThat(testMovie.getImdbRating()).isEqualTo(UPDATED_IMDB_RATING);
        assertThat(testMovie.getImdbVotes()).isEqualTo(UPDATED_IMDB_VOTES);
        assertThat(testMovie.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMovie.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testMovie.getRated()).isEqualTo(UPDATED_RATED);
        assertThat(testMovie.getReleased()).isEqualTo(UPDATED_RELEASED);
        assertThat(testMovie.getRuntime()).isEqualTo(UPDATED_RUNTIME);
        assertThat(testMovie.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testMovie.getDirector()).isEqualTo(UPDATED_DIRECTOR);
        assertThat(testMovie.getWriter()).isEqualTo(UPDATED_WRITER);
        assertThat(testMovie.getActors()).isEqualTo(UPDATED_ACTORS);
        assertThat(testMovie.getPlot()).isEqualTo(UPDATED_PLOT);
        assertThat(testMovie.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testMovie.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testMovie.getAwards()).isEqualTo(UPDATED_AWARDS);
        assertThat(testMovie.getPoster()).isEqualTo(UPDATED_POSTER);
        assertThat(testMovie.getMetascore()).isEqualTo(UPDATED_METASCORE);
        assertThat(testMovie.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMovie.getDvd()).isEqualTo(UPDATED_DVD);
        assertThat(testMovie.getBoxOffice()).isEqualTo(UPDATED_BOX_OFFICE);
        assertThat(testMovie.getProduction()).isEqualTo(UPDATED_PRODUCTION);
        assertThat(testMovie.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testMovie.getResponse()).isEqualTo(UPDATED_RESPONSE);
    }

    @Test
    @Transactional
    void patchNonExistingMovie() throws Exception {
        int databaseSizeBeforeUpdate = movieRepository.findAll().size();
        movie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, movie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(movie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMovie() throws Exception {
        int databaseSizeBeforeUpdate = movieRepository.findAll().size();
        movie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(movie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMovie() throws Exception {
        int databaseSizeBeforeUpdate = movieRepository.findAll().size();
        movie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(movie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMovie() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        int databaseSizeBeforeDelete = movieRepository.findAll().size();

        // Delete the movie
        restMovieMockMvc
            .perform(delete(ENTITY_API_URL_ID, movie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
