package com.example.esraa.movieapp.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.esraa.movieapp.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movie);

    @Query("DELETE FROM movie_table WHERE id = :movieId")
    void deleteMovie(long movieId);

    @Query("SELECT * from movie_table")
    LiveData<List<Movie>> getAllLiveFavMovies();

    @Query("SELECT * from movie_table")
    List<Movie> getAllFavMovies();
}
