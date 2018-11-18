package com.example.esraa.movieapp.view;

import com.example.esraa.movieapp.model.Movie;
import com.example.esraa.movieapp.model.MovieList;

import java.util.List;

import retrofit2.Response;

public interface IMainActivity {
    void viewUiWithMoviesResponse(List<Movie> response);

    void viewUiWithFailResponse();

    void navigateToMovieDetails(Movie movie);
}
