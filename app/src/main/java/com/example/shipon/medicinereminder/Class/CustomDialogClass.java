package com.example.shipon.medicinereminder.Class;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.shipon.medicinereminder.R;
import com.example.shipon.medicinereminder.activity.AddMedicineActivity;
import com.example.shipon.medicinereminder.activity.MedicinePreviewActivity;
import com.example.shipon.medicinereminder.database.MedicineManagementDatabase;

/**
 * Created by Shipon on 8/26/2018.
 */

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {
    public Context mContext;
    public EditText dialogMedicineNameET, dialogMedicineDurationET;
    Spinner spinnerTabletType, spinnerTabletQuantity;
    public Button submitUpdate;
    String mName;
    int duration;
    String mType;

    public CustomDialogClass(Context a, String mName, int duration) {
        super(a);
        // TODO Auto-generated constructor stub
        this.mContext = a;
        this.mName = mName;
        this.duration = duration;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        dialogMedicineNameET = findViewById(R.id.DialogMedicineNameET);
        spinnerTabletType = findViewById(R.id.DialogTabletOrLiquiedSpinner);
        spinnerTabletQuantity = findViewById(R.id.DialogTabletOrLiquiedQuantitySpinner);
        submitUpdate = findViewById(R.id.DialogSubmit);
        dialogMedicineNameET.setText(mName);
        addListener();
        submitUpdate.setOnClickListener(this);


    }

    public void addListener() {
        final SpinnerAdapter adapter1 = ArrayAdapter.createFromResource(mContext, R.array.tablet_arrays, android.R.layout.simple_spinner_dropdown_item);
        final SpinnerAdapter adapter2 = ArrayAdapter.createFromResource(mContext, R.array.liquied_arrays, android.R.layout.simple_spinner_dropdown_item);
        spinnerTabletType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) spinnerTabletQuantity.setAdapter(adapter1);
                else spinnerTabletQuantity.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerTabletQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.DialogSubmit:
                MedicineManagementDatabase obj = new MedicineManagementDatabase(mContext);
                long i = obj.update(dialogMedicineNameET.getText().toString(), mType, mName);
                if (i != 0) {
                    Toast.makeText(mContext, "Updated", Toast.LENGTH_SHORT).show();
                    //relunch();

                }
                break;
            default:
                break;
        }
        dismiss();
    }

    public void relunch() {
        Intent i = new Intent(mContext, MedicinePreviewActivity.class);
        mContext.startActivity(i);

    }

    public void update(String mName, int duration, String medicineType) {

    }
}
