package com.mobdeve.group11.assist.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_groups_table")
public class ContactGroup {
    private Integer type;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo
    Integer id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private Integer thumbnailId;

    public ContactGroup(String name){
        this.id = null;
        this.name = name;
        this.thumbnailId = -1;
    }

    public ContactGroup(){
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

    public Integer getType(){return type;}
    public void setType(int type){this.type = type;}

    public Integer getThumbnailId(){ return thumbnailId;}

    public void setThumbnailId(Integer id){ this.thumbnailId = id;}
}
