package com.example.elevate.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Fts4
@Entity(tableName = "workout_table")
public class Workout {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    private int mWorkoutId;

    private String name;

    private String style;

    private int grade; //int with value 0-17, corresponding to a V-grade

    private String tutorial;

    //Constructor
    public Workout(String name, String style, int grade, String tutorial) { //constructor
        this.name = name;
        this.style = style;
        this.grade = grade;
        this.tutorial = tutorial;
    }

    public void setWorkoutId(int mWorkoutId) { //setter for id since it is not in constructor
        this.mWorkoutId = mWorkoutId;
    }

    //getters
    public int getWorkoutId() {
        return mWorkoutId;
    }

    public String getName() {
        return name;
    }

    public String getStyle() {
        return style;
    }

    public int getGrade() {
        return grade;
    }

    public String getTutorial() {
        return tutorial;
    }

/*public Workout(@NonNull int workId) {this.mWorkoutId = workId;} //setter?

    public int getWorkoutId(){return this.mWorkoutId;}*/
}
