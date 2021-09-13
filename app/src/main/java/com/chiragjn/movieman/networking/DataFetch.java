package com.chiragjn.movieman.networking;

import android.content.Context;
import android.widget.Toast;

import com.chiragjn.movieman.MovieManApplication;
import com.chiragjn.movieman.R;
import com.chiragjn.movieman.injector.component.DaggerAppComponent;
import com.chiragjn.movieman.networking.database.DatabaseManager;
import com.chiragjn.movieman.networking.entity.Movie;
import com.chiragjn.movieman.networking.entity.NowPlaying;
import com.chiragjn.movieman.networking.entity.Search;
import com.chiragjn.movieman.networking.entity.TrendingDay;
import com.chiragjn.movieman.networking.entity.TrendingWeek;
import com.chiragjn.movieman.networking.entity.util.TmdbResponseData;
import com.chiragjn.movieman.networking.listener.ErrorListener;
import com.chiragjn.movieman.networking.listener.ResponseListener;
import com.chiragjn.movieman.utils.Constants;

import java.util.ArrayList;

import javax.inject.Inject;

public class DataFetch {

    @Inject
    protected DatabaseManager dbManager;

    @Inject
    protected ApiManager retrofitApi;

    public DataFetch() {
        DaggerAppComponent.create().injectField(this);
    }

    public void cancelQueue() {
        retrofitApi.cancelQueue();
    }

    public void loadNowPlayingItems(int currentPage) {
        int curPage = currentPage + 1;

        if (curPage >= Constants.MAX_PAGES) {
            return;
        }

        retrofitApi.getNowPlayingMovies(curPage, new ResponseListener<TmdbResponseData>() {
            @Override
            public void onResponse(TmdbResponseData response, int statusCode) {
                ArrayList<Movie> responseArr = (ArrayList<Movie>) response.getResults();
                dbManager.insertMovies(responseArr);

                ArrayList<NowPlaying> responseIds = new ArrayList<>();
                for (Movie movie : responseArr) {
                    responseIds.add(new NowPlaying(movie.getId()));
                }
                dbManager.insertNowPlayingMovies(responseIds);

                boolean isLastPage = curPage == response.getTotalPages();

                if (!isLastPage) {
                    loadNowPlayingItems(curPage);
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(Throwable t) {
                showWorkingOffline();
            }

            @Override
            public void onErrorResponse(int statusCode) {
                showWorkingOffline();
            }
        });

    }

    public void loadTrendingDayItems(int currentPage) {
        int curPage = currentPage + 1;

        if (curPage >= Constants.MAX_PAGES) {
            return;
        }

        retrofitApi.getTrendingMoviesByDay(curPage, new ResponseListener<TmdbResponseData>() {
            @Override
            public void onResponse(TmdbResponseData response, int statusCode) {
                ArrayList<Movie> responseArr = (ArrayList<Movie>) response.getResults();
                dbManager.insertMovies(responseArr);

                ArrayList<TrendingDay> responseIds = new ArrayList<>();
                for (Movie movie : responseArr) {
                    responseIds.add(new TrendingDay(movie.getId()));
                }
                dbManager.insertTrendingDayMovies(responseIds);

                boolean isLastPage = curPage == response.getTotalPages();

                if (!isLastPage) {
                    loadTrendingDayItems(curPage);
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(Throwable t) {
                showWorkingOffline();
            }

            @Override
            public void onErrorResponse(int statusCode) {
                showWorkingOffline();
            }
        });

    }

    public void loadTrendingWeekItems(int currentPage) {
        int curPage = currentPage + 1;

        if (curPage >= Constants.MAX_PAGES) {
            return;
        }

        retrofitApi.getTrendingMoviesByWeek(curPage, new ResponseListener<TmdbResponseData>() {
            @Override
            public void onResponse(TmdbResponseData response, int statusCode) {
                ArrayList<Movie> responseArr = (ArrayList<Movie>) response.getResults();
                dbManager.insertMovies(responseArr);

                ArrayList<TrendingWeek> responseIds = new ArrayList<>();
                for (Movie movie : responseArr) {
                    responseIds.add(new TrendingWeek(movie.getId()));
                }
                dbManager.insertTrendingWeekMovies(responseIds);

                boolean isLastPage = curPage == response.getTotalPages();

                if (!isLastPage) {
                    loadTrendingWeekItems(curPage);
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(Throwable t) {
                showWorkingOffline();
            }

            @Override
            public void onErrorResponse(int statusCode) {
                showWorkingOffline();
            }
        });

    }

    public void deleteAllMovies() {
        dbManager.deleteAllMovies();
    }

    public void deleteSearchedMovies() {
        dbManager.deleteSearchMovies();
    }

    public void loadSearchedMovies(String search, int currentPage) {
        int curPage = currentPage + 1;

        retrofitApi.searchMovieByName(search, curPage, new ResponseListener<TmdbResponseData>() {
            @Override
            public void onResponse(TmdbResponseData response, int statusCode) {

                dbManager.deleteSearchMovies();

                ArrayList<Movie> responseArr = (ArrayList<Movie>) response.getResults();
                dbManager.insertMovies(responseArr);

                ArrayList<Search> responseIds = new ArrayList<>();
                for (Movie movie : responseArr) {
                    responseIds.add(new Search(movie.getId()));
                }
                dbManager.insertSearchMovies(responseIds);
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(Throwable t) {
                showWorkingOffline();
            }

            @Override
            public void onErrorResponse(int statusCode) {
                showWorkingOffline();
            }
        });
    }

    public ApiManager getRetrofitApi() {
        return retrofitApi;
    }

    public void showWorkingOffline() {
        Context ctx = MovieManApplication.getAppContext();
        Toast.makeText(ctx, ctx.getString(R.string.working_offline), Toast.LENGTH_SHORT).show();
    }
}
