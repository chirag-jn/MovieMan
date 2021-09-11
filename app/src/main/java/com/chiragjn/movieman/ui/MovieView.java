package com.chiragjn.movieman.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.chiragjn.movieman.R;
import com.chiragjn.movieman.networking.entity.Movie;

public class MovieView extends ConstraintLayout {

    private TextView popularityScore;

    private ImageView bookmarkBtn;

    private ImageView posterImg;

    private Movie movie;

    public MovieView(Context ctx) {
        super(ctx);
        initialise(ctx, null);
    }

    public MovieView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        initialise(ctx, attrs);
    }

    public MovieView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise(context, attrs);
    }

    public MovieView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @AttrRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialise(context, attrs);
    }

    private void initialise(Context ctx, @Nullable AttributeSet attrs) {
        inflateView(ctx, attrs);
    }

    private void inflateView(Context ctx, @Nullable AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.movie_item, this, true);

        popularityScore = rootView.findViewById(R.id.movieRating);
        bookmarkBtn = rootView.findViewById(R.id.movieBookmark);
        posterImg = rootView.findViewById(R.id.movieBgd);

        bookmarkBtn.setOnClickListener(view -> bookMarkOnClick(ctx));
    }

    public void setValues(Movie movie) {
        if (movie != null) {
            this.movie = movie;
            String avgVote = movie.getVoteAverage() + "";
            popularityScore.setText(avgVote);
        }
    }

    private void bookMarkOnClick(Context ctx) {
        bookmarkBtn.setColorFilter(ContextCompat.getColor(ctx, R.color.bookmark), PorterDuff.Mode.SRC_IN);
    }

}
