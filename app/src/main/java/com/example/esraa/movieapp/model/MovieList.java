package com.example.esraa.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieList {
    private int page;
    private int total_results;
    private int total_pages;
    @SerializedName("results")
    private ArrayList<Movie> movies;

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return this.total_results;
    }

    public void setTotalResults(int total_results) {
        this.total_results = total_results;
    }

    public int getTotalPages() {
        return this.total_pages;
    }

    public void setTotalPages(int total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<Movie> getMovies() {
        return this.movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
