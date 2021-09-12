package com.chiragjn.movieman.fragment.viewManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chiragjn.movieman.R;
import com.chiragjn.movieman.activity.MovieActivity;
import com.chiragjn.movieman.networking.Endpoints;
import com.chiragjn.movieman.networking.entity.Movie;
import com.facebook.drawee.view.SimpleDraweeView;

public class ViewHolder extends RecyclerView.ViewHolder {

    private final TextView popularityScore;
    private final ImageView bookmarkBtn;
    private final SimpleDraweeView posterImg;
    private Movie movie;

    public ViewHolder(View view, Context ctx) {
        super(view);
        popularityScore = view.findViewById(R.id.movieRating);
        bookmarkBtn = view.findViewById(R.id.movieBookmark);
        posterImg = view.findViewById(R.id.movieBgd);

        bookmarkBtn.setOnClickListener(v -> bookMarkOnClick(ctx));

        view.setOnClickListener(vClick -> {
            if (movie != null && movie.getId() >= 0) {
                String value = String.valueOf(movie.getId());
                Intent i = new Intent(ctx, MovieActivity.class);
                i.putExtra(MovieActivity.MOVIE_ID_KEY, value);
                ctx.startActivity(i);
            }
        });
    }

    public void setValues(Movie movie) {
        if (movie != null) {
            this.movie = movie;
            String avgVote = movie.getVoteAverage() + "";
            popularityScore.setText(avgVote);

            if (movie.getPosterPath() != null && movie.getPosterPath().length() > 0) {
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