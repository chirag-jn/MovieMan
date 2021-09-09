package com.chiragjn.movieman.activity;

import android.os.Bundle;
import android.util.Log;

import com.chiragjn.movieman.R;
import com.chiragjn.movieman.networking.RetrofitClient;
import com.chiragjn.movieman.networking.dao.Movie;

import java.util.List;

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
        Call<List<Movie>> call = RetrofitClient.getInstance().getTrendingApi().getMoviesByDay();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                List<Movie> movieList = response.body();
                String[] movieTitles = new String[movieList.size()];

                for(int i=0; i<movieTitles.length; i++) {
                    Log.v(getString(R.string.app_name), movieList.get(i).toString());
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.v(getString(R.string.app_name), t.getMessage());
            }
        });
    }
}