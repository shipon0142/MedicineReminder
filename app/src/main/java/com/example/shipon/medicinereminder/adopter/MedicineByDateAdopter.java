package com.example.shipon.medicinereminder.adopter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.shipon.medicinereminder.Class.MedicinePerRow;
import com.example.shipon.medicinereminder.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Shipon on 8/25/2018.
 */

public class MedicineByDateAdopter extends RecyclerView.Adapter<MedicineByDateAdopter.ViewHolder> {
    Context mContext;
    ArrayList<MedicinePerRow> medicinePerRows;
    LayoutInflater layoutInflater;

    public MedicineByDateAdopter(Context mContext, ArrayList<MedicinePerRow> medicinePerRows) {
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.medicinePerRows = medicinePerRows;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.medicine_by_date, parent, false);
        return new ViewHolder(view);
    }

    public void createPopup(ViewHolder holder, int position) {
        PopupMenu popup = new PopupMenu(mContext, holder.dotsIV);
        popup.inflate(R.menu.takenornottaken);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.TakenID:

                        break;
                    case R.id.NotTakenID:

                        break;
                }
                return false;

            }
        });
        popup.show();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.timeTV.setText(getTime1(position));
        holder.dotsIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopup(holder, position);
            }
        });
        float hour = dayCounts(medicinePerRows.get(position).getMedicineTakenDate()
                , medicinePerRows.get(position).getMedicineTime());
        float minute = hour * 60;
        float day = hour / 24;
        if (minute < -240.00) {

            holder.remainingTV.setText("Not Taken");
        } else if (minute > -240.00 && minute < -60.00) {

            holder.remainingTV.setText((int) hour * -1 + " hours left. Take .5ml");
        } else if (minute > -60.00 && minute <= 0.00) {

            holder.remainingTV.setText("Time to take 0.5ml now");
        } else if (minute <= 60.00) {
            holder.remainingTV.setText((int) minute + " minutes to take");
        } else if (hour <= 24.00) {
            holder.remainingTV.setText((int) hour + " hours to take");
        } else holder.remainingTV.setText((int) day + " days to take");
    }

    public float dayCounts(String date, String time) {
        Calendar today = Calendar.getInstance();
        int ww = 12;
        int l = today.get(Calendar.MONTH);
        int m = today.get(Calendar.DAY_OF_MONTH);
        int i = today.get(Calendar.HOUR);
        int j = today.get(Calendar.MINUTE);

        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH, getDay(date));
        thatDay.set(Calendar.MONTH, getMonth(date));
        thatDay.set(Calendar.YEAR, getYear(date));
        thatDay.set(Calendar.HOUR, getHour(time));
        thatDay.set(Calendar.MINUTE, getMin(time));
        int lK = thatDay.get(Calendar.MONTH);
        int mHH = thatDay.get(Calendar.DAY_OF_MONTH);
        int ii = thatDay.get(Calendar.HOUR);
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

    public int getHour(String time) {
        int str1 = time.indexOf(":");
        String st1 = time.substring(0, str1);
        int h = Integer.parseInt(st1);
        if (h >= 12) h = h % 12;

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
        int h = getHour(time);
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
        return h + ":" + m + " " + AM_PM;

    }

    @Override
    public int getItemCount() {
        return medicinePerRows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeTV, remainingTV;
        ImageView dotsIV;

        ViewHolder(View itemView) {
            super(itemView);
            timeTV = itemView.findViewById(R.id.TimeTV);
            remainingTV = itemView.findViewById(R.id.RemainingTV);
            dotsIV = itemView.findViewById(R.id.DotsIV);

        }

    }

}
