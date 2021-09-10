package com.chiragjn.movieman.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chiragjn.movieman.R;
import com.chiragjn.movieman.activity.HomeActivity;
import com.chiragjn.movieman.networking.dao.Movie;
import com.chiragjn.movieman.networking.dao.TmdbResponseData;
import com.chiragjn.movieman.networking.listener.ErrorListener;
import com.chiragjn.movieman.networking.listener.ResponseListener;
import com.chiragjn.movieman.utils.Constants;

import java.util.ArrayList;

public class NowPlayingFragment extends Fragment {

    boolean isLoading;
    boolean isLastPage;
    int currentPage;
    ArrayList<Movie> movies;

    public GridView gridView;

    public NowPlayingFragment() {
    }

    public static NowPlayingFragment newInstance() {
        NowPlayingFragment fragment = new NowPlayingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridView = view.findViewById(R.id.moviesGrid);
        gridView.setNumColumns(Constants.COLUMNS);

        setScrollListener();
    }

    private void setScrollListener() {

        isLoading = false;
        isLastPage = false;
        currentPage = 0;
        movies = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                int visibleCount = layoutManager.getChildCount();
                int totalCount = layoutManager.getItemCount();
                int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();

                boolean isNotLoading_ifNotLast = !isLoading && !isLastPage;
                boolean ifAtLast = firstVisiblePosition + visibleCount >= totalCount;
                boolean isValidFirstItem = firstVisiblePosition >= 0;
                boolean isTotalMoreThanVisible = totalCount >= Constants.ITEMS_PER_PAGE;

                boolean shouldLoadMore = isValidFirstItem && ifAtLast && isTotalMoreThanVisible && isNotLoading_ifNotLast;

                if (shouldLoadMore) {
                    loadItems(false);
                }
            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                int visibleCount = layoutManager.getChildCount();
                int totalCount = layoutManager.getItemCount();
                int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();

                boolean isNotLoading_ifNotLast = !isLoading && !isLastPage;
                boolean ifAtLast = firstVisiblePosition + visibleCount >= totalCount;
                boolean isValidFirstItem = firstVisiblePosition >= 0;
                boolean isTotalMoreThanVisible = totalCount >= Constants.ITEMS_PER_PAGE;

                boolean shouldLoadMore = isValidFirstItem && ifAtLast && isTotalMoreThanVisible && isNotLoading_ifNotLast;

                if (shouldLoadMore) {
                    loadItems(false);
                }
            }
        });

        loadItems(true);
    }

    private void loadItems(boolean isFirstPage) {
        isLoading = true;
        currentPage = currentPage + 1;

        if (getActivity() != null) {

            ((HomeActivity) getActivity()).getApi().getNowPlayingMovies(currentPage, new ResponseListener<TmdbResponseData>() {
                @Override
                public void onResponse(TmdbResponseData response, int statusCode) {
                    if (!isFirstPage) {
                        movies.addAll(response.getResults());
                    } else {
                        movies = new ArrayList<>(response.getResults());
                    }
                    isLoading = false;
                    isLastPage = currentPage == response.getTotalPages();
                }
            }, new ErrorListener() {
                @Override
                public void onErrorResponse(Throwable t) {
//                TODO: Show Internet Disconnection Snackbar
                    Log.v(getString(R.string.app_name), t.getMessage());
                }

                @Override
                public void onErrorResponse(int statusCode) {
//                TODO: Show Internet Disconnection Snackbar
                    Log.v(getString(R.string.app_name), "Error: " + statusCode);
                }
            });
        }
    }
}