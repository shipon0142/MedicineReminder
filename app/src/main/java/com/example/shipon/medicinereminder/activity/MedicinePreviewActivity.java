package com.example.shipon.medicinereminder.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.shipon.medicinereminder.R;
import com.example.shipon.medicinereminder.fragment.MedicineDashboardFragment;

public class MedicinePreviewActivity extends AppCompatActivity {
    LinearLayout fragmentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_preview);
        fragmentLayout = findViewById(R.id.MedicinePreviewFragmentLayout);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MedicineDashboardFragment fragment = new MedicineDashboardFragment();
        transaction.replace(R.id.MedicinePreviewFragmentLayout, fragment).commit();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void clickBack(View view) {
        this.finish();
    }
}
