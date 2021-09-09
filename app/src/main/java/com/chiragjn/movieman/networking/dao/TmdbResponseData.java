package com.chiragjn.movieman.networking.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TmdbResponseData {

    @SerializedName("dates")
    private Dates dates;

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("results")
    private List<Movie> results;

    @Override
    public String toString() {
        return "ResponseData{" +
                "totalResults=" + totalResults +
                '}';
    }

    public Dates getDates() {
        return dates;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Movie> getResults() {
        return results;
    }
}
