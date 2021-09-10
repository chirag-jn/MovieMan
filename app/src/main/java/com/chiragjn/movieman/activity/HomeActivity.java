package com.chiragjn.movieman.activity;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.chiragjn.movieman.R;
import com.chiragjn.movieman.networking.ApiManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();
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

    public ApiManager getApi() {
        return getApiManager();
    }
}