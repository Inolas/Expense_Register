package com.inolas.expenseregister;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by inolas on 7/11/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_NAME = "EXPENSES";

    // Table columns
    public static final String _ID = "_id";
    public static final String DATE = "date";
    public static final String AMT = "amount";
    public static final String DESC = "description";

    // Database Information
    static final String DB_NAME = "EXPENSE_1.DB";

    // database version
    static final int DB_VERSION = 12;

    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE + " DATE NOT NULL, " + DESC + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if(oldVersion < newVersion)
            db.execSQL("ALTER TABLE " + TABLE_NAME +" ADD COLUMN "+ AMT + " INTEGER");
     //   onCreate(db);
    }
}

