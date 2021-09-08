package com.chiragjn.movieman.networking.api;

import com.chiragjn.movieman.networking.dao.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TrendingApi {

    @GET("day")
    Call<List<Movie>> getMoviesByDay();

    @GET("week")
    Call<List<Movie>> getMoviesByWeek();
}
