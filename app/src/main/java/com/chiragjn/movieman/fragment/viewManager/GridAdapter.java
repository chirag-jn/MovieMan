package com.chiragjn.movieman.fragment.viewManager;

import android.content.Context;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chiragjn.movieman.Endpoints;
import com.chiragjn.movieman.R;
import com.chiragjn.movieman.networking.dao.Movie;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    Context ctx;

    ArrayList<Movie> movieList;
    LayoutInflater inflater;

    public GridAdapter(Context ctx) {
        this.ctx = ctx;
        movieList = new ArrayList<>();
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.ViewHolder holder, int position) {
        holder.setValues(movieList.get(position));
    }

    public void setList(ArrayList<Movie> list) {
        movieList = list;
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Movie> list) {
        int lastIdx = movieList.size()-1;
        movieList.addAll(list);
        notifyItemRangeChanged(lastIdx, list.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // TODO: Use CustomLayout-> MovieView

        private final TextView popularityScore;
        private final ImageView bookmarkBtn;
        private SimpleDraweeView posterImg;
        private Movie movie;

        public ViewHolder(View view) {
            super(view);
            popularityScore = view.findViewById(R.id.movieRating);
            bookmarkBtn = view.findViewById(R.id.movieBookmark);
            posterImg = view.findViewById(R.id.movieBgd);

            bookmarkBtn.setOnClickListener(v -> bookMarkOnClick(ctx));
        }

        public void setValues(Movie movie) {
            if (movie != null) {
                this.movie = movie;
                String avgVote = movie.getVoteAverage() + "";
                popularityScore.setText(avgVote);
                Log.v("Chirag", Endpoints.IMAGE_URL.concat(movie.getPosterPath()));
                posterImg.setImageURI(Uri.parse(Endpoints.IMAGE_URL.concat(movie.getPosterPath())));
            }
        }

        private void bookMarkOnClick(Context ctx) {
            bookmarkBtn.setColorFilter(ContextCompat.getColor(ctx, R.color.bookmark), PorterDuff.Mode.SRC_IN);
        }
    }
}
