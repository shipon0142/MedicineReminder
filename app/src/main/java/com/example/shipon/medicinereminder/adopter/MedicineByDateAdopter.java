package com.example.shipon.medicinereminder.adopter;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.shipon.medicinereminder.Class.Medicine;
import com.example.shipon.medicinereminder.Class.MedicinePerRow;
import com.example.shipon.medicinereminder.R;
import com.example.shipon.medicinereminder.database.MedicineManagementDatabase;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Shipon on 8/25/2018.
 */

public class MedicineByDateAdopter extends RecyclerView.Adapter<MedicineByDateAdopter.ViewHolder> {
    Context mContext;
    ArrayList<MedicinePerRow> medicinePerRows;
    ArrayList<Medicine> medicine;
    float minute;
    LayoutInflater layoutInflater;

    public MedicineByDateAdopter(Context mContext, ArrayList<MedicinePerRow> medicinePerRows, ArrayList<Medicine> medicine) {
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.medicinePerRows = medicinePerRows;
        this.medicine = medicine;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.medicine_by_date, parent, false);
        return new ViewHolder(view);
    }

    public void createPopup(ViewHolder holder, final int position) {
        float hou = dayCounts(medicinePerRows.get(position).getMedicineTakenDate()
                , medicinePerRows.get(position).getMedicineTime());
        final float minut = hou * 60;
        Calendar mcurrentTime = Calendar.getInstance();
        final int h = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int m = mcurrentTime.get(Calendar.MINUTE);
        PopupMenu popup = new PopupMenu(mContext, holder.dotsIV);
        popup.inflate(R.menu.takenornottaken);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.TakenID:
                        if (minut > -240.00 && minut <= 0) {
                            MedicineManagementDatabase obj = new MedicineManagementDatabase(mContext);
                            obj.updateTakenOrNotTaken(medicinePerRows.get(position).getMedicineName()
                                    , medicinePerRows.get(position).getMedicineTakenDate()
                                    , medicinePerRows.get(position).getMedicineTime(), 1);
                            medicinePerRows.get(position).setMedicineTakenYesOrNo(1);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(mContext, "You can't upate this now", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.NotTakenID:
                        if (minut > -240.00 && minut <= 0) {
                            MedicineManagementDatabase obBj = new MedicineManagementDatabase(mContext);
                            obBj.updateTakenOrNotTaken(medicinePerRows.get(position).getMedicineName()
                                    , medicinePerRows.get(position).getMedicineTakenDate()
                                    , medicinePerRows.get(position).getMedicineTime(), 0);
                            medicinePerRows.get(position).setMedicineTakenYesOrNo(0);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(mContext, "You can't upate this now", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.ResuduleID:
                        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, final int i, final int i1) {
                                final String takenTime = i + ":" + i1;
                                new AlertDialog.Builder(mContext)
                                        .setMessage("Do you really want to update time?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton("All days", new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MedicineManagementDatabase obj = new MedicineManagementDatabase(mContext);
                                                obj.updateAllTime(takenTime, medicinePerRows.get(position).getMedicineTime(), medicinePerRows.get(position).getMedicineName());
                                                medicinePerRows.get(position).setMedicineTime(takenTime);
                                                notifyDataSetChanged();
                                            }
                                        })
                                        .setNeutralButton("Cancel", null)
                                        .setNegativeButton("This day", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                MedicineManagementDatabase obj = new MedicineManagementDatabase(mContext);
                                                obj.updateTime(takenTime, medicinePerRows.get(position).getMedicineTime(), medicinePerRows.get(position).getMedicineName(), medicinePerRows.get(position).getMedicineTakenDate());
                                                medicinePerRows.get(position).setMedicineTime(takenTime);
                                                notifyDataSetChanged();
                                            }
                                        }).show();
                            }
                        }, h, m, false);
                        timePickerDialog.show();


                        break;
                }
                return false;

            }
        });
        popup.show();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String mType = null;
        for (int i = 0; i < medicine.size(); i++) {
            if (medicine.get(i).getMedicineName().equals(medicinePerRows.get(position).getMedicineName())) {
                mType = medicine.get(i).getMedicineType();
            }
        }
        int index = medicine.indexOf(medicinePerRows.get(position).getMedicineName());

        holder.timeTV.setText(getTime1(position));
        holder.dotsIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopup(holder, position);
            }
        });
        float hour = dayCounts(medicinePerRows.get(position).getMedicineTakenDate()
                , medicinePerRows.get(position).getMedicineTime());
        minute = hour * 60;
        float day = hour / 24;
        int i = medicinePerRows.get(position).getMedicineTakenYesOrNo();
        int ii = medicinePerRows.get(position).getMedicineTakenYesOrNo();

        if (medicinePerRows.get(position).getMedicineTakenYesOrNo() == 1) {
            holder.dotsIV.setColorFilter(ContextCompat.getColor(mContext,
                    R.color.green));
            holder.remainingTV.setText("Taken");
            holder.remainingTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.green));
            holder.timeTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.green));
        } else if (minute <= -240.00) {

            holder.dotsIV.setColorFilter(ContextCompat.getColor(mContext,
                    R.color.red));
            holder.remainingTV.setText("Not Taken");
            holder.remainingTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.red));
            holder.timeTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.red));
        } else if (minute >= -10.00 && minute <= 0.00) {
            holder.dotsIV.setColorFilter(ContextCompat.getColor(mContext,
                    R.color.WHITE));
            holder.remainingTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.WHITE));
            holder.remainingTV.setText("Time to take now " + mType);
            holder.timeTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.WHITE));
        } else if (minute > -60.00 && minute < -10.00) {
            holder.dotsIV.setColorFilter(ContextCompat.getColor(mContext,
                    R.color.WHITE));
            holder.remainingTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.WHITE));
            holder.timeTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.WHITE));
            holder.remainingTV.setText((int) minute * -1 + " minutes passed. Take " + mType);
        } else if (minute > -240.00 && minute <= -60.00) {
            holder.dotsIV.setColorFilter(ContextCompat.getColor(mContext,
                    R.color.WHITE));
            holder.remainingTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.WHITE));
            holder.timeTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.WHITE));
            holder.remainingTV.setText((int) hour * -1 + " hours passed. Take " + mType);
        } else if (minute <= 60.00) {
            holder.dotsIV.setColorFilter(ContextCompat.getColor(mContext,
                    R.color.black));
            holder.timeTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.black));
            holder.remainingTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.black));
            holder.remainingTV.setText((int) minute + " minutes to take");
        } else if (hour <= 24.00) {
            holder.dotsIV.setColorFilter(ContextCompat.getColor(mContext,
                    R.color.black));
            holder.timeTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.black));
            holder.remainingTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.black));
            holder.remainingTV.setText((int) hour + " hours to take");
        } else {
            holder.dotsIV.setColorFilter(ContextCompat.getColor(mContext,
                    R.color.black));
            holder.timeTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.black));
            holder.remainingTV.setTextColor(ContextCompat.getColor(mContext,
                    R.color.black));
            holder.remainingTV.setText((int) day + " days to take");
        }
    }

    public float dayCounts(String date, String time) {
        Calendar today = Calendar.getInstance();
        int ww = 12;
        int l = today.get(Calendar.MONTH);
        int m = today.get(Calendar.DAY_OF_MONTH);
        int i = today.get(Calendar.HOUR_OF_DAY);
        int j = today.get(Calendar.MINUTE);

        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH, getDay(date));
        thatDay.set(Calendar.MONTH, getMonth(date));
        thatDay.set(Calendar.YEAR, getYear(date));
        thatDay.set(Calendar.HOUR_OF_DAY, getHour(time));
        thatDay.set(Calendar.MINUTE, getMin(time));
        int lK = thatDay.get(Calendar.MONTH);
        int mHH = thatDay.get(Calendar.DAY_OF_MONTH);
        int ii = thatDay.get(Calendar.HOUR_OF_DAY);
        int jj = thatDay.get(Calendar.MINUTE);
        long tht = thatDay.getTimeInMillis();

        long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();

        float dayCount = (float) diff / (60 * 60 * 1000);
        return dayCount;
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

    public int getHour1(String time) {
        int str1 = time.indexOf(":");
        String st1 = time.substring(0, str1);
        int h = Integer.parseInt(st1);

        return h;
    }

    public int getHour(String time) {
        int str1 = time.indexOf(":");
        String st1 = time.substring(0, str1);
        int h = Integer.parseInt(st1);
        // if (h >= 12) h = h % 12;
        return h;
    }

    public int getMin(String time) {
        int str1 = time.indexOf(":");
        String st2 = time.substring(str1 + 1, time.length());
        return Integer.parseInt(st2);
    }

    public String getTime1(int position) {
        String AM_PM = null;
        String time = medicinePerRows.get(position).getMedicineTime().toString();
        int h = getHour1(time);
        if (h >= 13) {
            h = h % 12;
            AM_PM = "PM";
        } else if (h == 0) {
            h = 12;
            AM_PM = "AM";
        } else if (h == 12) {
            AM_PM = "PM";
        } else {
            AM_PM = "AM";
        }
        int m = getMin(time);
        String mm = "" + m;
        if (m < 10) mm = "0" + m;
        return h + ":" + mm + " " + AM_PM;
    }

    @Override
    public int getItemCount() {
        return medicinePerRows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeTV, remainingTV;
        ImageView dotsIV;
        LinearLayout medicineDateLinearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            timeTV = itemView.findViewById(R.id.TimeTV);
            remainingTV = itemView.findViewById(R.id.RemainingTV);
            dotsIV = itemView.findViewById(R.id.DotsIV);
            medicineDateLinearLayout = itemView.findViewById(R.id.MedicineDateLinearLayout);

        }

    }

}
