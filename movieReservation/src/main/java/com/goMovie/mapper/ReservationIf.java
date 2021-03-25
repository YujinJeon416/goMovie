package com.goMovie.mapper;

import java.util.ArrayList;

import com.goMovie.beans.Movie;
import com.goMovie.beans.Seat;

public interface ReservationIf {
	public ArrayList<Movie> getMovieList(Movie mv);
	public Movie getMovieInfo(Movie mv);
	public ArrayList<Movie> getScreeningInfo(Movie mv);
	public ArrayList<Movie> ifSelling(Movie mv);
	public ArrayList<Seat> getSeat(Movie mv);
}
