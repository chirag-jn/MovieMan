package com.chiragjn.movieman.networking;

import com.chiragjn.movieman.Endpoints;
import com.chiragjn.movieman.networking.api.NowPlayingApi;
import com.chiragjn.movieman.networking.api.TrendingApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private final TrendingApi trendingApi;
    private final NowPlayingApi nowPlayingApi;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Endpoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        trendingApi = retrofit.create(TrendingApi.class);
        nowPlayingApi = retrofit.create(NowPlayingApi.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public TrendingApi getTrendingApi() {
        return trendingApi;
    }

    public NowPlayingApi getNowPlayingApi() {
        return nowPlayingApi;
    }
}
