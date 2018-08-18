package com.example.shipon.medicinereminder;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity {
    RecyclerView medicineRV;
    TextView time;
    ArrayList<String> medicine = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        medicineRV = findViewById(R.id.MedicineRV);
        time = findViewById(R.id.Time);
        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.AM_PM) == Calendar.AM) {
            // AM
            time.setText("" + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE) + " AM");
        } else {
            // PM
            time.setText("" + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE) + " PM");
        }
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

//* ends after 1 month from now *//*
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        //    toolbar.inflateMenu(R.menu.main_menu);
        final HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .datesNumberOnScreen(7)
                .showMonthName(false)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {

            }
        });


        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        medicineRV.setLayoutManager(horizontalLayoutManagaer);
        medicine.add("Napa");
        medicine.add("Pantonix");
        medicine.add("Napa xtra");
        medicine.add("Metro");
        medicine.add("Gemax");
        medicine.add("Alatrol");
        medicine.add("Napa");
        medicine.add("Pantonix");
        medicine.add("Napa xtra");
        medicine.add("Metro");
        medicine.add("Gemax");
        medicine.add("Alatrol");
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(MainActivity.this, medicine);
        medicineRV.setAdapter(adapter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
