package com.example.esraa.movieapp.retrofitnetwork;


import com.example.esraa.movieapp.BuildConfig;
import com.example.esraa.movieapp.model.MovieList;
import com.example.esraa.movieapp.model.ReviewsList;
import com.example.esraa.movieapp.model.TrailersList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApiClient {
    String BASE_URL = "https://api.themoviedb.org/3/";
    String API_KEY_TEXT = "api_key";
    String API_KEY = BuildConfig.THE_API_KEY;

    @GET("movie/{sort_order}")
    Call<MovieList> getMovies(@Path("sort_order") String sort_order ,@Query(API_KEY_TEXT) String apiKey);

    @GET("movie/{id}/videos")
    Call<TrailersList> getMovieTrailers(@Path("id") long groupId, @Query(API_KEY_TEXT) String apiKey);


    @GET("movie/{id}/reviews")
    Call<ReviewsList> getMovieReviews(@Path("id") long groupId, @Query(API_KEY_TEXT) String apiKey);

}