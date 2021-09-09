package com.chiragjn.movieman.networking.dao;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("adult")
    private boolean isAdult;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("genre_ids")
    private int[] genreIds;

    @SerializedName("id")
    private int id;

    @SerializedName("original_language")
    private String language;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("video")
    private boolean isVideoAvailable;

    @SerializedName("vote_average")
    private float voteAverage;

    @SerializedName("popularity")
    private float popularity;

    @SerializedName("media_type")
    private String mediaType;

    public Movie() {}

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                '}';
    }
}
