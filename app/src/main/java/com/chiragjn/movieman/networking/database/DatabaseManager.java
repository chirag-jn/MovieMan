package com.chiragjn.movieman.networking.database;

import android.os.AsyncTask;

import androidx.room.Room;

import com.chiragjn.movieman.MovieManApplication;
import com.chiragjn.movieman.networking.dao.Movie;

import java.util.ArrayList;

public class DatabaseManager {

    private final MovieDatabase movieDb;

    public DatabaseManager() {
        movieDb = Room.databaseBuilder(MovieManApplication.getAppContext(),
                MovieDatabase.class, movieDbName).fallbackToDestructiveMigration().build();
    }

    private static final String movieDbName = "movieman";

    public void insertMovie(final Movie movie) {
        AsyncTask.execute(() -> {
            movieDb.movieDao().insert(movie);
        });
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

    public ArrayList<Movie> getMovies() {
        try {
            return new GetMoviesUsersAsyncTask().execute().get();
        } catch (Exception e) {
//            TODO: Manage Exception
        }
        return null;
    }

//    public ArrayList<Movie> getBookmarkedMovies() {
//        try {
//            return new GetBookmarkedUsersAsyncTask().execute().get();
//        } catch (Exception e) {
//            // TODO: Manage exception
//        }
//        return null;
//    }

    private class GetMoviesUsersAsyncTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return (ArrayList<Movie>) movieDb.movieDao().getAll();
        }
    }

//    private class GetBookmarkedUsersAsyncTask extends AsyncTask<Void, Void, ArrayList<Movie>> {
//
//        @Override
//        protected ArrayList<Movie> doInBackground(Void... voids) {
//            return (ArrayList<Movie>) movieDb.movieDao().getBookmarked();
//        }
//    }

}
