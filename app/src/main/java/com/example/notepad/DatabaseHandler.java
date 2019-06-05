package com.example.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "TODOTASK";
    private static String TABLE_NAME = "TOTABLE";
    private static String COL_0 = "id";
    private static String COL_1 = "task";
    private static String COL_2 = "boolean";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = "CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT,task TEXT,boolean INTEGER)";
        db.execSQL(table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean adddata(String task, boolean b) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, task);
        contentValues.put(COL_2, b);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;


    }

    public Cursor getalldata() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    public boolean update(Todo todo) {

        SQLiteDatabase db = this.getWritableDatabase();
        int i=todo.getCheck()?1:0;


        String updateQuery = "UPDATE TOTABLE SET boolean ="+i+"  WHERE task=?";
        db.execSQL(updateQuery,new String[]{todo.getTask()});
        return todo.getCheck();
           }
}