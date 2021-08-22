package com.mobdeve.group11.assist.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts_table")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo
    private Integer id;

    @ColumnInfo
    private String firstName;

    @ColumnInfo
    private String lastName;

    @ColumnInfo
    private int contactNumber;

    @ColumnInfo
    private String guardian;

    public Contact(String firstName, String lastName, int contactNumber, String guardian){
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.guardian = guardian;
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

    public int getContactNumber() {
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

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }
}
