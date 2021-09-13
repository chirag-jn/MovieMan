package com.chiragjn.movieman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.chiragjn.movieman.R;
import com.chiragjn.movieman.injector.component.DaggerAppComponent;
import com.chiragjn.movieman.networking.DataFetch;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    private static final int SPLASH_SCREEN_TIME_OUT = 1500;

    @Inject
    protected DataFetch fetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerAppComponent.create().injectField(this);
        bindView();
    }

    @Override
    void bindView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        fetcher.loadNowPlayingItems(0);
        fetcher.loadTrendingDayItems(0);
        fetcher.loadTrendingWeekItems(0);

        setContentView(R.layout.activity_splash);

        setUpActivity();
    }

    private void setUpActivity() {
        new Handler().postDelayed(this::launchNavigation, SPLASH_SCREEN_TIME_OUT);
    }

    private void launchNavigation() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInAnonymously().addOnCompleteListener(task -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });
    }

}