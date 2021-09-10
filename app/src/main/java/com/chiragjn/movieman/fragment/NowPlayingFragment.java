package com.chiragjn.movieman.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chiragjn.movieman.R;
import com.chiragjn.movieman.activity.HomeActivity;
import com.chiragjn.movieman.fragment.viewManager.GridAdapter;
import com.chiragjn.movieman.networking.dao.Movie;
import com.chiragjn.movieman.networking.dao.TmdbResponseData;
import com.chiragjn.movieman.networking.listener.ErrorListener;
import com.chiragjn.movieman.networking.listener.ResponseListener;
import com.chiragjn.movieman.utils.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class NowPlayingFragment extends Fragment {

    boolean isLoading;
    boolean isLastPage;
    int currentPage;
    GridAdapter adapter;

    public RecyclerView gridView;

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
        gridView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.COLUMNS));

        adapter = new GridAdapter(getActivity());
        gridView.setAdapter(adapter);

        setScrollListener();
    }

    private void setScrollListener() {

        isLoading = false;
        isLastPage = false;
        currentPage = 0;

        gridView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleCount = Objects.requireNonNull(gridView.getLayoutManager()).getChildCount();
                int totalCount = gridView.getLayoutManager().getItemCount();
                int firstVisiblePosition = ((LinearLayoutManager) gridView.getLayoutManager()).findFirstVisibleItemPosition();

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
                        adapter.addAll((ArrayList<Movie>) response.getResults());
                    } else {
                        adapter.setList((ArrayList<Movie>) response.getResults());
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