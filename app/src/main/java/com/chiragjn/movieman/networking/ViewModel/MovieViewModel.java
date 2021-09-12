package com.chiragjn.movieman.networking.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.chiragjn.movieman.injector.component.DaggerAppComponent;
import com.chiragjn.movieman.networking.database.MovieDatabase;
import com.chiragjn.movieman.networking.entity.Movie;
import com.chiragjn.movieman.networking.database.DatabaseManager;
import com.chiragjn.movieman.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MovieViewModel extends AndroidViewModel {

    @Inject
    protected DatabaseManager dbManager;

    @Inject
    protected MovieDatabase movieDb;

    PagedList.Config config = new PagedList.Config.Builder()
            .setPageSize(Constants.ITEMS_PER_PAGE)
            .setInitialLoadSizeHint(Constants.ITEMS_IN_MEM)
            .setPrefetchDistance(Constants.ITEMS_PREFETCH)
            .setEnablePlaceholders(false)
            .build();

    public MovieViewModel(@NonNull Application application) {
        super(application);

        DaggerAppComponent.create().injectField(this);
    }

    public void insert(List<Movie> movies) {
        dbManager.insertMovies((ArrayList<Movie>) movies);
    }

    public LiveData<PagedList<Movie>> getAllMoviesPaged() {
        DataSource.Factory<Integer, Movie> factory = movieDb.movieDao().getMoviesPaged();
        return new LivePagedListBuilder<>(factory, config).build();
    }

    public LiveData<PagedList<Movie>> getAllNowPlayingMoviesPaged() {
        DataSource.Factory<Integer, Movie> factory = movieDb.nowPlayingDao().getMoviesPaged();
        return new LivePagedListBuilder<>(factory, config).build();
    }

    public LiveData<PagedList<Movie>> getAllTrendingDayMoviesPaged() {
        DataSource.Factory<Integer, Movie> factory = movieDb.trendingDayDao().getMoviesPaged();
        return new LivePagedListBuilder<>(factory, config).build();
    }

    public LiveData<PagedList<Movie>> getAllTrendingWeekMoviesPaged() {
        DataSource.Factory<Integer, Movie> factory = movieDb.trendingWeekDao().getMoviesPaged();
        return new LivePagedListBuilder<>(factory, config).build();
    }

    public LiveData<PagedList<Movie>> getTop10TrendingDayMoviesPaged() {
        DataSource.Factory<Integer, Movie> factory = movieDb.trendingDayDao().getTop10Movies();
        return new LivePagedListBuilder<>(factory, config).build();
    }

    public LiveData<PagedList<Movie>> getTop10TrendingWeekMoviesPaged() {
        DataSource.Factory<Integer, Movie> factory = movieDb.trendingWeekDao().getTop10Movies();
        return new LivePagedListBuilder<>(factory, config).build();
    }

    public LiveData<PagedList<Movie>> getAllBookmarkMoviesPaged() {
        DataSource.Factory<Integer, Movie> factory = movieDb.bookmarkDao().getMoviesPaged();
        return new LivePagedListBuilder<>(factory, config).build();
    }
}
