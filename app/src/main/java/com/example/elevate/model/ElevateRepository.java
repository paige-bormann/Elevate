package com.example.elevate.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ElevateRepository {
    private UserAccountDao mUserAccountDao;
    private LiveData<List<UserAccount>> mAllNames;

    private WorkoutDao mWorkoutDao;
    private LiveData<List<Workout>> mAllWorkouts;

    public ElevateRepository(Application application) {
        ElevateRoomDatabase db=ElevateRoomDatabase.getDatabase(application);
        mUserAccountDao=db.userAccountDao();
        mAllNames=mUserAccountDao.getAllNames();

        mWorkoutDao=db.workoutDao();
        mAllWorkouts= mWorkoutDao.getAllWorkouts();
    }

    UserAccount findUserAccountByName(UserAccount userAccount) {
        return mUserAccountDao.findByName(userAccount.getName(), userAccount.getPassword());
    }

    UserAccount findUserAccountByUsername(String username) {
        return mUserAccountDao.findByUsername(username);
    }

    //UserAccount database methods
    public void insert(UserAccount userAccount){
        ElevateRoomDatabase.databaseWriteExecutor.execute(() ->{
            mUserAccountDao.insert(userAccount);
        });
    }

    public void update(UserAccount userAccount){
        ElevateRoomDatabase.databaseWriteExecutor.execute(() ->{
            mUserAccountDao.update(userAccount);
        });
    }

    public void delete(UserAccount userAccount){
        ElevateRoomDatabase.databaseWriteExecutor.execute(() ->{
            mUserAccountDao.delete(userAccount);
        });
    }

    public void deleteAll(){
        ElevateRoomDatabase.databaseWriteExecutor.execute(() ->{
            mUserAccountDao.deleteAll();
        });
    }

    //returns LiveData
    LiveData<List<UserAccount>> getAllNames(){
        return mAllNames;
    }


    //Workout database method bodies
    public void insert(Workout workout){
        ElevateRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWorkoutDao.insert(workout);
        });
    }

    public void update(Workout workout){
        ElevateRoomDatabase.databaseWriteExecutor.execute(() ->{
            mWorkoutDao.update(workout);
        });
    }

    public void delete(Workout workout){
        ElevateRoomDatabase.databaseWriteExecutor.execute(() ->{
            mWorkoutDao.delete(workout);
        });
    }

    public void deleteAllWorkouts(){
        ElevateRoomDatabase.databaseWriteExecutor.execute(() ->{
            mWorkoutDao.deleteAllWorkouts();
        });
    }

    //returns LiveData
    LiveData<List<Workout>> getAllWorkouts(){
        return mAllWorkouts;
    }

    Workout findWorkoutById(int id) {
        return mWorkoutDao.findById(id);
    }
}
