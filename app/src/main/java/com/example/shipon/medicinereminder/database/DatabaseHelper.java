package com.example.shipon.medicinereminder.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Medicine_reminder";

    public static final int DATABASE_VERSION = 2;
    //tabile1
    public static final String TABLE_MEDICINE_DETAILS = "table_medicine_details";

    public static final String MEDICINE_NAME = "medicine_name";
    public static final String MEDICINE_DURATION = "medicine_duration";
    public static final String MEDICINE_TYPE = "medicine_type";
    public static final String MEDICINE_PER_DAY = "medicine_per_day";
    public static final String CREATE_TABLE_MEDICINE_DETAILS_QUERY = "create table if not exists " + TABLE_MEDICINE_DETAILS
            + "(" + MEDICINE_NAME + " text, " + MEDICINE_DURATION + " integer, " + MEDICINE_TYPE + " text, " + MEDICINE_PER_DAY + " integer);";
    //tabile2
    public static final String TABLE_MEDICINE_DATE_TIME = "table_medicine_date_time";
    public static final String MEDICINE_NAME_TABLE2 = "medicine_name2";
    public static final String MEDICINE_DATE = "medicine_date";
    public static final String MEDICINE_TIME = "medicine_time";
    public static final String MEDICINE_TAKEN_YES_OR_NO = "medicine_taken";
    public static final String CREATE_TABLE_MEDICINE_DATE_TIME_QUERY = "create table if not exists " + TABLE_MEDICINE_DATE_TIME
            + "(" + MEDICINE_NAME_TABLE2 + " text, "
            + MEDICINE_DATE + " text, " + MEDICINE_TIME + " text, " + MEDICINE_TAKEN_YES_OR_NO + " integer);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MEDICINE_DETAILS_QUERY);
        db.execSQL(CREATE_TABLE_MEDICINE_DATE_TIME_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if EXISTS " + TABLE_MEDICINE_DETAILS);
        db.execSQL("drop table if EXISTS " + TABLE_MEDICINE_DATE_TIME);
        onCreate(db);
    }
}
