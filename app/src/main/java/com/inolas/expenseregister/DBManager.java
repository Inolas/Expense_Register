package com.inolas.expenseregister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by inolas on 7/11/17.
 */

public class DBManager {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, int amt, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.DATE, name);
        contentValue.put(DatabaseHelper.AMT, amt);
        contentValue.put(DatabaseHelper.DESC, desc);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.DATE, DatabaseHelper.AMT, DatabaseHelper.DESC };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        /*cursor = database.query(DatabaseHelper.TABLE_NAME,columns, DatabaseHelper.DATE +" = %11_____;", null, null, null, null, null);*/
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, int amt, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.DATE, name);
        contentValues.put(DatabaseHelper.AMT, amt);
        contentValues.put(DatabaseHelper.DESC, desc);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

    public int amtsum(int i) {
        i = 11;
        String query = "SELECT SUM("+ DatabaseHelper.AMT +") AS Total FROM "+ DatabaseHelper.TABLE_NAME + " WHERE strftime('%m',"+DatabaseHelper.DATE+") = 11;";
        //*strftime('%Y',"+ DatabaseHelper.DATE +") = strftime('%Y',date('now')) AND*/ strftime('%m',entry_date) = strftime('%m',date('now'))";
        Log.w(TAG, query+"Query"+ DatabaseHelper.DATE);

        Cursor cursor = database.rawQuery("SELECT SUM("+ DatabaseHelper.AMT +") AS Total FROM "+ DatabaseHelper.TABLE_NAME, null);

        int total=0;
        if (cursor.moveToFirst())
            total = cursor.getInt(cursor.getColumnIndex("Total"));// get final total
        return total;

    }
}