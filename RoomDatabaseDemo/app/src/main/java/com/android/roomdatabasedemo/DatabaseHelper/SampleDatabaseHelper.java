package com.android.roomdatabasedemo.DatabaseHelper;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.android.roomdatabasedemo.Dao.DBOperationPerformDAO;
import com.android.roomdatabasedemo.Entity.University;


@Database(entities = {University.class}, version = 1)
public abstract class SampleDatabaseHelper extends RoomDatabase {
    public abstract DBOperationPerformDAO dbOperationPerformDAO();
}


//@Database(entities = {User.class}, version = 1)
//public abstract class AppDatabase extends RoomDatabase {
//
//    private static AppDatabase INSTANCE;
//
//    public abstract UserDao userDao();
//
//    public static AppDatabase getAppDatabase(Context context) {
//        if (INSTANCE == null) {
//            INSTANCE =
//                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
//                            // allow queries on the main thread.
//                            // Don't do this on a real app! See PersistenceBasicSample for an example.
//                            .allowMainThreadQueries()
//                            .build();
//        }
//        return INSTANCE;
//    }
//
//    public static void destroyInstance() {
//        INSTANCE = null;
//    }
//}
