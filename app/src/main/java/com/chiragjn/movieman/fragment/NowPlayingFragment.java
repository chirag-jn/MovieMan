package com.chiragjn.movieman.fragment;

import android.os.Bundle;
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
import com.chiragjn.movieman.networking.DataFetch;
import com.chiragjn.movieman.networking.viewmodel.MovieViewModel;
import com.chiragjn.movieman.utils.Constants;

import javax.inject.Inject;

public class NowPlayingFragment extends Fragment {

    GridAdapter adapter;

    public RecyclerView gridView;

    @Inject
    protected DataFetch fetcher;

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

        // TODO: Load view model using Dagger
        MovieViewModel viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        adapter = new GridAdapter(getContext());
        viewModel.getAllNowPlayingMoviesPaged().observe(this, adapter::submitList);
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

        fetcher.loadNowPlayingItems(0);
    }
}