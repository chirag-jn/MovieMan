package com.chiragjn.movieman.networking.database;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.chiragjn.movieman.MovieManApplication;
import com.chiragjn.movieman.networking.dao.Movie;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private final MovieDatabase movieDb;

    public DatabaseManager() {
        movieDb = MovieDatabase.getInstance();
    }

    public void insertMovie(final Movie movie) {
        AsyncTask.execute(() -> {
            movieDb.movieDao().insert(movie);
        });
    }

    public LiveData<List<Movie>> getAllMovies() {
        return movieDb.movieDao().getPagedMovies();
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
}
