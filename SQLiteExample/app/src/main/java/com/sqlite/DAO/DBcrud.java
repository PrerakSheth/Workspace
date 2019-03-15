package com.sqlite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sqlite.Db.MyHelper;
import com.sqlite.GlobalData.GlobalItem;

import java.util.ArrayList;


public class DBcrud implements com.android.sqlitewithmvc.DAO.DBcrudInterface {
    Context context;
    MyHelper sqlitehelper;
    SQLiteDatabase database;

    public DBcrud(Context c) {
        // TODO Auto-generated constructor stub
        context = c;
        sqlitehelper = new MyHelper(context);

    }

    // other methods to access database
    public long insertstudent(String student) {
        database = sqlitehelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(GlobalItem.COL_NAME, student);
        long in = database.insert(GlobalItem.TABLE_NAME, null, value);
        database.close();
        return in;
    }

    public ArrayList<String> getallname() {
        database = sqlitehelper.getReadableDatabase();
        ArrayList<String> allname = new ArrayList<String>();
        Cursor cursor = database.query(GlobalItem.TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        do {
            allname.add(cursor.getString(1));
        } while (cursor.moveToNext());
        cursor.close();
        return allname;
    }

    public void deleteByID(int id) {
        database = sqlitehelper.getWritableDatabase();
        database.delete(GlobalItem.TABLE_NAME, GlobalItem.COL_ID + "=" + id, null);
        database.close();
    }

}
