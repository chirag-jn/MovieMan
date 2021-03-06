package com.chiragjn.movieman.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chiragjn.movieman.databinding.FragmentNowPlayingBinding;
import com.chiragjn.movieman.fragment.viewManager.GridAdapter;
import com.chiragjn.movieman.injector.component.DaggerAppComponent;
import com.chiragjn.movieman.networking.DataFetch;
import com.chiragjn.movieman.networking.viewmodel.MovieViewModel;
import com.chiragjn.movieman.utils.Constants;

import javax.inject.Inject;

public class NowPlayingFragment extends Fragment {

    GridAdapter adapter;

    private FragmentNowPlayingBinding binding;

    @Inject
    protected DataFetch fetcher;

    public NowPlayingFragment() {
        DaggerAppComponent.create().injectField(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MovieViewModel viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        adapter = new GridAdapter(getContext(), 0);
        viewModel.getAllNowPlayingMoviesPaged().observe(this, adapter::submitList);
        viewModel.getAllNowPlayingMoviesPaged().observe(this, movies -> {
            updateView(movies.size());
            adapter.submitList(movies);
            movies.addWeakCallback(null, new PagedList.Callback() {
                @Override
                public void onChanged(int position, int count) { updateView(count); }

                @Override
                public void onInserted(int position, int count) { }

                @Override
                public void onRemoved(int position, int count) { }
            });
        });
    }

    void updateView(int count) {
        if (binding!=null) {
            if (count > 0) {
                binding.swipeRefresh.setVisibility(View.VISIBLE);
                binding.emptyListHead.setVisibility(View.GONE);
                binding.moveToTopBtn.setVisibility(View.VISIBLE);
            } else {
                binding.swipeRefresh.setVisibility(View.GONE);
                binding.emptyListHead.setVisibility(View.VISIBLE);
                binding.moveToTopBtn.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNowPlayingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetcher.loadNowPlayingItems(0);
//        binding.swipeRefresh.setRefreshing(true);

        RecyclerView gridView = binding.moviesGrid;

        gridView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.COLUMNS));
        gridView.setItemAnimator(new DefaultItemAnimator());
        gridView.setHasFixedSize(false);

        gridView.setAdapter(adapter);

        binding.moveToTopBtn.setOnClickListener(view1 -> gridView.smoothScrollToPosition(0));

//        binding.swipeRefresh.setRefreshing(false);

        binding.swipeRefresh.setOnRefreshListener(() -> {
            fetcher.cancelQueue();
            fetcher.deleteAllMovies();
            fetcher.loadNowPlayingItems(0);
            fetcher.loadTrendingWeekItems(0);
            fetcher.loadTrendingDayItems(0);
            binding.swipeRefresh.setRefreshing(false);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}