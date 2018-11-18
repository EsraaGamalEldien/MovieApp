package com.example.esraa.movieapp.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.esraa.movieapp.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {


    private static MovieRoomDatabase movieRoomDatabase;

    public static MovieRoomDatabase getDatabase(final Context context) {
        if (movieRoomDatabase == null) {
            synchronized (MovieRoomDatabase.class) {
                if (movieRoomDatabase == null) {
                    movieRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, "movie_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return movieRoomDatabase;
    }

    public abstract MovieDao movieDao();
}

