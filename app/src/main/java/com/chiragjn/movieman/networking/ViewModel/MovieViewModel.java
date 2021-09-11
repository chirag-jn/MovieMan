package com.chiragjn.movieman.networking.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.chiragjn.movieman.injector.component.DaggerAppComponent;
import com.chiragjn.movieman.networking.dao.Movie;
import com.chiragjn.movieman.networking.database.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MovieViewModel extends AndroidViewModel {

    @Inject
    protected DatabaseManager dbManager;

    public MovieViewModel(@NonNull Application application) {
        super(application);

        DaggerAppComponent.create().injectField(this);
    }

    public void insert(List<Movie> movies) {
        dbManager.insertMovies((ArrayList<Movie>) movies);
    }

    public LiveData<PagedList<Movie>> getAllMoviesPaged() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(20)
                .setInitialLoadSizeHint(40)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(false)
                .build();
        return dbManager.getMoviesPagedList(config);
    }
}
