package com.chiragjn.movieman.networking.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = {"id"}, unique = true))
public class TrendingDay {

    @PrimaryKey
    private int id;
}
