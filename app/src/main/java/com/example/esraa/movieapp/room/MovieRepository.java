package com.example.esraa.movieapp.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.esraa.movieapp.model.Movie;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieRepository {

    private MovieDao movieDao;

    public MovieRepository(Application application) {
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        movieDao = db.movieDao();
    }

    public LiveData<List<Movie>> getAllFavMoviesLiveData() {
        try {
            return new getLiveDataAsyncTask(movieDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Movie> getAllFavMovies() {
        try {
            return new getAllFavMoviesAsyncTask(movieDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Movie movie) {
        new insertAsyncTask(movieDao).execute(movie);
    }

    public void delete(Movie movie) {
        new deleteAsyncTask(movieDao).execute(movie);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao mAsyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao mAsyncTaskDao;

        deleteAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.deleteMovie(params[0].getId());
            return null;
        }
    }

    private static class getLiveDataAsyncTask extends AsyncTask<Void, Void, LiveData<List<Movie>>> {

        private MovieDao mAsyncTaskDao;

        getLiveDataAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<Movie>> doInBackground(Void... voids) {
            return mAsyncTaskDao.getAllLiveFavMovies();
        }
    }

    private static class getAllFavMoviesAsyncTask extends AsyncTask<Void, Void, List<Movie>> {

        private MovieDao mAsyncTaskDao;

        getAllFavMoviesAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            return mAsyncTaskDao.getAllFavMovies();
        }
    }

}
