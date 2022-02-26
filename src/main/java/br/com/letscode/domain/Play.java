package br.com.letscode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Play.
 */
@Entity
@Table(name = "play")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Play implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "score")
    private Double score;

    @Column(name = "start")
    private Instant start;

    @Column(name = "end")
    private Instant end;

    @Column(name = "errors")
    private Integer errors;

    @OneToMany(mappedBy = "play")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "play", "movieOne", "movieTwo" }, allowSetters = true)
    private Set<Round> rounds = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "plays" }, allowSetters = true)
    private Player player;
    
    public Play() {}

    public Play(String id) {
        this.setId(id);
    }


    public String getId() {
        return this.id;
    }

    public Play id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getScore() {
        return this.score;
    }

    public Play score(Double score) {
        this.setScore(score);
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Instant getStart() {
        return this.start;
    }

    public Play start(Instant start) {
        this.setStart(start);
        return this;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return this.end;
    }

    public Play end(Instant end) {
        this.setEnd(end);
        return this;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Integer getErrors() {
        return this.errors;
    }

    public Play errors(Integer errors) {
        this.setErrors(errors);
        return this;
    }

    public void setErrors(Integer errors) {
        this.errors = errors;
    }

    public Set<Round> getRounds() {
        return this.rounds;
    }

    public void setRounds(Set<Round> rounds) {
        if (this.rounds != null) {
            this.rounds.forEach(i -> i.setPlay(null));
        }
        if (rounds != null) {
            rounds.forEach(i -> i.setPlay(this));
        }
        this.rounds = rounds;
    }

    public Play rounds(Set<Round> rounds) {
        this.setRounds(rounds);
        return this;
    }

    public Play addRound(Round round) {
        this.rounds.add(round);
        round.setPlay(this);
        return this;
    }

    public Play removeRound(Round round) {
        this.rounds.remove(round);
        round.setPlay(null);
        return this;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Play player(Player player) {
        this.setPlayer(player);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Play)) {
            return false;
        }
        return id != null && id.equals(((Play) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Play{" +
            "id=" + getId() +
            ", score='" + getScore() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", errors=" + getErrors() +
            "}";
    }
}
