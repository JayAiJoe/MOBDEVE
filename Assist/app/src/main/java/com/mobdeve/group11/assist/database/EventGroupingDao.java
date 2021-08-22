package com.mobdeve.group11.assist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventGroupingDao {

    @Insert
    public void insertEventGrouping(EventGrouping eventGrouping);

    @Delete
    public void deleteEventGrouping(EventGrouping eventGrouping);

    @Query("SELECT * FROM event_groupings_table WHERE eventId=:id")
    public LiveData<List<EventGrouping>> findGroupingsByEventId(Integer id);

    @Query("SELECT * FROM event_groupings_table WHERE groupId=:id")
    public LiveData<List<EventGrouping>> findGroupingsByGroupId(Integer id);

}
