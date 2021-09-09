package com.chiragjn.movieman.networking.listener;

public interface ResponseListener<T> {
    void onResponse(T response, int statusCode);
}
