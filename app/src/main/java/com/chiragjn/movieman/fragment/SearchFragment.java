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
import com.chiragjn.movieman.databinding.FragmentSearchBinding;
import com.chiragjn.movieman.injector.component.DaggerAppComponent;
import com.chiragjn.movieman.networking.DataFetch;

import javax.inject.Inject;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    @Inject
    protected DataFetch fetcher;

    public SearchFragment() {
        DaggerAppComponent.create().injectField(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}