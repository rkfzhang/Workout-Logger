package com.zkfxd.workoutapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 11/25/2017.
 */

public class DBadapter {
    public static final String DATABASE_NAME = "WorkoutDB";
    public static final int DATABASE_VERSION = 5;

    ////DB fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;

    //tables
    public static final String UPPERTABLE = "Upper";
    public static final String ARMSTABLE = "Arms";
    public static final String LOWERTABLE = "Lower";

    //fields
    public static final String KEY_NAME = "name";
    public static final String KEY_REPS = "sets";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_BODY = "body";

    //field numbers
    public static final int COL_NAME = 1;
    public static final int COL_REPS = 2;
    public static final int COL_WEIGHT = 3;
    public static final int COL_BODY = 4;

    public static final String[] ALL_KEYS = new String[]{KEY_ROWID, KEY_NAME, KEY_REPS, KEY_WEIGHT, KEY_BODY};



    //Tables
    private static String DATABASE_CREATE_SQL_CHEST = "create table " + UPPERTABLE + " ("
            + KEY_ROWID + " integer primary key autoincrement, "

            + KEY_NAME + " text, " + KEY_REPS + " text, " + KEY_WEIGHT + " text, " + KEY_BODY + " text"

            + ");";

    private static String DATABASE_CREATE_SQL_BACK = "create table " + ARMSTABLE + " ("
            + KEY_ROWID + " integer primary key autoincrement, "

            + KEY_NAME + " text, " + KEY_REPS + " text, " + KEY_WEIGHT + " text, " + KEY_BODY + " text"

            + ");";

    private static String DATABASE_CREATE_SQL_LEGS = "create table " +LOWERTABLE + " ("
            + KEY_ROWID + " integer primary key autoincrement, "

            + KEY_NAME + " text, " + KEY_REPS + " text, " + KEY_WEIGHT + " text, " + KEY_BODY + " text"

            + ");";

    //Context of applications who uses us
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    //Public methods

    public DBadapter(Context ctx){

        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    public DBadapter open(){

        db = myDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){

        myDBHelper.close();
    }

    public long insertRow(String name, String reps, String weight, String body, String tableName){

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME,name);
        initialValues.put(KEY_REPS,reps);
        initialValues.put(KEY_WEIGHT, weight);
        initialValues.put(KEY_BODY, body);

        return db.insert(tableName, null, initialValues);

    }

    public boolean deleteRow(long rowID, String tableName){

        String where = KEY_ROWID + "=" + rowID;
        return  db.delete(tableName, where, null) > 0;

    }

    public Cursor getAllRows(String tableName){

        String where = null;
        Cursor c = db.query(true, tableName, ALL_KEYS, where,
                null, null, null, null, null);

        if(c != null){
            c.moveToFirst();
        }

        return c;
    }



    public Cursor getRow(long rowId, String tableName){

        String where = KEY_ROWID + "=" + rowId;
        Cursor c = db.query(true, tableName, ALL_KEYS, where,
                null, null, null, null, null);

        if(c != null){
            c.moveToFirst();
        }

        return c;
    }

    public boolean updateRow(long rowId, String name, String reps, String weight, String body,  String tableName){

        String where = KEY_ROWID + "=" + rowId;


        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME,name);
        newValues.put(KEY_REPS,reps);
        newValues.put(KEY_WEIGHT, weight);
        newValues.put(KEY_BODY, body);

        return db.update(tableName, newValues, where, null) > 0;
    }


    //Private Helper Classes

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {


            _db.execSQL(DATABASE_CREATE_SQL_CHEST);
            _db.execSQL(DATABASE_CREATE_SQL_BACK);
            _db.execSQL(DATABASE_CREATE_SQL_LEGS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {

            _db.execSQL("DROP TABLE IF EXISTS " + UPPERTABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + ARMSTABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + LOWERTABLE);

            onCreate(_db);
        }


    }
}

