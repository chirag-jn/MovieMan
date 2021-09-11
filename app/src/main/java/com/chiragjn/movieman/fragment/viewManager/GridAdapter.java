package com.chiragjn.movieman.fragment.viewManager;

import android.content.Context;
import android.graphics.PorterDuff;
import android.net.Uri;
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

import com.chiragjn.movieman.networking.Endpoints;
import com.chiragjn.movieman.R;
import com.chiragjn.movieman.networking.entity.Movie;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class GridAdapter extends PagedListAdapter<Movie, GridAdapter.ViewHolder> {

    Context ctx;

    ArrayList<Movie> movieList;
    LayoutInflater inflater;

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

    public GridAdapter(Context ctx) {
        super(DIFF_CALLBACK);
        this.ctx = ctx;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.ViewHolder holder, int position) {
        holder.setValues(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // TODO: Use CustomLayout-> MovieView

        private final TextView popularityScore;
        private final ImageView bookmarkBtn;
        private final SimpleDraweeView posterImg;
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

                if (movie.getPosterPath()!=null && movie.getPosterPath().length() > 0) {
                    Uri uri = Uri.parse(Endpoints.IMAGE_URL.concat(movie.getPosterPath()));

                    posterImg.setImageURI(uri);
                } else {
//                    TODO: Set placeholder image
                }
            }
        }

        private void bookMarkOnClick(Context ctx) {
            bookmarkBtn.setColorFilter(ContextCompat.getColor(ctx, R.color.bookmark), PorterDuff.Mode.SRC_IN);
        }
    }
}
