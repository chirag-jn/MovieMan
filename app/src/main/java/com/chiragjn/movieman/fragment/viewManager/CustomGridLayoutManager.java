package com.chiragjn.movieman.fragment.viewManager;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;

import com.chiragjn.movieman.utils.Constants;

public class CustomGridLayoutManager extends GridLayoutManager {
    private boolean isScrollEnabled = false;

    public CustomGridLayoutManager(Context context) {
        super(context, Constants.COLUMNS);
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}