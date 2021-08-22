package com.mobdeve.group11.assist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GroupMembershipDao {
    
    @Insert
    public void insertGroupMembership(GroupMembership groupMembership);

    @Delete
    public void deleteGroupMemberShip(GroupMembership groupMembership);

    @Query("SELECT * FROM group_memberships_table WHERE groupId=:id")
    public LiveData<List<GroupMembership>> findMembershipsByGroupId(Integer id);

    @Query("SELECT * FROM group_memberships_table WHERE contactId=:id")
    public LiveData<List<GroupMembership>> findMembershipsByContactId(Integer id);
}
