package com.example.snacktracker.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.snacktracker.Snack;
import com.example.snacktracker.database.SnackDbSchema.SnackTable;

import java.util.Date;
import java.util.UUID;

public class SnackCursorWrapper extends CursorWrapper {
    public SnackCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Snack getSnack(){
        String uuidString = getString(getColumnIndex(SnackTable.Cols.UUID));
        String name = getString(getColumnIndex(SnackTable.Cols.NAME));
        long date = getLong(getColumnIndex(SnackTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(SnackTable.Cols.FOUND));

        Snack snack = new Snack(UUID.fromString(uuidString));
        snack.setmTitle(name);
        snack.setmDate(new Date(date));
        snack.setmFound(isSolved != 0);


        return snack;
    }
}
