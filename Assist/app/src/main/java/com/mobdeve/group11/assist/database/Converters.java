package com.mobdeve.group11.assist.database;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

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
}
