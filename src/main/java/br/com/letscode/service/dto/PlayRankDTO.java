package br.com.letscode.service.dto;

public class PlayRankDTO {

    public Double score;
    public Long playerId;

    public PlayRankDTO(Double score, Long playerId) {
        this.score = score;
        this.playerId = playerId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
}
