package com.chiragjn.movieman.activity;

import android.content.Intent;
import android.os.Bundle;

import com.chiragjn.movieman.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();
    }

    @Override
    void bindView() {
        setUpActivity();
    }

    private void setUpActivity() {
        launchNavigation();
    }

    private void launchNavigation() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

}