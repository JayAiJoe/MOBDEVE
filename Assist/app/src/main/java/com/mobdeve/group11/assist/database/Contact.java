package com.mobdeve.group11.assist.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts_table")
public class Contact {
    private Integer type;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo
    Integer id=0;

    @ColumnInfo
    private String firstName;

    @ColumnInfo
    private String lastName;

    @ColumnInfo
    private String contactNumber;

    @ColumnInfo
    private String guardian;

    @ColumnInfo
    private Integer thumbnailId;

    public Contact(String firstName, String lastName, String contactNumber, String guardian){
        this.id = null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.guardian = guardian;
        this.thumbnailId = -1;
    }

    public Contact() {
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getGuardian() {
        return guardian;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public Integer getType(){return type;}

    public void setType(int type){this.type = type;}

    public Integer getThumbnailId(){ return thumbnailId;}

    public void setThumbnailId(Integer id){ this.thumbnailId = id;}
}
