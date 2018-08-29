package com.example.shipon.medicinereminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.shipon.medicinereminder.Class.Medicine;
import com.example.shipon.medicinereminder.Class.MedicinePerRow;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Shipon on 8/22/2018.
 */

public class MedicineManagementDatabase {
    DatabaseHelper databaseHelper;
    long insertedRow1;
    long insertedRow2;
    Context context;
    ArrayList<MedicinePerRow> MedicineRow = new ArrayList<>();
    ArrayList<String> MedicineName = new ArrayList<>();

    public MedicineManagementDatabase(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public long addMedicineDetails(Medicine medicine) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MEDICINE_NAME, medicine.getMedicineName());
        contentValues.put(DatabaseHelper.MEDICINE_DURATION, medicine.getMedicineDuration());
        contentValues.put(DatabaseHelper.MEDICINE_TYPE, medicine.getMedicineType());
        contentValues.put(DatabaseHelper.MEDICINE_PER_DAY, medicine.getMedicinePerday());
        insertedRow1 = sqLiteDatabase.insert(DatabaseHelper.TABLE_MEDICINE_DETAILS, null, contentValues);

        return 0;
    }

    public int getMonth(String date) {

        int str1 = date.indexOf("/");
        int str2 = date.lastIndexOf("/");
        String st1 = date.substring(0, str1);
        String st2 = date.substring(str1 + 1, str2);
        return Integer.parseInt(st2);

    }

    public int getDay(String date) {

        int str1 = date.indexOf("/");
        int str2 = date.lastIndexOf("/");
        String st1 = date.substring(0, str1);

        int ind = Integer.valueOf(st1);

        return ind;

    }

    public int getYear(String date) {

        int str1 = date.indexOf("/");
        int str2 = date.lastIndexOf("/");
        String st1 = date.substring(0, str1);
        String st2 = date.substring(str1 + 1, str2);
        String st3 = date.substring(str2 + 1, date.length());
        return Integer.parseInt(st3);
    }

    public ArrayList<MedicinePerRow> retriveMedicineByDate(String date, String medicine) {
        MedicineRow.clear();
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        String selectQuery = "Select * from " + DatabaseHelper.TABLE_MEDICINE_DATE_TIME + " where " + databaseHelper.MEDICINE_DATE + " = ? and "
                + databaseHelper.MEDICINE_NAME_TABLE2 + " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, new String[]{date, medicine}, null);
        if (cursor.moveToFirst()) {
            do {

                String mName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICINE_NAME_TABLE2));
                String mDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICINE_DATE));
                String mTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICINE_TIME));
                int takenYesOrNo = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICINE_TAKEN_YES_OR_NO)));
                // int takenYesOrNo = 0 ;
                MedicineRow.add(new MedicinePerRow(mName, mDate, mTime, takenYesOrNo));
            } while (cursor.moveToNext());
        }
        return MedicineRow;
    }

    public ArrayList<String> retriveMedicineNameByDate(String date) {
        MedicineName.clear();
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        String selectQuery = "Select * from " + DatabaseHelper.TABLE_MEDICINE_DATE_TIME + " where " + databaseHelper.MEDICINE_DATE + " = ? ";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, new String[]{date}, null);
        if (cursor.moveToFirst()) {
            do {

                String mName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICINE_NAME_TABLE2));

                //int takenYesOrNo = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICINE_TAKEN_YES_OR_NO)));
                // int takenYesOrNo = 0 ;
                MedicineName.add(new String(mName));
            } while (cursor.moveToNext());
        }
        return MedicineName;
    }

    public ArrayList<MedicinePerRow> retriveAllDateTime() {
        ArrayList<MedicinePerRow> row = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        String selectQuery = "Select * from " + DatabaseHelper.TABLE_MEDICINE_DATE_TIME;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                String mName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICINE_NAME_TABLE2));
                String mDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICINE_DATE));
                String mTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICINE_TIME));

                int takenYesOrNo = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICINE_TAKEN_YES_OR_NO)));
                // int takenYesOrNo = 0 ;
                row.add(new MedicinePerRow(mName, mDate, mTime, takenYesOrNo));
            } while (cursor.moveToNext());
        }
        return row;
    }

    public long update(String mName, String medicineType, String oldMedicineName) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MEDICINE_NAME, mName);
        contentValues.put(DatabaseHelper.MEDICINE_TYPE, medicineType);
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.MEDICINE_NAME_TABLE2, mName);
        long update1 = sqLiteDatabase.update(DatabaseHelper.TABLE_MEDICINE_DETAILS, contentValues, databaseHelper.MEDICINE_NAME + " =? ", new String[]{oldMedicineName});
        long update2 = sqLiteDatabase.update(DatabaseHelper.TABLE_MEDICINE_DATE_TIME, contentValue, databaseHelper.MEDICINE_NAME_TABLE2 + " =? ", new String[]{oldMedicineName});

        return update2;
    }

    public long updateAllTime(String newTime, String oldTime, String medicineName) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MEDICINE_TIME, newTime);
        // long update1=sqLiteDatabase.update(DatabaseHelper.TABLE_MEDICINE_DETAILS,contentValues,databaseHelper.MEDICINE_NAME+" =? " ,new String[]{oldMedicineName});
        long update2 = sqLiteDatabase.update(DatabaseHelper.TABLE_MEDICINE_DATE_TIME, contentValues, databaseHelper.MEDICINE_NAME_TABLE2 + " =? and " + databaseHelper.MEDICINE_TIME + " =? ", new String[]{medicineName, oldTime});
        return update2;
    }

    public long updateTime(String newTime, String oldTime, String medicineName, String date) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MEDICINE_TIME, newTime);
        // long update1=sqLiteDatabase.update(DatabaseHelper.TABLE_MEDICINE_DETAILS,contentValues,databaseHelper.MEDICINE_NAME+" =? " ,new String[]{oldMedicineName});
        long update2 = sqLiteDatabase.update(DatabaseHelper.TABLE_MEDICINE_DATE_TIME, contentValues, databaseHelper.MEDICINE_NAME_TABLE2 + " =? and " + databaseHelper.MEDICINE_TIME + " =? and " + databaseHelper.MEDICINE_DATE + " =? ", new String[]{medicineName, oldTime, date});
        return update2;
    }

    public long deleteMedicine(String name) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long delete = sqLiteDatabase.delete(DatabaseHelper.TABLE_MEDICINE_DETAILS, databaseHelper.MEDICINE_NAME + " =? ", new String[]{name});
        long delete2 = sqLiteDatabase.delete(DatabaseHelper.TABLE_MEDICINE_DATE_TIME, databaseHelper.MEDICINE_NAME_TABLE2 + " =? ", new String[]{name});
        return delete2;
    }

    public ArrayList<Medicine> retriveAllMedicineInfo() {
        ArrayList<Medicine> medicines = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        String selectQuery = "Select * from " + DatabaseHelper.TABLE_MEDICINE_DETAILS;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String mName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICINE_NAME));
                int mDuration = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICINE_DURATION)));
                String mType = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICINE_TYPE));
                int mPerday = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICINE_PER_DAY)));
                medicines.add(new Medicine(mName, mDuration, mType, mPerday));
            } while (cursor.moveToNext());
        }
        return medicines;
    }

    public long updateTakenOrNotTaken(String mName, String date, String time, int taken) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MEDICINE_TAKEN_YES_OR_NO, taken);
        long update1 = sqLiteDatabase.update(DatabaseHelper.TABLE_MEDICINE_DATE_TIME, contentValues, databaseHelper.MEDICINE_NAME_TABLE2 + " =? and " + databaseHelper.MEDICINE_DATE + " =? and " + databaseHelper.MEDICINE_TIME + " =? ", new String[]{mName, date, time});
        return update1;
    }

    public long addMedicineDateTime(Medicine medicine) {

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String startDate = medicine.getMedicineStartDate();
        Calendar thatDay = Calendar.getInstance();
        int d = getDay(startDate);
        int m = getMonth(startDate);
        int y = getYear(startDate);
        thatDay.set(Calendar.DAY_OF_MONTH, getDay(startDate));
        thatDay.set(Calendar.MONTH, getMonth(startDate));
        thatDay.set(Calendar.YEAR, getYear(startDate));
        thatDay.add(Calendar.DAY_OF_MONTH, -1);

        ArrayList<String> time = medicine.getMedicineTime();
        for (int i = 0; i < medicine.getMedicineDuration(); i++) {
            thatDay.add(Calendar.DAY_OF_MONTH, 1);
            int dd = thatDay.get(Calendar.DAY_OF_MONTH);
            int mm = thatDay.get(Calendar.MONTH);
            int yy = thatDay.get(Calendar.YEAR);
            String thatday = dd + "/" + mm + "/" + yy;
            for (int j = 0; j < time.size(); j++) {
                SQLiteDatabase sqLiteDatabas = databaseHelper.getWritableDatabase();
                ContentValues contentValue = new ContentValues();
                contentValue.put(DatabaseHelper.MEDICINE_NAME_TABLE2, medicine.getMedicineName());
                contentValue.put(DatabaseHelper.MEDICINE_DATE, thatday);
                contentValue.put(DatabaseHelper.MEDICINE_TIME, time.get(j));
                contentValue.put(DatabaseHelper.MEDICINE_TAKEN_YES_OR_NO, medicine.getMedicineTakenYesOrNo());
                insertedRow2 = sqLiteDatabas.insert(DatabaseHelper.TABLE_MEDICINE_DATE_TIME, null, contentValue);
            }
        }
        return 0;
    }

}
