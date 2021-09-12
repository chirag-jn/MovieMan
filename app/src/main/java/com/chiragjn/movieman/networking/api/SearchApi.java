package com.chiragjn.movieman.networking.api;

import com.chiragjn.movieman.networking.entity.util.TmdbResponseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SearchApi {

    @GET("search/movie")
    Call<TmdbResponseData> getMoviesByName(@Query("page") int page, @Query("query") String search);

    @GET("movie/{id}")
    Call<TmdbResponseData> getMoviesById(@Path("id") int id);
}
