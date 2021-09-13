package com.chiragjn.movieman.networking;

import com.chiragjn.movieman.networking.entity.Movie;
import com.chiragjn.movieman.networking.entity.util.TmdbResponseData;
import com.chiragjn.movieman.networking.listener.ErrorListener;
import com.chiragjn.movieman.networking.listener.ResponseListener;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager {

    RetrofitClient client;

    @Inject
    public ApiManager(RetrofitClient client) {
        this.client = client;
    }

    public void cancelQueue() {
        client.getDispatcher().cancelAll();
    }

    public void getTrendingMoviesByDay(int page, final ResponseListener<TmdbResponseData> listener, final ErrorListener errorListener) {
        Call<TmdbResponseData> call = client.getTrendingApi().getMoviesByDay(page);
        callEnqueue(call, listener, errorListener);
    }

    public void getTrendingMoviesByWeek(int page, final ResponseListener<TmdbResponseData> listener, final ErrorListener errorListener) {
        Call<TmdbResponseData> call = client.getTrendingApi().getMoviesByWeek(page);
        callEnqueue(call, listener, errorListener);
    }

    public void getNowPlayingMovies(int page, final ResponseListener<TmdbResponseData> listener, final ErrorListener errorListener) {
        Call<TmdbResponseData> call = client.getNowPlayingApi().getMoviesNowPlaying(page);
        callEnqueue(call, listener, errorListener);
    }

    public void searchMovieByName(String search, int page, final ResponseListener<TmdbResponseData> listener, final ErrorListener errorListener) {
        Call<TmdbResponseData> call = client.getSearchApi().getMoviesByName(page, search);
        callEnqueue(call, listener, errorListener);
    }

    public void searchMovieById(int id, final ResponseListener<Movie> listener, final ErrorListener errorListener) {
        Call<Movie> call = client.getSearchApi().getMovieById(id);
        callMovieEnqueue(call, listener, errorListener);
    }

    private void callEnqueue(Call<TmdbResponseData> call, final ResponseListener<TmdbResponseData> listener, final ErrorListener errorListener) {
        call.enqueue(new Callback<TmdbResponseData>() {
            @Override
            public void onResponse(Call<TmdbResponseData> call, Response<TmdbResponseData> response) {
                if (response.isSuccessful()) {
                    listener.onResponse(response.body(), response.code());
                } else {
                    errorListener.onErrorResponse(response.code());
                }
            }

            @Override
            public void onFailure(Call<TmdbResponseData> call, Throwable t) {
                if (!call.isCanceled()) {
                    errorListener.onErrorResponse(t);
                }
            }
        });
    }

    private void callMovieEnqueue(Call<Movie> call, final ResponseListener<Movie> listener, final ErrorListener errorListener) {
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    listener.onResponse(response.body(), response.code());
                } else {
                    errorListener.onErrorResponse(response.code());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                if (!call.isCanceled()) {
                    errorListener.onErrorResponse(t);
                }
            }
        });
    }
}
