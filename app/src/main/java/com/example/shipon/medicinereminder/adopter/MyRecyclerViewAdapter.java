package com.example.shipon.medicinereminder.adopter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
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
    private LayoutInflater mInflater;
    private Context mContext;
    private ItemClickListener mClickListener;
    ArrayList<String> MadicineName;

    public MyRecyclerViewAdapter(Context context, ArrayList<String> MadicineName) {
        this.mInflater = LayoutInflater.from(context);
        this.mClickListener = (ItemClickListener) context;
        this.MadicineName = MadicineName;
        this.mContext = context;
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
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            for (CardView card : itemViewList) {
                if (itemViewList.get(getAdapterPosition()) == card) {
                    card.setCardBackgroundColor(Color.parseColor("#5778e1"));
                } else card.setCardBackgroundColor(Color.parseColor("#57bfe1"));
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