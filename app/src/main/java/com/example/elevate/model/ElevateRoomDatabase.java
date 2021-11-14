package com.example.elevate.model;

import android.content.Context;

import androidx.annotation.NonNull;
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
                            .allowMainThreadQueries()
                            .addCallback(prepopulate)
                            .build();
                }
            }
        }
        return INSTANCE;
        //instantiates if null, if not, it returns the already existing instance
    }

    private static RoomDatabase.Callback prepopulate = new RoomDatabase.Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                // Add pre-made workouts here to populate the database

                //Grade V0-V2 workouts
                Workout workout = new Workout("Beginner Footwork Technique", "Technique",
                        "Technique focused bouldering session: " +
                                "complete 12 boulders in the V0-V2 range while " +
                                "focusing on the footwork techniques in the tutorial video", 1,
                        "https://www.youtube.com/watch?v=KoTG-0_smTE&ab_channel=LatticeTraining");
                INSTANCE.workoutDao().insert(workout);
                workout = new Workout("Beginner Board Climbing", "Power",
                        "Find six boulders V0-V2 on an overhung board, give yourself " +
                                "6 minutes to attempt each boulder, with a max of 3 attempts " +
                                "per 6 minutes, and then rest 6 minutes in between boulders", 1,
                        "https://www.youtube.com/watch?v=LjQephtLTGQ&t=333s&ab_channel=LatticeTraining");
                INSTANCE.workoutDao().insert(workout);

                //Grade v3-v5 workouts
                workout = new Workout("Intermediate Footwork Technique", "Technique",
                        "Complete 12 boulders in the V3-V5 range while focusing on the " +
                                "techniques in the tutorial video", 3,
                        "https://www.youtube.com/watch?v=yjFjJZYnqcE&ab_channel=LatticeTraining");
                INSTANCE.workoutDao().insert(workout);

                workout= new Workout("Intermediate Board Climbing", "Power",
                        "Find six boulders V3-V5 on an overhung board, give yourself" +
                                "6 minutes to attempt each boulder, with a max of 3 attempts " +
                                "per 6 minutes, and then rest 6 minutes in between boulders", 3,
                        "https://www.youtube.com/watch?v=LjQephtLTGQ&t=4s&ab_channel=LatticeTraining");
                INSTANCE.workoutDao().insert(workout);

                //Grade v6-v9 workouts
                workout = new Workout("Campus Boarding", "Power",
                        "3 sets of campus max range repeaters, 3 sets of " +
                                "max range bumpers, and 3 sets of" +
                                " lockoffs, with 3 minutes between each set.", 5,
                        "https://www.youtube.com/watch?v=Gde5EvNnR7k&t=327s&ab_channel=ManitheMonkey");
                INSTANCE.workoutDao().insert(workout);

                workout= new Workout("Deadpoint Practice", "Technique", "Find or" +
                        "make up 6 boulder problems that require a deadpoint, practice that move in isolation" +
                        "until you have done it successfully in each, and then give each boulder 2 send attempts",
                        6,
                        "https://www.youtube.com/watch?v=g4E748X07dQ&ab_channel=NeilGreshamClimbingMasterclass-CruxFilms");
                INSTANCE.workoutDao().insert(workout);

                //Grade v10-v13 workouts
                workout=new Workout("Max Hangs", "Power", "6 sets of 10 second " +
                        "max hangs on a 8mm edge, 3 rest 3 minutes between each set", 11,
                        "https://www.youtube.com/watch?v=VeKE5VH5-qg&t=1183s&ab_channel=DaveMacLeod");
                INSTANCE.workoutDao().insert(workout);

                //Grade v15 workout
                workout=new Workout("4x4s", "Power", "Complete 4 sets of 4 reps of a climb with " +
                        "no rest between reps and 4 minute rest between sets",15,
                        "https://www.youtube.com/watch?v=g_9XeEyFrLw&t=99s&ab_channel=FunctionalFitness");
                INSTANCE.workoutDao().insert(workout);
            });
        }
    };
}

