package com.example.esraa.movieapp.presenter;

public interface IDetailsPresenter extends IBasePresenter {
    void getMovieTrailers(long movieId);
    void getMovieReviews(long movieId);
}
