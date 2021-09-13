package com.chiragjn.movieman.networking.database.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.chiragjn.movieman.networking.entity.Movie;
import com.chiragjn.movieman.networking.entity.TrendingWeek;

import java.util.List;

@Dao
public interface TrendingWeekDao {

    @Query("SELECT Movie.* FROM Movie, TrendingWeek WHERE Movie.id = TrendingWeek.id ORDER BY vote_average DESC")
    DataSource.Factory<Integer, Movie> getMoviesPaged();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<TrendingWeek> movies);

    @Query("DELETE from TrendingWeek")
    void deleteTable();

    @Query("SELECT Movie.* FROM Movie,TrendingWeek WHERE Movie.id = TrendingWeek.id ORDER BY vote_average DESC, vote_count DESC LIMIT 6")
    DataSource.Factory<Integer, Movie> getTop10Movies();
}
