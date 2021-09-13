package com.chiragjn.movieman.fragment;

import android.content.Intent;
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

import com.chiragjn.movieman.activity.MovieCollectionActivity;
import com.chiragjn.movieman.databinding.FragmentTrendingBinding;
import com.chiragjn.movieman.fragment.viewManager.GridAdapter;
import com.chiragjn.movieman.injector.component.DaggerAppComponent;
import com.chiragjn.movieman.networking.DataFetch;
import com.chiragjn.movieman.networking.viewmodel.MovieViewModel;
import com.chiragjn.movieman.utils.Constants;

import javax.inject.Inject;

public class TrendingFragment extends Fragment {

    private FragmentTrendingBinding binding;

    @Inject
    protected DataFetch fetcher;

    MovieViewModel viewModel;
    GridAdapter dayAdapter, weekAdapter;

    public TrendingFragment() {
        DaggerAppComponent.create().injectField(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        dayAdapter = new GridAdapter(getContext(), 0);
        weekAdapter = new GridAdapter(getContext(), 0);

        viewModel.getTop10TrendingDayMoviesPaged().observe(this, movies -> {
            updateViewDay(movies.size());
            dayAdapter.submitList(movies);
            movies.addWeakCallback(null, new PagedList.Callback() {
                @Override
                public void onChanged(int position, int count) { updateViewDay(count); }

                @Override
                public void onInserted(int position, int count) { }

                @Override
                public void onRemoved(int position, int count) { }
            });
        });

        viewModel.getTop10TrendingWeekMoviesPaged().observe(this, movies -> {
            updateViewWeek(movies.size());
            weekAdapter.submitList(movies);
            movies.addWeakCallback(null, new PagedList.Callback() {
                @Override
                public void onChanged(int position, int count) { updateViewWeek(count); }

                @Override
                public void onInserted(int position, int count) { }

                @Override
                public void onRemoved(int position, int count) { }
            });
        });
    }

    void updateViewDay(int count) {
        if (binding!=null) {
            if (count > 0) {
                binding.swipeRefreshDay.setVisibility(View.VISIBLE);
                binding.emptyListHeadDay.setVisibility(View.GONE);
                binding.todayViewAll.setVisibility(View.VISIBLE);
            } else {
                binding.swipeRefreshDay.setVisibility(View.GONE);
                binding.emptyListHeadDay.setVisibility(View.VISIBLE);
                binding.todayViewAll.setVisibility(View.GONE);
            }
        }
    }

    void updateViewWeek(int count) {
        if (binding!=null) {
            if (count > 0) {
                binding.swipeRefreshWeek.setVisibility(View.VISIBLE);
                binding.emptyListHeadWeek.setVisibility(View.GONE);
                binding.weekViewAll.setVisibility(View.VISIBLE);
            } else {
                binding.swipeRefreshWeek.setVisibility(View.GONE);
                binding.emptyListHeadWeek.setVisibility(View.VISIBLE);
                binding.weekViewAll.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrendingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetcher.loadTrendingDayItems(0);
        fetcher.loadTrendingWeekItems(0);
//        binding.swipeRefreshDay.setRefreshing(true);
//        binding.swipeRefreshWeek.setRefreshing(true);

        RecyclerView dayView = binding.dayMoviesGrid;
        dayView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.COLUMNS));
        dayView.setItemAnimator(new DefaultItemAnimator());
        dayView.setHasFixedSize(false);

        RecyclerView weekView = binding.weekMoviesGrid;
        weekView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.COLUMNS));
        weekView.setItemAnimator(new DefaultItemAnimator());
        weekView.setHasFixedSize(false);

        dayView.setAdapter(dayAdapter);
//        binding.swipeRefreshDay.setRefreshing(false);

        weekView.setAdapter(weekAdapter);
//        binding.swipeRefreshWeek.setRefreshing(false);

        binding.swipeRefreshDay.setOnRefreshListener(() -> {
            fetcher.cancelQueue();
            fetcher.deleteAllMovies();
            fetcher.loadNowPlayingItems(0);
            fetcher.loadTrendingWeekItems(0);
            fetcher.loadTrendingDayItems(0);
            binding.swipeRefreshDay.setRefreshing(false);
        });

        binding.swipeRefreshWeek.setOnRefreshListener(() -> {
            fetcher.cancelQueue();
            fetcher.deleteAllMovies();
            fetcher.loadNowPlayingItems(0);
            fetcher.loadTrendingWeekItems(0);
            fetcher.loadTrendingDayItems(0);
            binding.swipeRefreshWeek.setRefreshing(false);
        });

        binding.todayViewAll.setOnClickListener(view1 -> openCollectionActivity(0));

        binding.weekViewAll.setOnClickListener(view12 -> openCollectionActivity(1));
    }

    void openCollectionActivity(int type) {
        Intent intent = new Intent(getActivity(), MovieCollectionActivity.class);
        intent.putExtra(MovieCollectionActivity.TRENDING_TYPE, String.valueOf(type));
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}