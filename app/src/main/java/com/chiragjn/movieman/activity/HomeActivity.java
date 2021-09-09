package com.chiragjn.movieman.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.chiragjn.movieman.R;
import com.chiragjn.movieman.networking.dao.TmdbResponseData;
import com.chiragjn.movieman.networking.listener.ErrorListener;
import com.chiragjn.movieman.networking.listener.ResponseListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        getMovies();
    }

    @Override
    void bindView() {
        setContentView(R.layout.activity_home);

        setNavBarView();
    }

    void setNavBarView() {
        BottomNavigationView navView = findViewById(R.id.bottomNavBar);
        NavController navController = Navigation.findNavController(this, R.id.navFragment);
        NavigationUI.setupWithNavController(navView, navController);
        navView.setSelectedItemId(R.id.nowPlayingFragment);
    }

    void getMovies() {

        getApiManager().getTrendingMoviesByDay(new ResponseListener<TmdbResponseData>() {
            @Override
            public void onResponse(TmdbResponseData response, int statusCode) {
                Log.v(getString(R.string.app_name), response.toString());
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(Throwable t) {
//                TODO: Show Internet Disconnection Snackbar
                Log.v(getString(R.string.app_name), t.getMessage());
            }

            @Override
            public void onErrorResponse(int statusCode) {
//                TODO: Show Internet Disconnection Snackbar
                Log.v(getString(R.string.app_name), "Error: " + statusCode);
            }
        });
    }
}