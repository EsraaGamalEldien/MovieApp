package com.example.esraa.movieapp.view;

import com.example.esraa.movieapp.model.Review;
import com.example.esraa.movieapp.model.Trailer;

import java.util.List;

public interface IDetailsActivity {
    void viewUiWithTrailersResponse(List<Trailer> results);

    void playTrailer(Trailer trailer);

    void viewUiWithReviewsResponse(List<Review> reviewsList);
}
