package com.chiragjn.movieman.injector;


import com.chiragjn.movieman.injector.module.ApiModule;
import com.chiragjn.movieman.networking.ApiManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class})
public interface ApiComponent {

    ApiManager getApi();
}
