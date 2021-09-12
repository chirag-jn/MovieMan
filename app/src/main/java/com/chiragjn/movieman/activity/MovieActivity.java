package com.chiragjn.movieman.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chiragjn.movieman.R;
import com.chiragjn.movieman.databinding.ActivityMovieBinding;
import com.chiragjn.movieman.injector.component.DaggerAppComponent;
import com.chiragjn.movieman.networking.Endpoints;
import com.chiragjn.movieman.networking.database.DatabaseManager;
import com.chiragjn.movieman.networking.entity.Movie;

import javax.inject.Inject;

public class MovieActivity extends BaseActivity {

    @Inject
    protected DatabaseManager dbManager;

    public static final String MOVIE_ID_KEY = "movie_id";

    private int movie_id;
    private Movie movie;

    private ActivityMovieBinding binding;

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerAppComponent.create().injectField(this);
        getIdFromBundle();
        bindView();
        populateView();
    }

    @Override
    void bindView() {
        ctx = this;
        binding = ActivityMovieBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    void getIdFromBundle() {
        Uri data = getIntent().getData();
        if (data != null) {
            try {
                String url = data.toString();
                String[] pathArr = url.split("/");
                movie_id = Integer.parseInt(pathArr[pathArr.length-1]);
            } catch (Exception e) {
                Toast.makeText(this, getString(R.string.invalid_code), Toast.LENGTH_SHORT).show();
                this.finishAffinity();
            }
        } else {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String value = extras.getString(MOVIE_ID_KEY);
                if(value!=null) {
                    movie_id = Integer.parseInt(value);
                } else {
                    movie_id = -1;
                }
            }
        }
    }

    void populateView() {
        findMovie();
        setShare();
        setBackPress();
    }

    void populateMovie() {
        if(movie!=null) {
            binding.name.setText(movie.getTitle());
            binding.summary.setText(movie.getOverview());
            binding.share.setVisibility(View.VISIBLE);
            if (movie.getPosterPath() != null && movie.getPosterPath().length() > 0) {
                Uri uri = Uri.parse(Endpoints.IMAGE_URL.concat(movie.getPosterPath()));
                binding.imagePoster.setImageURI(uri);
            }
        }
    }

    void findMovie() {
        AsyncTask.execute(() -> {
            movie = dbManager.getMoviefromId(movie_id);
            runOnUiThread(() -> {
                if (movie == null) {
                    Toast.makeText(ctx, getString(R.string.invalid_code), Toast.LENGTH_SHORT).show();
//                            TODO: Find via retrofit
                }
                if (movie == null) {
                    Toast.makeText(ctx, getString(R.string.invalid_movie), Toast.LENGTH_SHORT).show();
                    finish();
                }
                populateMovie();
            });
        });
    }

    void setShare() {
        binding.share.setOnClickListener(view -> {
            String message = getString(R.string.refer_msg);
            if (movie!=null) {
                message = message.concat("\n").concat(movie.getTitle());
            }
            message = message.concat("\n").concat(buildShareURL());
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(intent, getString(R.string.share_using)));
        });
    }

    void setBackPress() {
        binding.buttonBack.setOnClickListener(view -> onBackPressed());
    }

    String buildShareURL() {
        return getString(R.string.share_url).concat(String.valueOf(movie_id));
    }
}