package com.techease.appointment.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eapple on 24/12/2018.
 */

public class AppointDatabase extends SQLiteOpenHelper {
    private static String DB_NAME = "APPOINTMENT_DB";
    public static int DB_VERSION = 2;

    public AppointDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE DATE_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE)";
        String insert_date = "CREATE TABLE USER_DATE (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE)";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(insert_date);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DATE_TABLE");
        db.execSQL("DROP TABLE IF EXISTS USER_DATE");
        onCreate(db);
    }
}

