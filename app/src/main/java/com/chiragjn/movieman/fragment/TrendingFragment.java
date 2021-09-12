package com.chiragjn.movieman.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chiragjn.movieman.R;
import com.chiragjn.movieman.databinding.FragmentNowPlayingBinding;
import com.chiragjn.movieman.databinding.FragmentTrendingBinding;
import com.chiragjn.movieman.injector.component.DaggerAppComponent;
import com.chiragjn.movieman.networking.DataFetch;

import javax.inject.Inject;

public class TrendingFragment extends Fragment {

    private FragmentTrendingBinding binding;

    @Inject
    protected DataFetch fetcher;

    public TrendingFragment() {
        DaggerAppComponent.create().injectField(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}