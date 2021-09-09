package com.chiragjn.movieman.networking.api;

import com.chiragjn.movieman.networking.dao.TmdbResponseData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NowPlayingApi {

    @GET("movie/now_playing")
    Call<TmdbResponseData> getMoviesNowPlaying();
}
