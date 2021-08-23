package com.mobdeve.group11.assist.database;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.sql.Time;
import java.time.LocalDate;

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

    @TypeConverter
    public static Time timeFromTimestamp(Long value){
        return value == null ? null : new Time(value);
    }

    @TypeConverter
    public static Long timeToTimestamp(Time time){
        return time == null ? null : time.getTime();
    }
}
