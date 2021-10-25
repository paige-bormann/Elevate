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
        LiveData<List<UserAccount>> userAccountListData = this.getAllNames();
        List<UserAccount> userAccountList = userAccountListData.getValue();

        if (Objects.requireNonNull(userAccountList).contains(userAccount)) {
            accountInList = true;
            mCurrentUser = userAccount;
        }

        return accountInList;
    }

/*    public boolean containsUserAccount(UserAccount userAccount) {
        boolean accountInList = false;

        LiveData<UserAccount> userAccountLiveData = mRepository.findUserAccountByName(userAccount);
        UserAccount theUserAccount = userAccountLiveData.getValue();
        if (Objects.requireNonNull(theUserAccount).getName().equals(userAccount.getName()) &&
                Objects.requireNonNull(theUserAccount).getPassword().equals(userAccount.getPassword())) {
            accountInList = true;
            mCurrentUser = theUserAccount;
        }

        return accountInList;
    }*/

    public UserAccount getCurrentUser() {
        return mCurrentUser;
    }

    public LiveData<UserAccount> getUserAccount(UserAccount userAccount) {
        return mRepository.findUserAccountByName(userAccount);
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
