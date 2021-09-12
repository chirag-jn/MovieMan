package com.chiragjn.movieman.networking.database.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.chiragjn.movieman.networking.entity.Bookmark;
import com.chiragjn.movieman.networking.entity.Movie;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Query("SELECT Movie.* FROM Movie, Bookmark WHERE Movie.id == Bookmark.id ORDER BY vote_average DESC")
    DataSource.Factory<Integer, Movie> getMoviesPaged();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Bookmark> movies);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Bookmark movie);

    @Query("DELETE from Bookmark")
    void deleteTable();

    @Query("DELETE from Bookmark WHERE id = :id")
    void deleteEntry(int id);

    @Query("SELECT * FROM Bookmark WHERE id = :id")
    Bookmark get(int id);
}
