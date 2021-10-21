package com.example.elevate.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao //create one dao per entity
public interface WorkoutDao { //create methods and annotate, room will automatically generate code for us

    @Insert
    void insert(Workout workout);

    @Update
    void update(Workout workout);

    @Delete
    void delete(Workout workout);

    //TODO add R of "CRUD"

    @Query("DELETE FROM workout_table") //delete all workouts
    void deleteAllWorkouts();

    // * means all columns
    @Query("SELECT * FROM workout_table ORDER BY rowid DESC") //get all of the workouts?
    LiveData<List<Workout>> getAllWorkouts(); //liveData wrapper

}
