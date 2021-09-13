package com.chiragjn.movieman.fragment.viewManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.chiragjn.movieman.R;
import com.chiragjn.movieman.activity.MovieActivity;
import com.chiragjn.movieman.networking.Endpoints;
import com.chiragjn.movieman.networking.entity.Movie;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class GridAdapter extends PagedListAdapter<Movie, ViewHolder> {

    Context ctx;

    ArrayList<Movie> movieList;
    LayoutInflater inflater;

    int viewNum;

    private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId() == newItem.getId();
        }
    };

    public GridAdapter(Context ctx, int viewNum) {
        super(DIFF_CALLBACK);
        this.ctx = ctx;
        this.viewNum = viewNum;
        movieList = new ArrayList<>();
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Nullable
    @Override
    protected Movie getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewNum) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_search_item, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_bookmark_item, parent, false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
                break;
        }
        return new ViewHolder(view, ctx, viewNum);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setValues(getItem(position), viewNum);
    }
}
