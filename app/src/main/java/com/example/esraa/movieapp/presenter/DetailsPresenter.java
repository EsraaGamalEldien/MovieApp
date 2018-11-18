package com.example.esraa.movieapp.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.esraa.movieapp.model.ReviewsList;
import com.example.esraa.movieapp.model.TrailersList;
import com.example.esraa.movieapp.retrofitnetwork.APIClient;
import com.example.esraa.movieapp.retrofitnetwork.IApiClient;
import com.example.esraa.movieapp.view.IDetailsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsPresenter extends BasePresenter implements IDetailsPresenter {
    private Context context;
    private IDetailsActivity detailsActivityView;

    public DetailsPresenter(Context context, Activity activity, IDetailsActivity detailsActivityView) {
        super(activity);
        this.context = context;
        this.detailsActivityView = detailsActivityView;
    }

    @Override
    public void getMovieTrailers(long movieId) {
        progressDialog.show();
        APIClient.getApiClientInstance().getApiService(context).getMovieTrailers(movieId, IApiClient.API_KEY)
                .enqueue(new Callback<TrailersList>() {
                    @Override
                    public void onResponse(Call<TrailersList> call, @NonNull Response<TrailersList> response) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        detailsActivityView.viewUiWithTrailersResponse(response.body().getResults());
                    }

                    @Override
                    public void onFailure(Call<TrailersList> call, Throwable t) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void getMovieReviews(long movieId) {
        progressDialog.show();
        APIClient.getApiClientInstance().getApiService(context).getMovieReviews(movieId, IApiClient.API_KEY)
                .enqueue(new Callback<ReviewsList>() {
                    @Override
                    public void onResponse(Call<ReviewsList> call, Response<ReviewsList> response) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        detailsActivityView.viewUiWithReviewsResponse(response.body().getReviewsList());
                    }

                    @Override
                    public void onFailure(Call<ReviewsList> call, Throwable t) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });
    }

}
