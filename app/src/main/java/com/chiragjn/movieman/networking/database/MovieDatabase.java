package com.chiragjn.movieman.networking.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.chiragjn.movieman.Endpoints;
import com.chiragjn.movieman.MovieManApplication;
import com.chiragjn.movieman.networking.dao.Movie;

@Database(entities = {Movie.class}, version = 3)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static MovieDatabase instance = null;

    public static synchronized MovieDatabase getInstance() {
        if (instance == null) {
            instance = Room.databaseBuilder(MovieManApplication.getAppContext(),
                    MovieDatabase.class, Endpoints.NOW_PLAYING_DB)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

//    static Callback callback = new Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateAsyncTask(instance);
//        }
//    };

//    static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {
//
//        private MovieDao movieDao;
//
//        PopulateAsyncTask(MovieDatabase movieDb) {
//            movieDao = movieDb.movieDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            movieDao.deleteTable();
//            return null;
//        }
//    }

}
