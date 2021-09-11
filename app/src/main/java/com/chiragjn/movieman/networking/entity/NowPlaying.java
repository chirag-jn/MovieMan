package com.chiragjn.movieman.networking.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = {"id"}, unique = true))
public class NowPlaying {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NowPlaying(int id) {
        this.id = id;
    }
}
