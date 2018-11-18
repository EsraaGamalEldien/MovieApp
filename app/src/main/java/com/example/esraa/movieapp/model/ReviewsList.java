package com.example.esraa.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsList {
    @SerializedName("results")
    private List<Review> reviewsList;
    private String page;
    private String id;
    private String total_pages;
    private String total_results;


    public List<Review> getReviewsList() {
        return reviewsList;
    }

    public void setReviewsList(List<Review> reviewsList) {
        this.reviewsList = reviewsList;
    }
}
