package com.mobdeve.group11.assist.database;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "images_table",
        foreignKeys = {@ForeignKey(entity = Contact.class, parentColumns = "id", childColumns = "id")},
        primaryKeys = {"id"})
public class ThumbnailImage {

    @ColumnInfo
    @NonNull
    private int id;

    @ColumnInfo
    private Bitmap image;

    public ThumbnailImage(Integer id, Bitmap image){
        this.id = id;
        this.image = image;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public Bitmap  getImage() { return image; }

    public void setImage(Bitmap image){ this.image = image; }
}