package com.chiragjn.movieman.injector.module;

import com.chiragjn.movieman.networking.ApiManager;
import com.chiragjn.movieman.networking.DataFetch;
import com.chiragjn.movieman.networking.RetrofitClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Provides
    @Singleton
    RetrofitClient provideRetrofitClient() {
        return RetrofitClient.getInstance();
    }

    @Provides
    @Singleton
    ApiManager provideApiManager(RetrofitClient retrofitClient) {
        return new ApiManager(retrofitClient);
    }

    @Provides
    @Singleton
    DataFetch provideDataFetch() {
        return new DataFetch();
    }
}
