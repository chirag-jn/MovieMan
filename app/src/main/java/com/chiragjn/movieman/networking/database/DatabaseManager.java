package com.chiragjn.movieman.networking.database;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.chiragjn.movieman.networking.entity.Movie;
import com.chiragjn.movieman.networking.entity.NowPlaying;

import java.util.ArrayList;

import javax.inject.Inject;

public class DatabaseManager {

    public MovieDatabase movieDb;

    @Inject
    public DatabaseManager(MovieDatabase movieDb) {
        this.movieDb = movieDb;
    }

    public void deleteAllMovies() {
        AsyncTask.execute(() -> {
            movieDb.movieDao().deleteTable();
        });
    }

    public void getMoviefromId(int id) {
        movieDb.movieDao().get(id);
    }

    public void insertMovies(final ArrayList<Movie> movies) {
        AsyncTask.execute(() -> {
            movieDb.movieDao().insertAll(movies);
        });
    }

    public void insertNowPlayingMovies(final ArrayList<NowPlaying> movies) {
        AsyncTask.execute(() -> {
            movieDb.nowPlayingDao().insertAll(movies);
        });
    }
}
