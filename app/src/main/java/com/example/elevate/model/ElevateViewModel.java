package com.example.elevate.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ElevateViewModel  extends AndroidViewModel {

    private ElevateRepository mRepository;

    private final LiveData<List<UserAccount>> mAllNames;
    private final LiveData<List<Workout>> mAllWorkouts;

    //constructor
    public ElevateViewModel(@NonNull Application application) {
        super(application);
        mRepository=new ElevateRepository(application);
        mAllNames=mRepository.getAllNames();
        mAllWorkouts=mRepository.getAllWorkouts();
    }

    //User account db methods
    public void insert(UserAccount userAccount){
        mRepository.insert(userAccount);
    }

    public void delete(UserAccount userAccount){
        mRepository.delete(userAccount);
    }

    public void update(UserAccount userAccount){
        mRepository.update(userAccount);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    LiveData<List<UserAccount>> getAllNames(){
        return mAllNames;
    }


    //workout db methods
    public void insert(Workout workout){
        mRepository.insert(workout);
    }

    public void delete(Workout workout){
        mRepository.delete(workout);
    }

    public void update(Workout workout){
        mRepository.update(workout);
    }

    public void deleteAllWorkouts(){
        mRepository.deleteAllWorkouts();
    }

    LiveData<List<Workout>> getAllWorkouts(){
        return mAllWorkouts;
    }

}
