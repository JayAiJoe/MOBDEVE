package com.mobdeve.group11.assist.database;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "images_table")
public class ThumbnailImage {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    @NonNull
    private Integer id;

    @ColumnInfo
    private Bitmap image;

    public ThumbnailImage( Bitmap image){
        this.id = null;
        this.image = image;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public Bitmap  getImage() { return image; }

    public void setId(Integer id){this.id = id;}

    public void setImage(Bitmap image){ this.image = image; }
}