package com.example.shipon.medicinereminder.adopter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.shipon.medicinereminder.Class.CustomDialogClass;
import com.example.shipon.medicinereminder.Class.Medicine;
import com.example.shipon.medicinereminder.Class.TakenTime;
import com.example.shipon.medicinereminder.R;
import com.example.shipon.medicinereminder.activity.MedicinePreviewActivity;
import com.example.shipon.medicinereminder.database.MedicineManagementDatabase;
import com.example.shipon.medicinereminder.fragment.MedicineDashboardFragment;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.shipon.medicinereminder.activity.AddMedicineActivity.takenTime;

public class MedicineDashboardRecyclerViewAdapter extends RecyclerView.Adapter<MedicineDashboardRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    ArrayList<Medicine> medicine;

    public MedicineDashboardRecyclerViewAdapter(Context context, ArrayList<Medicine> medicine) {
        this.mInflater = LayoutInflater.from(context);
        //this.mClickListener =(ItemClickListener)context;
        this.mContext = context;

        MedicineManagementDatabase obj = new MedicineManagementDatabase(mContext);
        this.medicine = obj.retriveAllMedicineInfo();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.medicine_dashboard_rv, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.medicinenameTV.setText(medicine.get(position).getMedicineName());


    }

    @Override
    public int getItemCount() {
        return medicine.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView medicinenameTV, deleteTV, viewTV, updateTV;

        ViewHolder(View itemView) {
            super(itemView);
            medicinenameTV = itemView.findViewById(R.id.MedicineNameTV);
            deleteTV = itemView.findViewById(R.id.DeleteTV);
            updateTV = itemView.findViewById(R.id.UpateTV);
            updateTV.setOnClickListener(this);
            deleteTV.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.equals(deleteTV)) {
                new AlertDialog.Builder(mContext)
                        .setMessage("Do you really want to Delete?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                MedicineManagementDatabase obj = new MedicineManagementDatabase(mContext);
                                long row = obj.deleteMedicine(medicine.get(getAdapterPosition()).getMedicineName());

                                medicine.remove(getAdapterPosition());
                                notifyDataSetChanged();
                               // Toast.makeText(mContext, "Removed", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();

            } else if (view.equals(updateTV)) {
                CustomDialogClass cdd = new CustomDialogClass(mContext, medicine.get(getAdapterPosition()).getMedicineName()
                        , medicine.get(getAdapterPosition()).getMedicineDuration());

                cdd.show();
            }

            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }
    }

    public Medicine getItem(int id) {
        return medicine.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        // this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}