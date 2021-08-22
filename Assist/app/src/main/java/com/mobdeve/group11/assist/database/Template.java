package com.mobdeve.group11.assist.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Template {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo
    private Integer id;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String subject;

    @ColumnInfo
    private String content;

    public Template(String title, String subject, String content)
    {
        this.title = title;
        this.subject = subject;
        this.content = content;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
