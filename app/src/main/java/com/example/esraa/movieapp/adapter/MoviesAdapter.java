package com.example.esraa.movieapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.esraa.movieapp.R;
import com.example.esraa.movieapp.model.Movie;
import com.example.esraa.movieapp.view.IMainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185/";
    private List<Movie> movieList;
    private Context context;
    private IMainActivity mainActivityView;

    public MoviesAdapter(List<Movie> movieList, Context context, IMainActivity mainActivityView) {
        this.movieList = movieList;
        this.context = context;
        this.mainActivityView = mainActivityView;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.movie_view_layout, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder viewHolder, int position) {
        final Movie movie = movieList.get(position);
        String posterUrl = String.format("%s%s%s", BASE_IMAGE_URL, IMAGE_SIZE, movie.getPosterPath());
        movie.setPosterUrl(posterUrl);
        Picasso.get().load(posterUrl)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.picasso_error_image)
                .into(viewHolder.imgv_movie);
        viewHolder.imgv_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityView.navigateToMovieDetails(movie);
            }
        });


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public void setMoviesList(List<Movie> response) {
        movieList = response;
    }
}
