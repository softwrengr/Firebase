package com.techease.appointment.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by eapple on 24/12/2018.
 */

public class AppointCrud {

    private static SQLiteDatabase sqLiteDatabase;
    private Context context;

    public AppointCrud(Context context) {
        AppointDatabase insert = new AppointDatabase(context);
        sqLiteDatabase = insert.getWritableDatabase();
        this.context = context;
    }

    public void insertData(String date) {

        if (!checkItemChart(date)) {
            ContentValues values = new ContentValues();
            values.put("DATE", date);
            sqLiteDatabase.insert("DATE_TABLE", null, values);
            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(context, "already exist", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkItemChart(String date) {

        Cursor cursor = this.sqLiteDatabase.rawQuery("SELECT * FROM DATE_TABLE WHERE DATE = '" + date + "' ", null);

        boolean isItemAddChart = false;
        if (cursor.moveToFirst()) {
            isItemAddChart = true;
        }
        else {
            isItemAddChart = false;
        }
        return isItemAddChart;

    }

    //fetching all certificates
    public Cursor getData() {
        String query = "SELECT * FROM DATE_TABLE";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor;
    }

}
