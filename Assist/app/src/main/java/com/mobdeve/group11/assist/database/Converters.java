package com.mobdeve.group11.assist.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;

public class Converters {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate dateFromTimestamp(Long value) {
        return value == null ? null : LocalDate.ofEpochDay(value);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static Long dateToTimestamp(LocalDate date) {
        return date == null ? null : date.toEpochDay();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalTime timeFromTimestamp(Long value){
        return value == null ? null : LocalTime.ofNanoOfDay(value);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static Long timeToTimestamp(LocalTime time){
        return time == null ? null : time.toNanoOfDay();
    }

    @TypeConverter
    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,60, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        if(temp==null)
        {
            return null;
        }
        else
            return temp;
    }

    @TypeConverter
    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            if (bitmap == null) {
                return null;
            } else {
                return bitmap;
            }

        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
