package com.chiragjn.movieman.activity;

import android.os.Bundle;

import com.chiragjn.movieman.R;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();
    }

    @Override
    void bindView() {
        setContentView(R.layout.activity_home);
    }
}