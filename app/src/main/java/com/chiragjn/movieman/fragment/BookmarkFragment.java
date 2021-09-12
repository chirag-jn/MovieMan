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

import com.chiragjn.movieman.databinding.FragmentBookmarkBinding;
import com.chiragjn.movieman.fragment.viewManager.GridAdapter;
import com.chiragjn.movieman.networking.viewmodel.MovieViewModel;
import com.chiragjn.movieman.utils.Constants;

public class BookmarkFragment extends Fragment {

    GridAdapter adapter;

    private FragmentBookmarkBinding binding;

    public BookmarkFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MovieViewModel viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        adapter = new GridAdapter(getContext(), 0);
        viewModel.getAllBookmarkMoviesPaged().observe(this, adapter::submitList);
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

        RecyclerView gridView = binding.moviesGrid;

        gridView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.COLUMNS));
        gridView.setItemAnimator(new DefaultItemAnimator());
        gridView.setHasFixedSize(false);

        gridView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}