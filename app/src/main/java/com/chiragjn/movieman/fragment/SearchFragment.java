package com.chiragjn.movieman.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.chiragjn.movieman.R;
import com.chiragjn.movieman.databinding.FragmentSearchBinding;
import com.chiragjn.movieman.fragment.viewManager.GridAdapter;
import com.chiragjn.movieman.injector.component.DaggerAppComponent;
import com.chiragjn.movieman.networking.DataFetch;
import com.chiragjn.movieman.networking.viewmodel.MovieViewModel;

import javax.inject.Inject;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    GridAdapter adapter;

    @Inject
    protected DataFetch fetcher;

    public SearchFragment() {
        DaggerAppComponent.create().injectField(this);
        fetcher.deleteSearchedMovies();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MovieViewModel viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        adapter = new GridAdapter(getContext(), 1);
        viewModel.getSearchedMoviesPaged().observe(this, movies -> {
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
                binding.moviesList.setVisibility(View.VISIBLE);
                binding.emptyListHead.setVisibility(View.GONE);
            } else {
                binding.moviesList.setVisibility(View.GONE);
                binding.emptyListHead.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String resp = editable.toString();
                String infoStr;
                if (resp.length() > 3) {
                    fetcher.loadSearchedMovies(resp, 0);
                    infoStr = getString(R.string.showing_results_for).concat(" ").concat(resp);
                } else {
                    infoStr = getString(R.string.enter_more_than_three);
                }
                binding.searchInfo.setText(infoStr);
            }
        });

        RecyclerView gridView = binding.moviesList;

        gridView.setLayoutManager(new LinearLayoutManager(getActivity()));
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