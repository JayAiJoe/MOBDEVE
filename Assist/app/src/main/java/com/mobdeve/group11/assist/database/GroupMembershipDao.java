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

    @Query("SELECT contactId FROM group_memberships_table WHERE groupId=:id")
    public LiveData<List<Integer>> findMembershipsByGroupId(Integer id);

    @Query("SELECT groupId FROM group_memberships_table WHERE contactId=:id")
    public LiveData<List<Integer>> findMembershipsByContactId(Integer id);
}
