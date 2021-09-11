package com.chiragjn.movieman.injector.component;


import com.chiragjn.movieman.fragment.NowPlayingFragment;
import com.chiragjn.movieman.injector.module.ApiModule;
import com.chiragjn.movieman.injector.module.RoomModule;
import com.chiragjn.movieman.networking.ViewModel.MovieViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, RoomModule.class})
public interface AppComponent {

    void injectField(NowPlayingFragment fragment);
    void injectField(MovieViewModel model);
}
