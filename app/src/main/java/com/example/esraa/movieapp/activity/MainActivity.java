package com.example.esraa.movieapp.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.esraa.movieapp.R;
import com.example.esraa.movieapp.adapter.MoviesAdapter;
import com.example.esraa.movieapp.model.Movie;
import com.example.esraa.movieapp.presenter.IMainPresenter;
import com.example.esraa.movieapp.presenter.MainPresenter;
import com.example.esraa.movieapp.room.MovieViewModel;
import com.example.esraa.movieapp.view.IMainActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IMainActivity {
    public static final String MOVIE_OBJECT = "movie_object";
    private static final String MENU_ITEM_ID = "MenuItemId";
    private static final int TOP_RATED_MENU_ITEM_ID = 0;
    private static final int INITIAL_MENU_ITEM_ID = -1;
    @BindView(R.id.movies_recycler_view)
    RecyclerView moviesRecyclerView;
    @BindView(R.id.tv_fail_response)
    TextView failResponseTextView;
    private MoviesAdapter moviesAdapter;
    private IMainPresenter mainPresenter;
    private List<Movie> movies;
    private int itemId;
    private List<Movie> favMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPresenter = new MainPresenter(getApplicationContext(), this, this);
        movies = new ArrayList<>();
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        moviesAdapter = new MoviesAdapter(movies, getApplicationContext(), this);
        initFavMoviesLiveData();
        if (savedInstanceState != null) {
            int menuItem = savedInstanceState.getInt(MENU_ITEM_ID);
            if (menuItem != INITIAL_MENU_ITEM_ID) {
                handleSelectedMenu(savedInstanceState.getInt(MENU_ITEM_ID));
            } else {
                handleSelectedMenu(TOP_RATED_MENU_ITEM_ID);
            }
        } else {
            handleSelectedMenu(TOP_RATED_MENU_ITEM_ID);
        }
    }

    private void initFavMoviesLiveData() {
        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getAllFavMovies().observe(MainActivity.this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> favourites) {
                favMovies = favourites;
                if (getTitle().equals(getString(R.string.favourite))) {
                    viewUiWithMoviesResponse(favMovies);
                }
            }
        });
    }

    private void handleSelectedMenu(int menuItemId) {
        itemId = menuItemId;
        switch (menuItemId) {
            case R.id.action_top_rated:
                setTitle(R.string.top_rated);
                mainPresenter.getTopRatedMovies();
                break;
            case R.id.action_popular:
                setTitle(R.string.popular);
                mainPresenter.getPopularMovies();
                break;
            case R.id.action_favourite:
                setTitle(R.string.favourite);
                viewUiWithMoviesResponse(mainPresenter.getMoviesFromDb());
                break;
            default:
                setTitle(R.string.top_rated);
                mainPresenter.getTopRatedMovies();
                break;

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(MENU_ITEM_ID, itemId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MENU_ITEM_ID, itemId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getTitle().equals(getString(R.string.favourite))) {
            viewUiWithMoviesResponse(mainPresenter.getMoviesFromDb());
        } else {
            moviesAdapter.setMoviesList(mainPresenter.checkFavouriteMovies(movies));
            moviesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        handleSelectedMenu(item.getItemId());
        return true;
    }

    @Override
    public void viewUiWithMoviesResponse(List<Movie> response) {
        movies = response;
        moviesRecyclerView.setVisibility(View.VISIBLE);
        failResponseTextView.setVisibility(View.GONE);
        moviesRecyclerView.setHasFixedSize(true);
        moviesRecyclerView.setAdapter(moviesAdapter);
        moviesAdapter.setMoviesList(response);
        moviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void viewUiWithFailResponse() {
        moviesRecyclerView.setVisibility(View.GONE);
        failResponseTextView.setVisibility(View.VISIBLE);
        failResponseTextView.setText(R.string.fail_response);
    }

    @Override
    public void navigateToMovieDetails(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MOVIE_OBJECT, (Serializable) movie);
        startActivity(intent);
    }

}
