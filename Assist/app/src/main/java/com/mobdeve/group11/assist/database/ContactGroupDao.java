package com.mobdeve.group11.assist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactGroupDao {

    @Insert
    public void insertContactGroup(ContactGroup contactGroup);

    @Update
    public void updateContactGroup(ContactGroup contactGroup);

    @Delete
    public void deleteContactGroup(ContactGroup contactGroup);

    @Query("SELECT * FROM contact_groups_table")
    public LiveData<List<ContactGroup>> loadAllContactGroups();

    @Query("SELECT * FROM contact_groups_table WHERE id =:id")
    public LiveData<ContactGroup> findContactGroupById(Integer id);

    @Query("SELECT * FROM contact_groups_table WHERE name=:name ")
    public LiveData<ContactGroup> findContactByName(String name);

    @Query("DELETE FROM contact_groups_table")
    public void deleteAllContactGroups();

    @Query("SELECT * FROM contact_groups_table WHERE id IN (:ids)")
    public LiveData<List<ContactGroup>> findManyContactGroupsById(List<Integer> ids);
}
