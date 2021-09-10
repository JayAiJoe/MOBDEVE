package com.mobdeve.group11.assist.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "event_groupings_table",
        foreignKeys = {
        @ForeignKey(entity = ContactGroup.class, parentColumns = "id", childColumns = "groupId", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Event.class, parentColumns = "id", childColumns = "eventId", onDelete = ForeignKey.CASCADE)},
        primaryKeys = {"groupId", "eventId"})
public class EventGrouping {

    @ColumnInfo
    @NonNull
    private Integer groupId;

    @ColumnInfo @NonNull
    private Integer eventId;

    public EventGrouping(Integer groupId, Integer eventId){
        this.groupId = groupId;
        this.eventId = eventId;
    }

    @NonNull
    public Integer getGroupId() {
        return groupId;
    }

    @NonNull
    public Integer getEventId() {
        return eventId;
    }
}
