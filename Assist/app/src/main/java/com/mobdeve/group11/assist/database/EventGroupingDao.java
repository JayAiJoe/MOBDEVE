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

    @Query("SELECT groupId FROM event_groupings_table WHERE eventId=:id")
    public LiveData<List<Integer>> findGroupingsByEventId(Integer id);

    @Query("SELECT eventId FROM event_groupings_table WHERE groupId=:id")
    public LiveData<List<Integer>> findGroupingsByGroupId(Integer id);

    @Query("DELETE FROM event_groupings_table WHERE eventId=:id")
    public Integer deleteAllGroupingsInEvent(Integer id);

}
