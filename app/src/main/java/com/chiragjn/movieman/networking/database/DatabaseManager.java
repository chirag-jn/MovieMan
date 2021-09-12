package com.chiragjn.movieman.networking.database;

import android.os.AsyncTask;

import com.chiragjn.movieman.networking.entity.Bookmark;
import com.chiragjn.movieman.networking.entity.Movie;
import com.chiragjn.movieman.networking.entity.NowPlaying;
import com.chiragjn.movieman.networking.entity.Search;
import com.chiragjn.movieman.networking.entity.TrendingDay;
import com.chiragjn.movieman.networking.entity.TrendingWeek;

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
            movieDb.nowPlayingDao().deleteTable();
            movieDb.trendingDayDao().deleteTable();
            movieDb.trendingWeekDao().deleteTable();
            movieDb.searchDao().deleteTable();
            // Not deleting bookmarked table so that it can be saved across refreshes
        });
    }

    public Movie getMoviefromId(int id) {
        return movieDb.movieDao().get(id);
    }

    public boolean isMovieBookmarked(int id) {
        Bookmark movie = movieDb.bookmarkDao().get(id);
        return movie != null;
    }

    public void removeBookmark(int id) {
        movieDb.bookmarkDao().deleteEntry(id);
    }

    public void addBookmark(int id) {
        Bookmark movie = new Bookmark(id);
        movieDb.bookmarkDao().insert(movie);
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

    public void insertTrendingDayMovies(final ArrayList<TrendingDay> movies) {
        AsyncTask.execute(() -> {
            movieDb.trendingDayDao().insertAll(movies);
        });
    }

    public void insertTrendingWeekMovies(final ArrayList<TrendingWeek> movies) {
        AsyncTask.execute(() -> {
            movieDb.trendingWeekDao().insertAll(movies);
        });
    }

    public void insertSearchMovies(final ArrayList<Search> movies) {
        AsyncTask.execute(() -> {
            movieDb.searchDao().insertAll(movies);
        });
    }

    public void deleteSearchMovies() {
        AsyncTask.execute(() -> {
            movieDb.searchDao().deleteTable();
        });
    }
}
