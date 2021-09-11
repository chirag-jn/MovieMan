package com.chiragjn.movieman.networking.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.chiragjn.movieman.networking.Endpoints;
import com.chiragjn.movieman.MovieManApplication;
import com.chiragjn.movieman.networking.database.dao.BookmarkDao;
import com.chiragjn.movieman.networking.database.dao.NowPlayingDao;
import com.chiragjn.movieman.networking.database.dao.TrendingDayDao;
import com.chiragjn.movieman.networking.database.dao.TrendingWeekDao;
import com.chiragjn.movieman.networking.entity.Movie;
import com.chiragjn.movieman.utils.Constants;

@Database(entities = {Movie.class}, version = Constants.DB_VERSION)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();
    public abstract TrendingDayDao trendingDayDao();
    public abstract TrendingWeekDao trendingWeekDao();
    public abstract BookmarkDao bookmarkDao();
    public abstract NowPlayingDao nowPlayingDao();

    private static MovieDatabase instance = null;

    public static synchronized MovieDatabase getInstance() {
        if (instance == null) {
            instance = Room.databaseBuilder(MovieManApplication.getAppContext(),
                    MovieDatabase.class, Endpoints.NOW_PLAYING_DB)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
