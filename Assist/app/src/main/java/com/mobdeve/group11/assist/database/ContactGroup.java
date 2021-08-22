package com.mobdeve.group11.assist.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_groups_table")
public class ContactGroup {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo
    private Integer id;

    @ColumnInfo
    private String name;

    public ContactGroup(String name){
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
