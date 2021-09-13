package com.chiragjn.movieman.fragment.viewManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
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

    private TextView popularityScore;
    private ImageView bookmarkBtn;
    private SimpleDraweeView posterImg;
    private TextView movieYear;
    private TextView movieTitle;
    private Movie movie;
    private ImageButton star2;
    private ImageButton star3;
    private ImageButton star4;
    private ImageButton star5;


    public ViewHolder(View view, Context ctx, int type) {
        super(view);

        if (type == 0) {
            popularityScore = view.findViewById(R.id.movieRating);
            bookmarkBtn = view.findViewById(R.id.movieBookmark);
            posterImg = view.findViewById(R.id.movieBgd);
            bookmarkBtn.setOnClickListener(v -> bookMarkOnClick(ctx));

            star2 = view.findViewById(R.id.star_image2);
            star3 = view.findViewById(R.id.star_image3);
            star4 = view.findViewById(R.id.star_image4);
            star5 = view.findViewById(R.id.star_image5);

        } else if (type == 1) {
            popularityScore = view.findViewById(R.id.movieRating);
            movieYear = view.findViewById(R.id.movieYear);
            movieTitle = view.findViewById(R.id.movieTitle);
        }

        view.setOnClickListener(vClick -> {
            if (movie != null && movie.getId() >= 0) {
                String value = String.valueOf(movie.getId());
                Intent i = new Intent(ctx, MovieActivity.class);
                i.putExtra(MovieActivity.MOVIE_ID_KEY, value);
                ctx.startActivity(i);
            }
        });
    }

    public void setValues(Movie movie, int type) {
        if (movie != null) {
            this.movie = movie;

            String avgVote = movie.getRating() + "";

            if (type == 0) {
                popularityScore.setText(avgVote);

                if (movie.getPosterPath() != null && movie.getPosterPath().length() > 0) {
                    Uri uri = Uri.parse(Endpoints.IMAGE_URL.concat(movie.getPosterPath()));
                    posterImg.setImageURI(uri);
                }

                setStars(Float.parseFloat(avgVote));
            } else if (type == 1) {
                popularityScore.setText(avgVote);
                movieTitle.setText(movie.getTitle());
                movieYear.setText(movie.getYear());
            }
        }
    }

    void setStars(float vote) {
        if (vote <= 4) {
            star5.setVisibility(View.GONE);
        }
        if (vote <= 3) {
            star4.setVisibility(View.GONE);
        }
        if (vote <= 2) {
            star3.setVisibility(View.GONE);
        }
        if (vote <= 1) {
            star2.setVisibility(View.GONE);
        }
    }

    private void bookMarkOnClick(Context ctx) {
        bookmarkBtn.setColorFilter(ContextCompat.getColor(ctx, R.color.bookmark), PorterDuff.Mode.SRC_IN);
    }
}