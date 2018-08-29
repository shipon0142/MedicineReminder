package com.example.shipon.medicinereminder.activity;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.medicinereminder.Class.Medicine;
import com.example.shipon.medicinereminder.Class.TakenTime;
import com.example.shipon.medicinereminder.R;
import com.example.shipon.medicinereminder.adopter.MyRecyclerViewAdapter;
import com.example.shipon.medicinereminder.adopter.ScheduleRecyclerViewAdapter;
import com.example.shipon.medicinereminder.database.MedicineManagementDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddMedicineActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner scheduleSpinner;
    RecyclerView scheduleRV;
    TextView pickDateTV;
    EditText medicineNameET, durationET;
    Button submitBtn;
    public static ArrayList<TakenTime> takenTime = new ArrayList<>();
    Spinner tabletOrLiquiedSpinner, tabletOrLiquiedQuantitySpinner;
    private int day, month, year, hour, minute;
    ArrayList<String> pickTimeList = new ArrayList<String>();
    private String[] M = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String mType;
    int mPerday;
    String startDate = "null";
    public static ArrayList<String> takenTimeperday = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        scheduleSpinner = findViewById(R.id.ScheduleSpinner);
        scheduleRV = findViewById(R.id.schedueRecyclerView);
        pickDateTV = findViewById(R.id.PickDateTv);
        tabletOrLiquiedSpinner = findViewById(R.id.TabletOrLiquiedSpinner);
        tabletOrLiquiedQuantitySpinner = findViewById(R.id.TabletOrLiquiedQuantitySpinner);
        medicineNameET = findViewById(R.id.MedicineNameET);
        durationET = findViewById(R.id.DurationET);
        submitBtn = findViewById(R.id.SubmitBtn);
        setClickListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setClickListener() {
        final SpinnerAdapter adapter1 = ArrayAdapter.createFromResource(AddMedicineActivity.this, R.array.tablet_arrays, android.R.layout.simple_spinner_dropdown_item);
        final SpinnerAdapter adapter2 = ArrayAdapter.createFromResource(AddMedicineActivity.this, R.array.liquied_arrays, android.R.layout.simple_spinner_dropdown_item);
        tabletOrLiquiedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) tabletOrLiquiedQuantitySpinner.setAdapter(adapter1);
                else tabletOrLiquiedQuantitySpinner.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        tabletOrLiquiedQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        scheduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPerday = position + 1;
                pickTimeList.clear();
                takenTime.clear();
                for (int i = 1; i <= position + 1; i++) {
                    pickTimeList.add("  Pick time");
                }
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(AddMedicineActivity.this, LinearLayoutManager.VERTICAL, false);
                scheduleRV.setLayoutManager(horizontalLayoutManagaer);
                ScheduleRecyclerViewAdapter adapter = new ScheduleRecyclerViewAdapter(AddMedicineActivity.this, pickTimeList);
                scheduleRV.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        pickDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(AddMedicineActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        startDate = i2 + "/" + i1 + "/" + i;
                        pickDateTV.setText(M[i1] + " " + i2 + "," + i);
                        pickDateTV.setTextColor(Color.BLACK);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    public void clickOnSubmit(View view) {
        String medicineName = medicineNameET.getText().toString();

        int duration = Integer.parseInt(durationET.getText().toString());
        String medicineType = mType;
        int medicinePerday = mPerday;
        String startingDate = startDate;
        ArrayList<String> takeTime = new ArrayList<>();
        for (int i = 0; i < takenTime.size(); i++) {
            takeTime.add(takenTime.get(i).getHour() + ":" + takenTime.get(i).getMinute());
        }
        int takenYesorNo = 0;
        if (medicineName.equals("")) {
            Toast.makeText(this, "Please medicine name", Toast.LENGTH_SHORT).show();
        } else if (medicinePerday != takeTime.size()) {
            Toast.makeText(this, "Please input schedule time", Toast.LENGTH_SHORT).show();
        } else if (startingDate.contains("null")) {
            Toast.makeText(this, "Please input date", Toast.LENGTH_SHORT).show();
        } else if (duration == 0) {
            Toast.makeText(this, "Please input duration time", Toast.LENGTH_SHORT).show();
        } else {
            Medicine medicine = new Medicine(medicineName, duration, medicineType, medicinePerday, startingDate, takeTime, takenYesorNo);
            MedicineManagementDatabase obj = new MedicineManagementDatabase(this);
            obj.addMedicineDetails(medicine);
            obj.addMedicineDateTime(medicine);
            finish();
            Toast.makeText(this, "Medicine added succesful", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void closeAddMedicineActivity(View view) {
        finish();
    }

    public void negativeClick(View view) {
        if (durationET.getText().toString().equals("")) durationET.setText("0");
        int duration = Integer.parseInt(durationET.getText().toString());
        if (duration > 0) {
            duration--;
            durationET.setText("" + duration);
        }
    }

    public void posotiveClick(View view) {
        if (durationET.getText().toString().equals("")) durationET.setText("0");
        int duration = Integer.parseInt(durationET.getText().toString());
        if (duration >= 0) {
            duration++;
            durationET.setText("" + duration);
        }
    }
}
