package com.example.elevate.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
                                    //Added workout entity below
@Database(entities = {UserAccount.class, Workout.class}, version = 1, exportSchema = false)
public abstract class ElevateRoomDatabase extends RoomDatabase {

    public abstract UserAccountDao userAccountDao();
    public abstract WorkoutDao workoutDao();

    private static volatile ElevateRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ElevateRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ElevateRoomDatabase.class) { //only one thread at a time can access database?
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ElevateRoomDatabase.class, "elevate_database")
                            .fallbackToDestructiveMigration()
                            .fallbackToDestructiveMigrationFrom(1)
                            .build();
                }
            }
        }
        return INSTANCE;
        //instantiates if null, if not, it returns the already existing instance
    }
}