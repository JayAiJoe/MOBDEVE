package com.mobdeve.group11.assist.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Contact.class, ContactGroup.class, Event.class, EventGrouping.class, GroupMembership.class, Template.class},
        version = 1,
        exportSchema = false)
public abstract class AssistDatabase extends RoomDatabase {

    public abstract ContactDao contactDao();
    public abstract ContactGroupDao contactGroupDao();
    public abstract EventDao eventDao();
    public abstract EventGroupingDao eventGroupingDao();
    public abstract GroupMembershipDao groupMembershipDao();
    public abstract TemplateDao templateDao();

    private static volatile AssistDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AssistDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AssistDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AssistDatabase.class, "assist_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}