package com.chiragjn.movieman.activity;

import android.os.Bundle;
import android.view.View;

import com.chiragjn.movieman.databinding.ActivityMovieBinding;
import com.chiragjn.movieman.networking.database.DatabaseManager;

import javax.inject.Inject;

public class MovieActivity extends BaseActivity {

    @Inject
    protected DatabaseManager dbManager;

    public static final String MOVIE_ID_KEY = "movie_id";

    private int movie_id;

    private ActivityMovieBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIdFromBundle();
        bindView();
        populateView();
    }

    @Override
    void bindView() {
        binding = ActivityMovieBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    void getIdFromBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString(MOVIE_ID_KEY);
            movie_id = Integer.parseInt(value);
        }
    }

    void populateView() {
        binding.name.setText(String.valueOf(movie_id));
    }
}