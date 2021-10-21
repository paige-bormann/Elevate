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
    void insert(UserAccount userAccount);

    @Update
    void update(UserAccount userAccount);

    @Delete
    void delete(UserAccount userAccount);

    //TODO add R of "CRUD"

    @Query("DELETE FROM useraccount") //useraccount is table name, delete all rows I think
    void deleteAll();

    @Query("SELECT * FROM useraccount ORDER BY rowid ASC")
        //wrap in liveData?
    LiveData<List<UserAccount>> getAllNames();


}

