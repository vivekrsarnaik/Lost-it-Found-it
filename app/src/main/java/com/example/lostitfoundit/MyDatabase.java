package com.example.lostitfoundit;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Post.class}, version = 5)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase myDatabase;

    public abstract AllDao getAllDao();


    public static MyDatabase getMyDatabase(Context context) {
        if (myDatabase == null) {
            myDatabase = Room.databaseBuilder(context,
                            MyDatabase.class, "AndroidDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return myDatabase;
    }

}
