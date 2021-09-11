package com.chiragjn.movieman.networking.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.chiragjn.movieman.networking.dao.Movie;

@Database(entities = {Movie.class}, version = 2)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();
}
