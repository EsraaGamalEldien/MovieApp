package com.example.esraa.movieapp.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.esraa.movieapp.model.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public void insert(Movie movie) {
        movieRepository.insert(movie);
    }

    public void delete(Movie movie) {
        movieRepository.delete(movie);
    }

    public LiveData<List<Movie>> getAllFavMovies() {
        return movieRepository.getAllFavMoviesLiveData();
    }
}
