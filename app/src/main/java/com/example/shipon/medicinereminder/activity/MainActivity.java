package com.example.shipon.medicinereminder.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.medicinereminder.Class.Medicine;
import com.example.shipon.medicinereminder.Class.MedicinePerRow;
import com.example.shipon.medicinereminder.adopter.MyRecyclerViewAdapter;
import com.example.shipon.medicinereminder.R;
import com.example.shipon.medicinereminder.database.MedicineManagementDatabase;
import com.example.shipon.medicinereminder.fragment.MainFragment;
import com.example.shipon.medicinereminder.fragment.MainFragment.OnFragmentInteractionListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener, MyRecyclerViewAdapter.ItemClickListener {
    RecyclerView medicineRV;
    TextView time;
    String medicineName = null;
    String dateFormat;
    ArrayList<String> medicine = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        medicineRV = findViewById(R.id.MedicineRV);
        time = findViewById(R.id.Time);
        time.setVisibility(View.GONE);
        // time.setText(getCurrrentTime());
        setCalender();
        setMedicineOnRv();

    }

    private void callMainFragment(String date, String mName) {
        Bundle bundle = new Bundle();
        bundle.putString("date", date);
        bundle.putString("name", mName);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);
        transaction.replace(R.id.MainFragmentLayout, fragment).commit();
    }

    private void setMedicineOnRv() {
        MedicineManagementDatabase obj = new MedicineManagementDatabase(this);
        ArrayList<Medicine> medicines = obj.retriveAllMedicineInfo();
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        medicineRV.setLayoutManager(horizontalLayoutManagaer);
        for (int i = 0; i < medicines.size(); i++) {
            medicine.add(medicines.get(i).getMedicineName());
        }
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(MainActivity.this, medicine);
        medicineRV.setAdapter(adapter);
        medicineRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setCalender() {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        //* ends after 1 month from now *//*
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        final HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .datesNumberOnScreen(7)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                int dd = Integer.valueOf(DateFormat.format("dd", date).toString());
                int mm = Integer.valueOf(DateFormat.format("MM", date).toString()) - 1;
                int yy = Integer.valueOf(DateFormat.format("yyyy", date).toString());
                dateFormat = dd + "/" + mm + "/" + yy;
                if (medicineName == null && medicine.size() != 0) {
                    medicineName = medicine.get(0).toString();
                }
                callMainFragment(dateFormat, medicineName);
            }
        });
    }

    private String getCurrrentTime() {
        String time = null;
        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.AM_PM) == Calendar.AM) {
            // AM
            time = "" + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE) + " AM";

        } else {
            // PM
            time = "" + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE) + " PM";
        }
        return time;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

        } else if (item.getItemId() == R.id.MedicineReviewID) {
            Intent ii = new Intent(this, MedicinePreviewActivity.class);
            startActivity(ii);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onItemClick(View view, int position) {
        medicineName = medicine.get(position);
        callMainFragment(dateFormat, medicineName);
        Toast.makeText(this, "" + medicine.get(position), Toast.LENGTH_SHORT).show();

    }
}
