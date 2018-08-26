package com.example.shipon.medicinereminder.Class;

import java.util.ArrayList;

/**
 * Created by Shipon on 8/22/2018.
 */

public class Medicine {
    private int medicineId;
    private String medicineName;
    private int medicineDuration;
    private String medicineType;
    private int medicinePerday;

    private int medicineId2;
    private String medicineStartDate;
    private ArrayList<String> medicineTime;
    private int medicineTakenYesOrNo;

    public Medicine(String medicineName, int medicineDuration, String medicineType, int medicinePerday) {
        this.medicineName = medicineName;
        this.medicineDuration = medicineDuration;
        this.medicineType = medicineType;
        this.medicinePerday = medicinePerday;
    }


    public Medicine(String medicineName, int medicineDuration, String medicineType, int medicinePerday,
                    String medicineStartDate, ArrayList<String> medicineTime,
                    int medicineTakenYesOrNo) {
        this.medicineName = medicineName;
        this.medicineDuration = medicineDuration;
        this.medicineType = medicineType;
        this.medicinePerday = medicinePerday;
        this.medicineStartDate = medicineStartDate;
        this.medicineTime = medicineTime;
        this.medicineTakenYesOrNo = medicineTakenYesOrNo;
    }


    public String getMedicineStartDate() {
        return medicineStartDate;
    }

    public ArrayList<String> getMedicineTime() {
        return medicineTime;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public int getMedicineDuration() {
        return medicineDuration;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public int getMedicinePerday() {
        return medicinePerday;
    }

    public int getMedicineId2() {
        return medicineId2;
    }


    public int getMedicineTakenYesOrNo() {
        return medicineTakenYesOrNo;
    }


}
