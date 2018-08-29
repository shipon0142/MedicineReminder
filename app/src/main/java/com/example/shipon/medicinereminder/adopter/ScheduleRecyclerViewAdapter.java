package com.example.shipon.medicinereminder.adopter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.shipon.medicinereminder.Class.TakenTime;
import com.example.shipon.medicinereminder.R;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.shipon.medicinereminder.activity.AddMedicineActivity.takenTime;

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ViewHolder> {
    private int day, month, year, hour, minute;

    private LayoutInflater mInflater;
    private Context mContext;
    // private ItemClickListener mClickListener;
    ArrayList<String> pickTimeList;

    public ScheduleRecyclerViewAdapter(Context context, ArrayList<String> pickTimeList) {
        this.mInflater = LayoutInflater.from(context);
        //this.mClickListener =(ItemClickListener)context;
        this.pickTimeList = pickTimeList;
        this.mContext = context;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.schedule_recyclerview, parent, false);
        Calendar mcurrentTime = Calendar.getInstance();
        hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minute = mcurrentTime.get(Calendar.MINUTE);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.pickTimeTV.setText(pickTimeList.get(position).toString());
        holder.pickTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        for (int j = 0; j < takenTime.size(); j++) {
                            if (takenTime.get(j).getPosition_in_rv() == position) {
                                takenTime.remove(j);
                                break;
                            }
                        }
                        takenTime.add(new TakenTime(i, i1, position));
                        holder.pickTimeTV.setText(getTime1(i, i1));
                        holder.pickTimeTV.setTextColor(Color.BLACK);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });
    }

    public String getTime1(int hh, int mm) {
        String AM_PM = null;

        int h = hh;
        int m = mm;
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

        return h + ":" + m + " " + AM_PM;

    }

    @Override
    public int getItemCount() {
        return pickTimeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View myView;
        TextView pickTimeTV;

        ViewHolder(View itemView) {
            super(itemView);
            pickTimeTV = itemView.findViewById(R.id.PickTimeTV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }
    }

    public String getItem(int id) {
        return pickTimeList.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        // this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}