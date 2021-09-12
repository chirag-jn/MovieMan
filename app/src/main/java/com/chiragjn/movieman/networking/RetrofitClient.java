package com.chiragjn.movieman.networking;

import com.chiragjn.movieman.BuildConfig;
import com.chiragjn.movieman.networking.api.NowPlayingApi;
import com.chiragjn.movieman.networking.api.SearchApi;
import com.chiragjn.movieman.networking.api.TrendingApi;
import com.chiragjn.movieman.utils.Constants;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private final TrendingApi trendingApi;
    private final NowPlayingApi nowPlayingApi;
    private final SearchApi searchApi;

    public RetrofitClient() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Endpoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        trendingApi = retrofit.create(TrendingApi.class);
        nowPlayingApi = retrofit.create(NowPlayingApi.class);
        searchApi = retrofit.create(SearchApi.class);
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient client;

        client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder().addQueryParameter(Constants.API_KEY, BuildConfig.API_KEY).build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        }).retryOnConnectionFailure(true).build();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = client.newBuilder().addInterceptor(interceptor).build();
        }

        return client;
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

    public SearchApi getSearchApi() {
        return searchApi;
    }

    public NowPlayingApi getNowPlayingApi() {
        return nowPlayingApi;
    }
}
