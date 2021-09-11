package com.chiragjn.movieman.networking.database;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.chiragjn.movieman.networking.entity.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movie ORDER BY vote_average DESC")
    List<Movie> getAll();

    @Query("SELECT * FROM Movie WHERE id = :id")
    Movie get(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movies);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Movie> movies);

    @Query("DELETE from Movie")
    void deleteTable();

    @Query("SELECT * FROM Movie ORDER BY vote_average DESC")
    DataSource.Factory<Integer, Movie> getMoviesPaged();
}
