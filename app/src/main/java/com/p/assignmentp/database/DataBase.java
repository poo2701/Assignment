package com.p.assignmentp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = { LoginData.class,ProductData.class } , version = 1,exportSchema = false)
public abstract class DataBase extends RoomDatabase {

    public static volatile DataBase INSTANCE;


    public abstract LoginDao getLoginDao();
    public abstract ProductDao getProductDao();

    public static final int NUMBER_OF_THREADS = 1;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static DataBase getDatabaseInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (DataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DataBase.class, "db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
