package com.example.esraa.movieapp.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.esraa.movieapp.room.MovieViewModel;
import com.example.esraa.movieapp.R;
import com.example.esraa.movieapp.adapter.ReviewsAdapter;
import com.example.esraa.movieapp.adapter.TrailersAdapter;
import com.example.esraa.movieapp.model.Movie;
import com.example.esraa.movieapp.model.Review;
import com.example.esraa.movieapp.model.Trailer;
import com.example.esraa.movieapp.presenter.DetailsPresenter;
import com.example.esraa.movieapp.presenter.IDetailsPresenter;
import com.example.esraa.movieapp.view.IDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends BaseActivity implements IDetailsActivity {
    @BindView(R.id.tv_movie_title)
    TextView tv_title;
    @BindView(R.id.tv_movie_release_date)
    TextView tv_release_date;
    @BindView(R.id.tv_movie_vote_avg)
    TextView tv_vote;
    @BindView(R.id.tv_movie_plot_synopsis)
    TextView tv_plot_synopsis;
    @BindView(R.id.imgv_movie_details)
    ImageView imgv_poster;
    @BindView(R.id.tgb_fav)
    ImageButton tgb_favourite;
    @BindView(R.id.recycler_view_movie_trailers)
    RecyclerView movieTrailersRecyclerView;
    @BindView(R.id.recycler_view_movie_reviews)
    RecyclerView movieReviewsRecyclerView;
    private Movie movie;
    private IDetailsPresenter detailsPresenter;
    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_acitivity);
        movie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE_OBJECT);
        ButterKnife.bind(this);
        initUi();
        detailsPresenter = new DetailsPresenter(getApplicationContext(), this, this);
        detailsPresenter.getMovieTrailers(movie.getId());
        detailsPresenter.getMovieReviews(movie.getId());
        detailsPresenter.getMovieReviews(movie.getId());
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
    }

    private void initUi() {
        if (null == movie) return;
        Picasso.get().load(movie.getPosterUrl())
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.picasso_error_image)
                .into(imgv_poster);
        if (null != movie.getTitle())
            tv_title.setText(movie.getTitle());
        tv_vote.setText(String.format("%s",movie.getVoteAverage()));
        if (null != movie.getReleaseDate())
            tv_release_date.setText(movie.getReleaseDate());
        if (null != movie.getOverview())
            tv_plot_synopsis.setText(movie.getOverview());
        if (movie.isFavourite()) {
            tgb_favourite.setBackgroundResource(R.drawable.ic_sharp_star_fav);
        } else {
            tgb_favourite.setBackgroundResource(R.drawable.ic_sharp_star_border_24px);
        }
        tgb_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!movie.isFavourite()) {
                    movie.setFavourite(true);
                    movieViewModel.insert(movie);
                    tgb_favourite.setBackgroundResource(R.drawable.ic_sharp_star_fav);
                } else {
                    movie.setFavourite(false);
                    movieViewModel.delete(movie);
                    tgb_favourite.setBackgroundResource(R.drawable.ic_sharp_star_border_24px);
                }
            }
        });

    }

    @Override
    public void viewUiWithTrailersResponse(List<Trailer> trailerList) {
        TrailersAdapter trailersAdapter = new TrailersAdapter(getApplicationContext(), trailerList, this);
        movieTrailersRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));
        movieTrailersRecyclerView.setAdapter(trailersAdapter);
        movieTrailersRecyclerView.setHasFixedSize(true);
        trailersAdapter.setTrailersList(trailerList);
        trailersAdapter.notifyDataSetChanged();
    }

    @Override
    public void playTrailer(Trailer trailer) {
        String videoUrl = String.format("%s%s", "https://www.youtube.com/watch?v=", trailer.getKey());
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)));
    }

    @Override
    public void viewUiWithReviewsResponse(List<Review> reviewsList) {
        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(reviewsList);
        movieReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        movieReviewsRecyclerView.setAdapter(reviewsAdapter);
        movieReviewsRecyclerView.setHasFixedSize(true);
        reviewsAdapter.setReviewsList(reviewsList);
        reviewsAdapter.notifyDataSetChanged();
    }
}
