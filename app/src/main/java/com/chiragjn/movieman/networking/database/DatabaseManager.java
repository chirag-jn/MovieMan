package com.chiragjn.movieman.networking.database;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.chiragjn.movieman.networking.entity.Movie;

import java.util.ArrayList;

import javax.inject.Inject;

public class DatabaseManager {

    protected MovieDatabase movieDb;

    @Inject
    public DatabaseManager(MovieDatabase movieDb) {
        this.movieDb = movieDb;
    }

    public void deleteAllMovies() {
        AsyncTask.execute(() -> {
            movieDb.movieDao().deleteTable();
        });
    }

    public void insertMovies(final ArrayList<Movie> movies) {
        AsyncTask.execute(() -> {
            movieDb.movieDao().insertAll(movies);
        });
    }

    public LiveData<PagedList<Movie>> getMoviesPagedList(PagedList.Config config) {
        DataSource.Factory<Integer, Movie> factory = movieDb.movieDao().getMoviesPaged();
        return new LivePagedListBuilder<>(factory, config).build();
    }
}
