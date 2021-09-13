package com.chiragjn.movieman.networking.database.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.chiragjn.movieman.networking.entity.Movie;
import com.chiragjn.movieman.networking.entity.TrendingDay;

import java.util.List;

@Dao
public interface TrendingDayDao {

    @Query("SELECT Movie.* FROM Movie, TrendingDay WHERE Movie.id = TrendingDay.id ORDER BY vote_average DESC")
    DataSource.Factory<Integer, Movie> getMoviesPaged();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<TrendingDay> movies);

    @Query("DELETE from TrendingDay")
    void deleteTable();

    @Query("SELECT Movie.* FROM Movie,TrendingDay WHERE Movie.id = TrendingDay.id ORDER BY vote_average DESC, vote_count DESC LIMIT 6")
    DataSource.Factory<Integer, Movie> getTop10Movies();
}
