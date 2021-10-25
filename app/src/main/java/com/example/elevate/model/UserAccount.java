package com.example.elevate.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Fts4
@Entity(tableName = "useraccount")
public class UserAccount {
    @PrimaryKey(autoGenerate = true) //room will autogenerate these numbers
    @ColumnInfo(name = "rowid")
    public int mUid;

    @NonNull
    @ColumnInfo(name = "name")
    public String mName;

    @NonNull
    @ColumnInfo(name = "password")
    public String mPassword;

    //@nonnull?
    @ColumnInfo(name="currentLevel")
    public int mCLevel;

    //@nonnull?
    @ColumnInfo(name="goalLevel")
    public int mGLevel;

    //Constructor
    public UserAccount(@NonNull String name, @NonNull String password) {
        mName = name;
        mPassword = password;
        mGLevel = 0;
        mCLevel = 0;
    }

    //setters (must have a setter for any variable that is not in the constructor)
    public void setCLevel(int mCLevel) {
        this.mCLevel = mCLevel;
    }

    public void setGLevel(int mGLevel) {
        this.mGLevel = mGLevel;
    }

    //getters
    public int getUid() { return this.mUid; }

    public String getName() {
        return mName;
    }

    public String getPassword() {
        return mPassword;
    }

    public int getCurrentLevel(){
        return mCLevel;
    }

    public int getGoalLevel(){
        return mGLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return mName.equals(that.mName) && mPassword.equals(that.mPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mUid, mName, mPassword, mCLevel, mGLevel);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserAccount{" +
                "uid=" + mUid +
                "; name='" + mName + '\'' +
                "; password='" + mPassword + '\'' +
                "; currentLevel='" + mCLevel + '\'' +
                "; goalLevel='" + mGLevel + '\'' +
                '}';
    }
}

