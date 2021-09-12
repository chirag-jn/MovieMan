package com.chiragjn.movieman.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chiragjn.movieman.R;
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
        Uri data = getIntent().getData();
        if (data != null) {
            try {
                String url = data.toString();
                String[] pathArr = url.split("/");
                movie_id = Integer.parseInt(pathArr[pathArr.length-1]);
            } catch (Exception e) {
                Toast.makeText(this, getString(R.string.invalid_code), Toast.LENGTH_SHORT).show();
                this.finishAffinity();
            }
        } else {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String value = extras.getString(MOVIE_ID_KEY);
                if(value!=null) {
                    movie_id = Integer.parseInt(value);
                } else {
                    movie_id = -1;
                }
            }
        }
    }

    void populateView() {
        binding.name.setText(String.valueOf(movie_id));
    }
}