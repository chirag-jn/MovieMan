package com.chiragjn.movieman.networking.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.chiragjn.movieman.networking.dao.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movie")
    List<Movie> getAll();

    @Query("SELECT * FROM Movie WHERE id = :id")
    Movie get(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movies);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Movie> movies);

    @Query("UPDATE Movie SET bookmarked = :bookmarkVal WHERE id = :id")
    void updateBookmark(int id, boolean bookmarkVal);

    @Query("DELETE from Movie")
    void deleteTable();

//    @Query("SELECT * FROM Movie WHERE bookmarked = 1")
//    List<Movie> getBookmarked();
}
