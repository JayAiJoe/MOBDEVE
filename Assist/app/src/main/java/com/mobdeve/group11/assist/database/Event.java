package com.mobdeve.group11.assist.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.time.LocalDate;

@Entity(foreignKeys = {@ForeignKey(entity = Template.class, parentColumns = "id", childColumns = "templateId", onUpdate = ForeignKey.CASCADE)})
public class Event {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo
    private Integer id;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private LocalDate date;

    @ColumnInfo
    private Time timeStart;

    @ColumnInfo
    private Time timeEnd;

    @ColumnInfo
    private Integer templateId;

    @ColumnInfo
    private int reminder;

    public Event(String title, LocalDate date, Time timeStart, Time timeEnd, Integer templateId, int reminder){
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

    public Time getTimeStart() {
        return timeStart;
    }

    public Time getTimeEnd() {
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

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public void setReminder(int reminder) {
        this.reminder = reminder;
    }
}
