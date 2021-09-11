package com.chiragjn.movieman.injector.module;

import com.chiragjn.movieman.networking.database.DatabaseManager;
import com.chiragjn.movieman.networking.database.MovieDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    @Provides
    @Singleton
    MovieDatabase provideMovieDatabase() {
        return MovieDatabase.getInstance();
    }

    @Provides
    @Singleton
    DatabaseManager provideDatabaseManager(MovieDatabase movieDatabase) {
        return new DatabaseManager(movieDatabase);
    }
}
