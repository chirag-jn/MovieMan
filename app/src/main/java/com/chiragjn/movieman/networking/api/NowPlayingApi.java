package com.chiragjn.movieman.networking.api;

import com.chiragjn.movieman.networking.dao.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NowPlayingApi {

    @GET("now_playing")
    Call<List<Movie>> getMoviesNowPlaying();
}
