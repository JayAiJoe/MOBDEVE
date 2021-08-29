package com.mobdeve.group11.assist.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Contact.class, ContactGroup.class, Event.class, EventGrouping.class, GroupMembership.class, Template.class},
        version = 2,
        exportSchema = false)
@TypeConverters({Converters.class})
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
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                ContactGroupDao dao = INSTANCE.contactGroupDao();
                dao.deleteAllContactGroups();

                dao.insertContactGroup(new ContactGroup("Grade 3 - Pink"));
                dao.insertContactGroup(new ContactGroup("Grade 4 - Violet"));
                dao.insertContactGroup(new ContactGroup("Grade 5 - Purple"));
                dao.insertContactGroup(new ContactGroup("Grade 6 - Fuschia"));
            });
        }
    };

}