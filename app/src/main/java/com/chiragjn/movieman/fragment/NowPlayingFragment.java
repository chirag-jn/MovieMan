package com.chiragjn.movieman.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chiragjn.movieman.R;
import com.chiragjn.movieman.fragment.viewManager.GridAdapter;
import com.chiragjn.movieman.injector.component.DaggerAppComponent;
import com.chiragjn.movieman.networking.ApiManager;
import com.chiragjn.movieman.networking.ViewModel.MovieViewModel;
import com.chiragjn.movieman.networking.entity.Movie;
import com.chiragjn.movieman.networking.entity.util.TmdbResponseData;
import com.chiragjn.movieman.networking.database.DatabaseManager;
import com.chiragjn.movieman.networking.listener.ErrorListener;
import com.chiragjn.movieman.networking.listener.ResponseListener;
import com.chiragjn.movieman.utils.Constants;

import java.util.ArrayList;

import javax.inject.Inject;

public class NowPlayingFragment extends Fragment {

    int currentPage;
    GridAdapter adapter;

    public RecyclerView gridView;

    @Inject
    protected DatabaseManager dbManager;

    @Inject
    protected ApiManager retrofitApi;

    public NowPlayingFragment() {
        DaggerAppComponent.create().injectField(this);
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

        MovieViewModel viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        adapter = new GridAdapter(getContext());
        viewModel.getAllMoviesPaged().observe(this, adapter::submitList);
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
        gridView.setItemAnimator(new DefaultItemAnimator());
        gridView.setHasFixedSize(false);

        gridView.setAdapter(adapter);

        currentPage = 0;
        loadItems();
    }

    private void loadItems() {
        currentPage = currentPage + 1;

        if (getActivity() != null) {

            retrofitApi.getNowPlayingMovies(currentPage, new ResponseListener<TmdbResponseData>() {
                @Override
                public void onResponse(TmdbResponseData response, int statusCode) {
                    dbManager.insertMovies((ArrayList<Movie>) response.getResults());
                    boolean isLastPage = currentPage == response.getTotalPages();

                    if (!isLastPage) {
                        loadItems();
                    }
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