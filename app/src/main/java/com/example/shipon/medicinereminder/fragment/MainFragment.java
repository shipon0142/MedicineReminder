package com.example.shipon.medicinereminder.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.medicinereminder.Class.Medicine;
import com.example.shipon.medicinereminder.Class.MedicinePerRow;
import com.example.shipon.medicinereminder.R;
import com.example.shipon.medicinereminder.activity.AddMedicineActivity;
import com.example.shipon.medicinereminder.activity.MainActivity;
import com.example.shipon.medicinereminder.adopter.MedicineByDateAdopter;
import com.example.shipon.medicinereminder.database.MedicineManagementDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainFragment extends Fragment {

    String dateSelected, medicineSelected;
    TextView addMedicineTV;
    RecyclerView mListTv;


    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        addMedicineTV = view.findViewById(R.id.AddTV);
        mListTv = view.findViewById(R.id.mListRv);
        MedicineManagementDatabase obj = new MedicineManagementDatabase(getContext());
        Bundle bundle = getArguments();
        if (bundle != null) {
            dateSelected = bundle.getString("date");
            medicineSelected = bundle.getString("name");
            if (medicineSelected == null) {
                medicineSelected = "null";
            }
            ArrayList<MedicinePerRow> medicinePerRows = obj.retriveMedicineByDate(dateSelected, medicineSelected);

            ArrayList<Medicine> medicine = obj.retriveAllMedicineInfo();
            // Toast.makeText(getContext(),""+medicinePerRows.get(1).getMedicineTakenYesOrNo(),Toast.LENGTH_SHORT).show();
            if (medicinePerRows.size() != 0) {
                Collections.sort(medicinePerRows, new Comparator<MedicinePerRow>() {
                    @Override
                    public int compare(MedicinePerRow o1, MedicinePerRow o2) {
                        return Integer.compare(o1.getHourMinSum(), o2.getHourMinSum());
                    }
                });
            }
            LinearLayoutManager LayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mListTv.setLayoutManager(LayoutManagaer);
            MedicineByDateAdopter adapter = new MedicineByDateAdopter(getActivity(), medicinePerRows, medicine);
            mListTv.setAdapter(adapter);

        } else {

        }
        addMedicineTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addActivity = new Intent(getActivity(), AddMedicineActivity.class);
                startActivity(addActivity);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
