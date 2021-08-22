package com.mobdeve.group11.assist.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = ContactGroup.class, parentColumns = "id", childColumns = "groupId"),
        @ForeignKey(entity = Event.class, parentColumns = "id", childColumns = "eventId")},
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
