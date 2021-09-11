package com.chiragjn.movieman.networking.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = {"id"}, unique = true))
public class Bookmark {

    @PrimaryKey
    private int id;
}
