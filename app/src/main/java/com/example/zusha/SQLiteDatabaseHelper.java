package com.example.zusha;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
    public  static final String DATABASE_NAME = "zusha";
    public static  final String TABLE_NAME = "myreports";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "REGNO";
    public static final String COL_3 = "SACCO";
    public static final String COL_4 = "TIME";
    public static final String COL_5 = "LOCATION";
    public static final String COL_6 = "SPEED";
    public static final String COL_7 = "DRIVER";

    public SQLiteDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, REGNO TEXT," +
                "SACCO TEXT, TIME TEXT, LOCATION TEXT, SPEED TEXT, DRIVER TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String REGNO, String SACCO, String TIME, String LOCATION, double SPEED, String DRIVER){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, REGNO);
        contentValues.put(COL_3, SACCO);
        contentValues.put(COL_4, TIME);
        contentValues.put(COL_5, LOCATION);
        contentValues.put(COL_6, SPEED);
        contentValues.put(COL_7, DRIVER);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
//        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        Cursor res = db.rawQuery("select * from myreports", null);
        return res;
    }

    public Cursor getReport(String caseId){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " Where " + COL_1 + " = '" + caseId + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
