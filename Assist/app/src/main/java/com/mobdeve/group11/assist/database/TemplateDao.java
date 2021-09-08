package com.mobdeve.group11.assist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TemplateDao {

    @Insert
    public void insertTemplate(Template template);

    @Update
    public void updateTemplate(Template template);

    @Delete
    public void deleteTemplate(Template template);

    @Query("SELECT * FROM templates_table")
    public LiveData<List<Template>> loadAllTemplates();

    @Query("SELECT * FROM templates_table WHERE id=:id")
    public LiveData<Template> findTemplateById(Integer id);

}
