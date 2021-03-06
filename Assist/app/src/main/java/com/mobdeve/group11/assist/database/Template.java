package com.mobdeve.group11.assist.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "templates_table")
public class Template {
    private Integer type;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo
    Integer id=0;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String subject;

    @ColumnInfo
    private String content;

    public Template(String title, String subject, String content)
    {
        this.id = null;
        this.title = title;
        this.subject = subject;
        this.content = content;
    }
    public Template(){

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

    public Integer getType(){return type;}
    public void setType(int type){this.type = type;}
}
