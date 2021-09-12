package com.chiragjn.movieman.injector.component;


import com.chiragjn.movieman.activity.MovieActivity;
import com.chiragjn.movieman.fragment.NowPlayingFragment;
import com.chiragjn.movieman.injector.module.ApiModule;
import com.chiragjn.movieman.injector.module.RoomModule;
import com.chiragjn.movieman.networking.DataFetch;
import com.chiragjn.movieman.networking.viewmodel.MovieViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, RoomModule.class})
public interface AppComponent {

    void injectField(NowPlayingFragment fragment);
    void injectField(MovieViewModel model);
    void injectField(MovieActivity activity);
    void injectField(DataFetch fetch);
}
