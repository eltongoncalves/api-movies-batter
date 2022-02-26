package br.com.letscode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Movie.
 */
@Entity
@Table(name = "movie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "imdb_id")
    private String imdbID;

    @Column(name = "imdb_rating")
    private Double imdbRating;

    @Column(name = "imdb_votes")
    private Integer imdbVotes;

    @Column(name = "title")
    private String title;

    @Column(name = "year")
    private String year;

    @Column(name = "rated")
    private String rated;

    @Column(name = "released")
    private String released;

    @Column(name = "runtime")
    private String runtime;

    @Column(name = "genre")
    private String genre;

    @Column(name = "director")
    private String director;

    @Column(name = "writer")
    private String writer;

    @Column(name = "actors")
    private String actors;

    @Column(name = "plot")
    private String plot;

    @Column(name = "language")
    private String language;

    @Column(name = "country")
    private String country;

    @Column(name = "awards")
    private String awards;

    @Column(name = "poster")
    private String poster;

    @Column(name = "metascore")
    private String metascore;

    @Column(name = "type")
    private String type;

    @Column(name = "dvd")
    private String dvd;

    @Column(name = "box_office")
    private String boxOffice;

    @Column(name = "production")
    private String production;

    @Column(name = "website")
    private String website;

    @Column(name = "response")
    private Boolean response;

    @OneToMany(mappedBy = "movieOne")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "play", "movieOne", "movieTwo" }, allowSetters = true)
    private Set<Round> movieOnes = new HashSet<>();

    @OneToMany(mappedBy = "movieTwo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "play", "movieOne", "movieTwo" }, allowSetters = true)
    private Set<Round> movieTwos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Movie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImdbID() {
        return this.imdbID;
    }

    public Movie imdbID(String imdbID) {
        this.setImdbID(imdbID);
        return this;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public Double getImdbRating() {
        return this.imdbRating;
    }

    public Movie imdbRating(Double imdbRating) {
        this.setImdbRating(imdbRating);
        return this;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public Integer getImdbVotes() {
        return this.imdbVotes;
    }

    public Movie imdbVotes(Integer imdbVotes) {
        this.setImdbVotes(imdbVotes);
        return this;
    }

    public void setImdbVotes(Integer imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getTitle() {
        return this.title;
    }

    public Movie title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return this.year;
    }

    public Movie year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return this.rated;
    }

    public Movie rated(String rated) {
        this.setRated(rated);
        return this;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return this.released;
    }

    public Movie released(String released) {
        this.setReleased(released);
        return this;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return this.runtime;
    }

    public Movie runtime(String runtime) {
        this.setRuntime(runtime);
        return this;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return this.genre;
    }

    public Movie genre(String genre) {
        this.setGenre(genre);
        return this;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return this.director;
    }

    public Movie director(String director) {
        this.setDirector(director);
        return this;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return this.writer;
    }

    public Movie writer(String writer) {
        this.setWriter(writer);
        return this;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return this.actors;
    }

    public Movie actors(String actors) {
        this.setActors(actors);
        return this;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return this.plot;
    }

    public Movie plot(String plot) {
        this.setPlot(plot);
        return this;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return this.language;
    }

    public Movie language(String language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return this.country;
    }

    public Movie country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return this.awards;
    }

    public Movie awards(String awards) {
        this.setAwards(awards);
        return this;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getPoster() {
        return this.poster;
    }

    public Movie poster(String poster) {
        this.setPoster(poster);
        return this;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getMetascore() {
        return this.metascore;
    }

    public Movie metascore(String metascore) {
        this.setMetascore(metascore);
        return this;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public String getType() {
        return this.type;
    }

    public Movie type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDvd() {
        return this.dvd;
    }

    public Movie dvd(String dvd) {
        this.setDvd(dvd);
        return this;
    }

    public void setDvd(String dvd) {
        this.dvd = dvd;
    }

    public String getBoxOffice() {
        return this.boxOffice;
    }

    public Movie boxOffice(String boxOffice) {
        this.setBoxOffice(boxOffice);
        return this;
    }

    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getProduction() {
        return this.production;
    }

    public Movie production(String production) {
        this.setProduction(production);
        return this;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getWebsite() {
        return this.website;
    }

    public Movie website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Boolean getResponse() {
        return this.response;
    }

    public Movie response(Boolean response) {
        this.setResponse(response);
        return this;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public Set<Round> getMovieOnes() {
        return this.movieOnes;
    }

    public void setMovieOnes(Set<Round> rounds) {
        if (this.movieOnes != null) {
            this.movieOnes.forEach(i -> i.setMovieOne(null));
        }
        if (rounds != null) {
            rounds.forEach(i -> i.setMovieOne(this));
        }
        this.movieOnes = rounds;
    }

    public Movie movieOnes(Set<Round> rounds) {
        this.setMovieOnes(rounds);
        return this;
    }

    public Movie addMovieOne(Round round) {
        this.movieOnes.add(round);
        round.setMovieOne(this);
        return this;
    }

    public Movie removeMovieOne(Round round) {
        this.movieOnes.remove(round);
        round.setMovieOne(null);
        return this;
    }

    public Set<Round> getMovieTwos() {
        return this.movieTwos;
    }

    public void setMovieTwos(Set<Round> rounds) {
        if (this.movieTwos != null) {
            this.movieTwos.forEach(i -> i.setMovieTwo(null));
        }
        if (rounds != null) {
            rounds.forEach(i -> i.setMovieTwo(this));
        }
        this.movieTwos = rounds;
    }

    public Movie movieTwos(Set<Round> rounds) {
        this.setMovieTwos(rounds);
        return this;
    }

    public Movie addMovieTwo(Round round) {
        this.movieTwos.add(round);
        round.setMovieTwo(this);
        return this;
    }

    public Movie removeMovieTwo(Round round) {
        this.movieTwos.remove(round);
        round.setMovieTwo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        return id != null && id.equals(((Movie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Movie{" +
            "id=" + getId() +
            ", imdbID='" + getImdbID() + "'" +
            ", imdbRating=" + getImdbRating() +
            ", imdbVotes=" + getImdbVotes() +
            ", title='" + getTitle() + "'" +
            ", year='" + getYear() + "'" +
            ", rated='" + getRated() + "'" +
            ", released='" + getReleased() + "'" +
            ", runtime='" + getRuntime() + "'" +
            ", genre='" + getGenre() + "'" +
            ", director='" + getDirector() + "'" +
            ", writer='" + getWriter() + "'" +
            ", actors='" + getActors() + "'" +
            ", plot='" + getPlot() + "'" +
            ", language='" + getLanguage() + "'" +
            ", country='" + getCountry() + "'" +
            ", awards='" + getAwards() + "'" +
            ", poster='" + getPoster() + "'" +
            ", metascore='" + getMetascore() + "'" +
            ", type='" + getType() + "'" +
            ", dvd='" + getDvd() + "'" +
            ", boxOffice='" + getBoxOffice() + "'" +
            ", production='" + getProduction() + "'" +
            ", website='" + getWebsite() + "'" +
            ", response='" + getResponse() + "'" +
            "}";
    }
}
