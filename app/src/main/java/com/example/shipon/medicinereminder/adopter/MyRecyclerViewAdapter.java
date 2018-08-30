package com.example.shipon.medicinereminder.adopter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.medicinereminder.R;
import com.example.shipon.medicinereminder.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    List<CardView> itemViewList = new ArrayList<>();
    List<TextView> TextViewList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    private ItemClickListener mClickListener;
    ArrayList<String> MadicineName;
    String NAME;
    public static String MNAME;

    public MyRecyclerViewAdapter(Context context, ArrayList<String> MadicineName, String NAME) {
        this.mInflater = LayoutInflater.from(context);
        this.mClickListener = (ItemClickListener) context;
        this.MadicineName = MadicineName;
        this.mContext = context;
        this.NAME = NAME;

    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.medicineName.setText(MadicineName.get(position).toString());
        if (MadicineName.contains(NAME)) {
            if (holder.medicineName.getText().toString().equals(NAME)) {
                MNAME=holder.medicineName.getText().toString();
                holder.medicineName.setTextSize(25);
                holder.medicineName.setTextColor(ContextCompat.getColor(mContext,
                        R.color.WHITE));
                holder.medicineCV.setCardBackgroundColor(Color.parseColor("#5778e1"));
            }
        } else if (position == 0) {
            MNAME=holder.medicineName.getText().toString();
            holder.medicineCV.setCardBackgroundColor(Color.parseColor("#5778e1"));
            holder.medicineName.setTextColor(ContextCompat.getColor(mContext,
                    R.color.WHITE));
        } else {
            holder.medicineName.setTextSize(15);
        }

    }

    @Override
    public int getItemCount() {
        return MadicineName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View myView;
        TextView medicineName;
        CardView medicineCV;

        ViewHolder(View itemView) {
            super(itemView);
            medicineName = itemView.findViewById(R.id.MedicineName);
            medicineCV = itemView.findViewById(R.id.MedicineCV);
            itemViewList.add(medicineCV);
            TextViewList.add(medicineName);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            for (CardView card : itemViewList) {
                if (itemViewList.get(getAdapterPosition()) == card) {
                    card.setCardBackgroundColor(Color.parseColor("#5778e1"));
                } else card.setCardBackgroundColor(Color.parseColor("#57bfe1"));
            }
            for (TextView textView : TextViewList) {
                if (TextViewList.get(getAdapterPosition()) == textView) {
                    MNAME=textView.getText().toString();
                    textView.setTextSize(25);
                    textView.setTextColor(ContextCompat.getColor(mContext,
                            R.color.WHITE));
                } else {
                    textView.setTextSize(15);
                    textView.setTextColor(ContextCompat.getColor(mContext,
                            R.color.black2));
                }
            }

            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
                // medicineCV.setCardBackgroundColor(Color.parseColor("#5778e1"));
            }
        }
    }

    public String getItem(int id) {
        return MadicineName.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}