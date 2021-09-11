package com.chiragjn.movieman.networking.api;

import com.chiragjn.movieman.networking.entity.util.TmdbResponseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NowPlayingApi {

    @GET("movie/now_playing")
    Call<TmdbResponseData> getMoviesNowPlaying(@Query("page") int page);
}
