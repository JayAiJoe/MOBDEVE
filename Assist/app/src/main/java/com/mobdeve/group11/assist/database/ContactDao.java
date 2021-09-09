package com.mobdeve.group11.assist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    public long insertContact(Contact contact);

    @Update
    public void updateContact(Contact contact);

    @Delete
    public void deleteContact(Contact contact);

    @Query("SELECT * FROM contacts_table ORDER BY lastName, firstName")
    public LiveData<List<Contact>> loadAllContacts();

    @Query("SELECT * FROM contacts_table WHERE id =:id")
    public LiveData<Contact> findContactById(Integer id);

    @Query("SELECT * FROM contacts_table WHERE firstName=:firstName AND lastName=:lastName")
    public LiveData<Contact> findContactByName(String firstName, String lastName);

    @Query("SELECT * FROM contacts_table WHERE id IN (:ids) ORDER BY lastName, firstName")
    public LiveData<List<Contact>> findManyContactsById(List<Integer> ids);

}
