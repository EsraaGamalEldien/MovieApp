package com.example.esraa.movieapp.presenter;

import android.app.Application;
import android.content.Context;

import com.example.esraa.movieapp.room.MovieRepository;
import com.example.esraa.movieapp.activity.MainActivity;
import com.example.esraa.movieapp.model.Movie;
import com.example.esraa.movieapp.model.MovieList;
import com.example.esraa.movieapp.retrofitnetwork.APIClient;
import com.example.esraa.movieapp.retrofitnetwork.IApiClient;
import com.example.esraa.movieapp.view.IMainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter extends BasePresenter implements IMainPresenter {
    private Context context;
    private IMainActivity mainActivityView;
    private MovieRepository movieRepository;

    public MainPresenter(Context context, IMainActivity mainActivityView, MainActivity activity) {
        super(activity);
        this.context = context;
        this.mainActivityView = mainActivityView;
        movieRepository = new MovieRepository((Application) context);
    }


    @Override
    public void getTopRatedMovies() {
        progressDialog.show();
        APIClient.getApiClientInstance().getApiService(context).getMovies("top_rated",IApiClient.API_KEY)
                .enqueue(new Callback<MovieList>() {
                    @Override
                    public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        mainActivityView.viewUiWithMoviesResponse(checkFavouriteMovies(response.body().getMovies()));
                    }

                    @Override
                    public void onFailure(Call<MovieList> call, Throwable t) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        mainActivityView.viewUiWithFailResponse();
                    }
                });
    }

    public List<Movie> checkFavouriteMovies(List<Movie> movies) {
        List<Movie> favMovies = getMoviesFromDb();
        for (int i = 0; i < movies.size(); i++) {
            movies.get(i).setFavourite(false);
            if (favMovies != null) {
                for (int j = 0; j < favMovies.size(); j++) {
                    if (movies.get(i).getTitle().equals(favMovies.get(j).getTitle())) {
                        movies.get(i).setFavourite(favMovies.get(j).isFavourite());
                    }
                }
            }
        }
        return movies;
    }


    @Override
    public void getPopularMovies() {
        progressDialog.show();
        APIClient.getApiClientInstance().getApiService(context).getMovies("popular",IApiClient.API_KEY)
                .enqueue(new Callback<MovieList>() {
                    @Override
                    public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        mainActivityView.viewUiWithMoviesResponse(checkFavouriteMovies(response.body().getMovies()));
                    }

                    @Override
                    public void onFailure(Call<MovieList> call, Throwable t) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        mainActivityView.viewUiWithFailResponse();
                    }
                });
    }

    public List<Movie> getMoviesFromDb() {
        return movieRepository.getAllFavMovies();
    }

}
