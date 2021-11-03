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

    private boolean completed;

    //Constructor
    public Workout(String name, String style, int grade, String tutorial) { //constructor
        this.name = name;
        this.style = style;
        this.grade = grade;
        this.tutorial = tutorial;
        this.completed = false;
    }

    //setters
    public void setWorkoutId(int mWorkoutId) { //setter for id since it is not in constructor
        this.mWorkoutId = mWorkoutId;
    }

    public void setName(String name) { this.name = name; }

    public void setStyle(String style) { this.style = style; }

    public void setGrade(int grade) { this.grade = grade; }

    public void setTutorial(String tutorial) { this.tutorial = tutorial; }

    public void setCompleted(boolean completed) { this.completed = completed; }

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

    public boolean getCompleted() { return completed; }

/*public Workout(@NonNull int workId) {this.mWorkoutId = workId;} //setter?

    public int getWorkoutId(){return this.mWorkoutId;}*/
}
