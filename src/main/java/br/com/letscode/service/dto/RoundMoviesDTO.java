package br.com.letscode.service.dto;

import br.com.letscode.domain.Movie;

public class RoundMoviesDTO {

	private Movie movieOne;
	private Movie movieTwo;
	
	
	public RoundMoviesDTO(Movie movieOne, Movie movieTwo) {
		this.movieOne =movieOne;
		this.movieTwo =movieTwo;
	}
	
	
	public Movie getMovieOne() {
		return movieOne;
	}
	public void setMovieOne(Movie movieOne) {
		this.movieOne = movieOne;
	}
	public Movie getMovieTwo() {
		return movieTwo;
	}
	public void setMovieTwo(Movie movieTwo) {
		this.movieTwo = movieTwo;
	}
	
	

    
}
