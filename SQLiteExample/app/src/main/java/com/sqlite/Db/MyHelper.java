package com.sqlite.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sqlite.GlobalData.GlobalItem;


public class MyHelper extends SQLiteOpenHelper {

    public static final String CREATE_TABLE = "create table " + GlobalItem.TABLE_NAME + "(" + GlobalItem.COL_ID + " integer primary key autoincrement, " + GlobalItem.COL_NAME + " text not NULL" + ")";

    public MyHelper(Context context) {
        super(context, GlobalItem.DATABASE_NAME, null, GlobalItem.DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL(" drop table if exists " + GlobalItem.TABLE_NAME + " ");
        onCreate(db);

    }

}
