package com.mobdeve.group11.assist.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(tableName = "events_table", foreignKeys = {@ForeignKey(entity = Template.class, parentColumns = "id", childColumns = "templateId", onUpdate = ForeignKey.CASCADE)})
public class Event {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo
    Integer id;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private LocalDate date;

    @ColumnInfo
    private LocalTime timeStart;

    @ColumnInfo
    private LocalTime timeEnd;

    @ColumnInfo
    private Integer templateId;

    @ColumnInfo
    private int reminder;

    public Event(String title, LocalDate date, LocalTime timeStart, LocalTime timeEnd, Integer templateId, int reminder){
        this.id = null;
        this.title = title;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.templateId = templateId;
        this.reminder = reminder;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public int getReminder() {
        return reminder;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public void setReminder(int reminder) {
        this.reminder = reminder;
    }
}
