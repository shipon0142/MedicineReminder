package com.example.shipon.medicinereminder.Class;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Shipon on 8/24/2018.
 */

public class MedicinePerRow {
    private String medicineName;
    private String medicineType;
    private String medicineTakenDate;

    public void setMedicineTime(String medicineTime) {
        this.medicineTime = medicineTime;
    }

    private String medicineTime;

    public void setMedicineTakenYesOrNo(int medicineTakenYesOrNo) {
        this.medicineTakenYesOrNo = medicineTakenYesOrNo;
    }

    private int medicineTakenYesOrNo;
    private String hourminsum;


    public MedicinePerRow(String medicineName, String medicineTakenDate, String medicineTime, int medicineTakenYesOrNo) {
        this.medicineName = medicineName;
        this.medicineTakenDate = medicineTakenDate;
        this.medicineTime = medicineTime;
        this.medicineTakenYesOrNo = medicineTakenYesOrNo;


    }

    public int getHourMinSum() {
        return getHour(medicineTime) * 60 + getMin(medicineTime);
    }

    public int getHour(String time) {
        int str1 = time.indexOf(":");
        String st1 = time.substring(0, str1);
        return Integer.parseInt(st1);
    }

    public int getMin(String time) {

        int str1 = time.indexOf(":");

        String st2 = time.substring(str1 + 1, time.length());
        return Integer.parseInt(st2);
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public String getMedicineTakenDate() {
        return medicineTakenDate;
    }

    public String getMedicineTime() {
        return medicineTime;
    }

    public int getMedicineTakenYesOrNo() {
        return medicineTakenYesOrNo;
    }


    public MedicinePerRow(String medicineName, String medicineType, String medicineTakenDate, String medicineTime, int medicineTakenYesOrNo) {
        this.medicineName = medicineName;
        this.medicineType = medicineType;
        this.medicineTakenDate = medicineTakenDate;
        this.medicineTime = medicineTime;
        this.medicineTakenYesOrNo = medicineTakenYesOrNo;
    }


}
