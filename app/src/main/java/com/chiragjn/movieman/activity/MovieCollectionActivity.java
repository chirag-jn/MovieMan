package com.chiragjn.movieman.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chiragjn.movieman.databinding.ActivityMovieCollectionBinding;
import com.chiragjn.movieman.fragment.viewManager.GridAdapter;
import com.chiragjn.movieman.injector.component.DaggerAppComponent;
import com.chiragjn.movieman.networking.DataFetch;
import com.chiragjn.movieman.networking.viewmodel.MovieViewModel;
import com.chiragjn.movieman.utils.Constants;

import javax.inject.Inject;

public class MovieCollectionActivity extends BaseActivity {

    public static final String TRENDING_TYPE = "type";

    @Inject
    protected DataFetch fetcher;

    private Context ctx;

    GridAdapter adapter;

    int type;

    private ActivityMovieCollectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerAppComponent.create().injectField(this);
        getIdFromBundle();
        bindView();
    }

    void getIdFromBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString(TRENDING_TYPE);
            if (value != null) {
                type = Integer.parseInt(value);
            } else {
                type = -1;
            }
        }
    }

    void setAdapter() {
        MovieViewModel viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        adapter = new GridAdapter(this);

        switch (type) {
            case 0:
                viewModel.getAllTrendingDayMoviesPaged().observe(this, adapter::submitList);
                break;
            case 1:
                viewModel.getAllTrendingWeekMoviesPaged().observe(this, adapter::submitList);
                break;
            default:
                break;
        }
    }

    @Override
    void bindView() {
        ctx = this;
        binding = ActivityMovieCollectionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setAdapter();

        RecyclerView gridView = binding.moviesGrid;

        gridView.setLayoutManager(new GridLayoutManager(this, Constants.COLUMNS));
        gridView.setItemAnimator(new DefaultItemAnimator());
        gridView.setHasFixedSize(false);

        gridView.setAdapter(adapter);

        setBackPress();
    }

    void setBackPress() {
        binding.buttonBack.setOnClickListener(view -> onBackPressed());
    }
}