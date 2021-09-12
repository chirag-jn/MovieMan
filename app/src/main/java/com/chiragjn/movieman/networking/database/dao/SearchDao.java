package com.chiragjn.movieman.networking.database.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.chiragjn.movieman.networking.entity.Movie;
import com.chiragjn.movieman.networking.entity.Search;

import java.util.List;

@Dao
public interface SearchDao {

    @Query("SELECT Movie.* FROM Movie, Search WHERE Movie.id = Search.id ORDER BY vote_average DESC")
    DataSource.Factory<Integer, Movie> getMoviesPaged();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Search> movies);

    @Query("DELETE from Search")
    void deleteTable();
}
