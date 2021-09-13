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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chiragjn.movieman.databinding.FragmentBookmarkBinding;
import com.chiragjn.movieman.fragment.viewManager.GridAdapter;
import com.chiragjn.movieman.networking.viewmodel.MovieViewModel;

public class BookmarkFragment extends Fragment {

    GridAdapter adapter;

    private FragmentBookmarkBinding binding;

    public BookmarkFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MovieViewModel viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        adapter = new GridAdapter(getContext(), 2);
        viewModel.getAllBookmarkMoviesPaged().observe(this, movies -> {
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
            } else {
                binding.swipeRefresh.setVisibility(View.GONE);
                binding.emptyListHead.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.swipeRefresh.setRefreshing(true);

        RecyclerView gridView = binding.moviesGrid;

        gridView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        gridView.setItemAnimator(new DefaultItemAnimator());
        gridView.setHasFixedSize(false);

        gridView.setAdapter(adapter);

        binding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}