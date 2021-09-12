package com.chiragjn.movieman.fragment.viewManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chiragjn.movieman.R;
import com.chiragjn.movieman.networking.entity.Movie;

import java.util.ArrayList;

public class TrendingHomeAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context ctx;

    ArrayList<Movie> movieList;
    LayoutInflater inflater;

    public TrendingHomeAdapter(Context ctx, ArrayList<Movie> list) {
        this.ctx = ctx;
        movieList = list;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    protected Movie getItem(int position) {
        return movieList.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view, ctx);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setValues(getItem(position));
    }
}
