package br.com.letscode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Round.
 */
@Entity
@Table(name = "round")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Round implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hit")
    private Boolean hit;

    @Column(name = "points")
    private Integer points;

    @Column(name = "date")
    private Instant date;

    @Column(name = "played")
    private Boolean played;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "rounds", "player" }, allowSetters = true)
    private Play play;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "movieOnes", "movieTwos" }, allowSetters = true)
    private Movie movieOne;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "movieOnes", "movieTwos" }, allowSetters = true)
    private Movie movieTwo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Round id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getHit() {
        return this.hit;
    }

    public Round hit(Boolean hit) {
        this.setHit(hit);
        return this;
    }

    public void setHit(Boolean hit) {
        this.hit = hit;
    }

    public Integer getPoints() {
        return this.points;
    }

    public Round points(Integer points) {
        this.setPoints(points);
        return this;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Instant getDate() {
        return this.date;
    }

    public Round date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Boolean getPlayed() {
        return this.played;
    }

    public Round played(Boolean played) {
        this.setPlayed(played);
        return this;
    }

    public void setPlayed(Boolean played) {
        this.played = played;
    }

    public Play getPlay() {
        return this.play;
    }

    public void setPlay(Play play) {
        this.play = play;
    }

    public Round play(Play play) {
        this.setPlay(play);
        return this;
    }

    public Movie getMovieOne() {
        return this.movieOne;
    }

    public void setMovieOne(Movie movie) {
        this.movieOne = movie;
    }

    public Round movieOne(Movie movie) {
        this.setMovieOne(movie);
        return this;
    }

    public Movie getMovieTwo() {
        return this.movieTwo;
    }

    public void setMovieTwo(Movie movie) {
        this.movieTwo = movie;
    }

    public Round movieTwo(Movie movie) {
        this.setMovieTwo(movie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Round)) {
            return false;
        }
        return id != null && id.equals(((Round) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Round{" +
            "id=" + getId() +
            ", hit='" + getHit() + "'" +
            ", points=" + getPoints() +
            ", date='" + getDate() + "'" +
            ", played='" + getPlayed() + "'" +
            "}";
    }
}
