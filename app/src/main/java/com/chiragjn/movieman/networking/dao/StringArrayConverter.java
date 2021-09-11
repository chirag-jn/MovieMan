package com.chiragjn.movieman.networking.dao;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class StringArrayConverter {
    @TypeConverter
    public static String[] fromString(String value) {
        Type listType = new TypeToken<String[]>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(String[] list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}