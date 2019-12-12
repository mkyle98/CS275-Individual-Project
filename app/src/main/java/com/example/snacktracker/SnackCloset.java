package com.example.snacktracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.snacktracker.database.SnackBaseHelper;
import com.example.snacktracker.database.SnackCursorWrapper;
import com.example.snacktracker.database.SnackDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.snacktracker.database.SnackDbSchema.*;

public class SnackCloset {
    private static SnackCloset sSnackCloset;

    private Context mContext;
    private SQLiteDatabase mDatabase;



    public static SnackCloset get(Context context){
        if (sSnackCloset == null){
            sSnackCloset = new SnackCloset(context);
        }
        return sSnackCloset;
    }

    private SnackCloset(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new SnackBaseHelper(mContext)
                .getWritableDatabase();
    }

    public int deleteSnack(Snack s){
        String uuidString = s.getmId().toString();
        return mDatabase.delete(
                SnackTable.NAME,
                SnackTable.Cols.UUID + " = ?",
                new String[] { uuidString }
        );
    }

    public void addSnack(Snack s) {
        ContentValues values = getContentValues(s);

        mDatabase.insert(SnackTable.NAME, null, values);
    }

    public List<Snack> getSnacks(){
        List<Snack> snacks = new ArrayList<>();

        SnackCursorWrapper cursor = querySnacks(null, null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                snacks.add(cursor.getSnack());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return snacks;
    }

    public Snack getSnack(UUID id){
        SnackCursorWrapper cursor = querySnacks(
                SnackTable.Cols.UUID + "= ?",
                new String[] { id.toString() }
        );

        try{
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getSnack();
        } finally {
            cursor.close();
        }
    }

    public void updateSnack(Snack snack){
        String uuidString = snack.getmId().toString();
        ContentValues values = getContentValues(snack);

        mDatabase.update(SnackTable.NAME, values,
                SnackTable.Cols.UUID + "= ?",
                new String[] { uuidString });
    }

    private SnackCursorWrapper querySnacks(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                SnackTable.NAME,
                null, //
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new SnackCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Snack snack){
        ContentValues values = new ContentValues();
        values.put(SnackTable.Cols.UUID, snack.getmId().toString());
        values.put(SnackTable.Cols.NAME, snack.getmTitle());
        values.put(SnackTable.Cols.DATE, snack.getmDate().getTime());
        values.put(SnackTable.Cols.FOUND, snack.ismFound() ? 1 : 0);

        return values;
    }

}
