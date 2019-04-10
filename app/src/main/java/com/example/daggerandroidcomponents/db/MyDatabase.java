package com.example.daggerandroidcomponents.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Data.class}, version = 3, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    private static volatile MyDatabase INSTANCE;
    public abstract UserDao userDao();
}
