package com.chiragjn.movieman.networking.api;

import com.chiragjn.movieman.networking.dao.TmdbResponseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TrendingApi {

    @GET("trending/movie/day")
    Call<TmdbResponseData> getMoviesByDay(@Query("page") int page);

    @GET("trending/movie/week")
    Call<TmdbResponseData> getMoviesByWeek(@Query("page") int page);
}
