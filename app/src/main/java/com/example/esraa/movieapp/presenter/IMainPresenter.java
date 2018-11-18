package com.example.esraa.movieapp.presenter;

import com.example.esraa.movieapp.model.Movie;

import java.util.List;

public interface IMainPresenter extends IBasePresenter {
    void getTopRatedMovies();

    void getPopularMovies();

    List<Movie> checkFavouriteMovies(List<Movie> movies);

    List<Movie> getMoviesFromDb();
}
