package com.chiragjn.movieman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.chiragjn.movieman.R;

public class SplashActivity extends BaseActivity {

    private static final int SPLASH_SCREEN_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();
    }

    @Override
    void bindView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_splash);

        setUpActivity();
    }

    private void setUpActivity() {
        new Handler().postDelayed(this::launchNavigation, SPLASH_SCREEN_TIME_OUT);
    }

    private void launchNavigation() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

}