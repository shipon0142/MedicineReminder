package com.example.shipon.medicinereminder.Class;

/**
 * Created by Shipon on 8/21/2018.
 */

public class TakenTime {
    private int hour;
    private int minute;
    private int position_in_rv;

    public TakenTime(int hour, int minute, int position_in_rv) {
        this.hour = hour;
        this.minute = minute;
        this.position_in_rv = position_in_rv;
    }

    public int getPosition_in_rv() {
        return position_in_rv;
    }


    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public TakenTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }
}
