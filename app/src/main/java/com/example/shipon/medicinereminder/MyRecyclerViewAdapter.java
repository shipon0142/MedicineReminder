package com.example.shipon.medicinereminder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {


    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    ArrayList<String> MadicineName;

    MyRecyclerViewAdapter(Context context, ArrayList<String> MadicineName) {
        this.mInflater = LayoutInflater.from(context);
        this.MadicineName = MadicineName;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.medicineName.setText(MadicineName.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return MadicineName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View myView;
        TextView medicineName;

        ViewHolder(View itemView) {
            super(itemView);

            medicineName = itemView.findViewById(R.id.MedicineName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
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