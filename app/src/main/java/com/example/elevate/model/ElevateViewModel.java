package com.example.elevate.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Objects;

public class ElevateViewModel  extends AndroidViewModel {

    private ElevateRepository mRepository;
    private final LiveData<List<UserAccount>> mAllNames;
    private final LiveData<List<Workout>> mAllWorkouts;
    private UserAccount mCurrentUser;

    //constructor
    public ElevateViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ElevateRepository(application);
        mAllNames = mRepository.getAllNames();
        mAllWorkouts = mRepository.getAllWorkouts();
        mCurrentUser = null;
    }

    public boolean containsUserAccount(UserAccount userAccount) {
        boolean accountInList = false;

        UserAccount user = mRepository.findUserAccountByName(userAccount);
        if (user != null) {
            if (Objects.requireNonNull(user).getName().equals(userAccount.getName()) &&
                    Objects.requireNonNull(user).getPassword().equals(userAccount.getPassword())) {
                accountInList = true;
                mCurrentUser = user;
            }
        }

        return accountInList;
    }

    public boolean usernameAlreadyExists(String username) {
        boolean exists = false;
        UserAccount user = mRepository.findUserAccountByUsername(username);
        if (user != null) {
            exists = true;
        }
        return exists;
    }

    public UserAccount getCurrentUser() {
        return mCurrentUser;
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

    public LiveData<List<UserAccount>> getAllNames(){
        return mAllNames;
    }


    public Workout getWorkout(int id) { return mRepository.findWorkoutById(id); }

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

    public LiveData<List<Workout>> getAllWorkouts(){
        return mAllWorkouts;
    }

}
