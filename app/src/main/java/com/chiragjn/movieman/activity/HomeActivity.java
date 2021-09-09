package com.chiragjn.movieman.activity;

import android.os.Bundle;
import android.util.Log;

import com.chiragjn.movieman.R;
import com.chiragjn.movieman.networking.RetrofitClient;
import com.chiragjn.movieman.networking.dao.TmdbResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    }

    void getMovies() {
        Call<TmdbResponseData> call = RetrofitClient.getInstance().getTrendingApi().getMoviesByDay();
        call.enqueue(new Callback<TmdbResponseData>() {
            @Override
            public void onResponse(Call<TmdbResponseData> call, Response<TmdbResponseData> response) {
                TmdbResponseData movieList = response.body();
                assert movieList != null;
                Log.v(getString(R.string.app_name), movieList.toString());
            }

            @Override
            public void onFailure(Call<TmdbResponseData> call, Throwable t) {
                Log.v(getString(R.string.app_name), t.getMessage());
            }
        });
    }
}