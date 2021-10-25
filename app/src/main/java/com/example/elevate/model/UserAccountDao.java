package com.example.elevate.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserAccountDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(UserAccount userAccount);

    @Update
    public void update(UserAccount userAccount);

    @Delete
    public void delete(UserAccount userAccount);

    @Query("DELETE FROM useraccount") //useraccount is table name, delete all rows I think
    public void deleteAll();

    @Query("SELECT rowid, name, password, currentLevel, goalLevel FROM useraccount ORDER BY rowid ASC")
        //wrap in liveData?
    public LiveData<List<UserAccount>> getAllNames();

    @Query("SELECT rowid, name, password, currentLevel, goalLevel FROM useraccount WHERE name LIKE :name AND password LIKE :password LIMIT 1")
    public LiveData<UserAccount> findByName(String name, String password);
}

