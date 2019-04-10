package com.example.daggerandroidcomponents.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Insert(onConflict = REPLACE)
    void save(Data data);


    @Query("SELECT * from data")
    LiveData<List<Data>> loadAllUser();

    @Query("SELECT * from data Where id = :id")
    LiveData<Data> load(int id);

    @Query("Select * from data Where id=:id LIMIT 1")
    Data hasTicker(int id);
}
