package com.example.snacktracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.UUID;

public class SnackDbSchema{

    public static final class SnackTable{
        public static final String NAME = "snacks";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String DATE = "date";
            public static final String FOUND = "found";
        }
    }
}
