package com.mobdeve.group11.assist.database;

import android.media.Image;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ThumbnailImageDao {
    @Insert
    public long insertImage(ThumbnailImage image);

    @Update
    public void updateImage(ThumbnailImage image);

    @Delete
    public void deleteImage(ThumbnailImage image);

    @Query("SELECT * FROM images_table where id = :imageId")
    public LiveData<ThumbnailImage> getImageByImageId(int imageId);
}