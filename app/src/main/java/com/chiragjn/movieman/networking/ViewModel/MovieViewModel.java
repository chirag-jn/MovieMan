package com.chiragjn.movieman.networking.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chiragjn.movieman.networking.dao.Movie;
import com.chiragjn.movieman.networking.database.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private final DatabaseManager dbManager;

    public MovieViewModel(@NonNull Application application) {
        super(application);

        dbManager = new DatabaseManager();
    }

    public void insert(List<Movie> movies) {
        dbManager.insertMovies((ArrayList<Movie>) movies);
    }

    public LiveData<List<Movie>> getAllMovies() {
        return dbManager.getAllMovies();
    }
}
