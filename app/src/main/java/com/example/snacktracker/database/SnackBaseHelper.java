package com.example.snacktracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.snacktracker.database.SnackDbSchema.SnackTable;

public class SnackBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "snackBase.db";

    public SnackBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + SnackTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                SnackTable.Cols.UUID + ", " +
                SnackTable.Cols.NAME + ", " +
                SnackTable.Cols.DATE + ", " +
                SnackTable.Cols.FOUND +
                ")"
        );

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
