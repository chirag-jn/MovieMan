package com.chiragjn.movieman.networking.listener;

public interface ErrorListener {
    void onErrorResponse(Throwable t);

    void onErrorResponse(int statusCode);
}
