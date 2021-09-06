package com.mobdeve.group11.assist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface EventDao {

    @Insert
    public long insertEvent(Event event);

    @Update
    public void updateEvent(Event event);

    @Delete
    public void deleteEvent(Event event);

    @Query("SELECT * FROM events_table")
    public LiveData<List<Event>> loadAllEvents();

    @Query("SELECT * FROM events_table WHERE id =:id")
    public LiveData<Event> findEventById(Integer id);

    @Query("SELECT * FROM events_table WHERE date(date)=date(:d) ORDER BY timeStart")
    public LiveData<List<Event>> loadEventsOfTheDay(LocalDate d);

}
