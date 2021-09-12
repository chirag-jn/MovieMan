package com.chiragjn.movieman.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.chiragjn.movieman.R;

public class MovieCollectionActivity extends BaseActivity {

    public static final String TRENDING_TYPE = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();
    }

    @Override
    void bindView() {
        setContentView(R.layout.activity_movie_collection);
    }
}