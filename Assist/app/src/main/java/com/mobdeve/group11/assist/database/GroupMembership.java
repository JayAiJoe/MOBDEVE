package com.mobdeve.group11.assist.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "group_memberships_table",
        foreignKeys = {
        @ForeignKey(entity = Contact.class, parentColumns = "id", childColumns = "contactId", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = ContactGroup.class, parentColumns = "id", childColumns = "groupId", onDelete = ForeignKey.CASCADE)},
        primaryKeys = {"contactId","groupId"})
public class GroupMembership {

    @ColumnInfo @NonNull
    private Integer contactId;

    @ColumnInfo @NonNull
    private Integer groupId;

    public GroupMembership(Integer contactId, Integer groupId){
        this.contactId = contactId;
        this.groupId = groupId;
    }

    @NonNull
    public Integer getContactId() {
        return contactId;
    }

    @NonNull
    public Integer getGroupId() {
        return groupId;
    }
}
