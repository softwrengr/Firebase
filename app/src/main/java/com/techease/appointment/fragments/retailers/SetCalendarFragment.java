package com.techease.appointment.fragments.retailers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techease.appointment.R;
import com.techease.appointment.utilities.AlertUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SetCalendarFragment extends Fragment {
    AlertDialog alertDialog;
    @BindView(R.id.set_calendar)
    DateRangeCalendarView dateRangeCalendarView;
    @BindView(R.id.btnSaveDate)
    Button btnSave;
    View view;

    String startDate,endDate;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    private DatabaseReference mDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        customActionBar();
        initUI();
        return view;
    }

    private void initUI(){
        ButterKnife.bind(this,view);
        setDate();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = AlertUtils.createProgressDialog(getActivity());
                alertDialog.show();
                saveWeekDays();
            }
        });
    }

    private void setDate() {
        dateRangeCalendarView.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {
                convertStartDateToString(startDate);
            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {

                convertEndDateToString(endDate);

            }
        });

    }

    private void convertStartDateToString(Calendar sDate) {

        sDate.add(Calendar.DATE, 0);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(sDate.getTime());
        startDate = formatted;
        Toast.makeText(getActivity(), startDate, Toast.LENGTH_SHORT).show();
        try {
            System.out.println(format1.parse(formatted));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void convertEndDateToString(Calendar eDate) {
        eDate.add(Calendar.DATE, 0);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(eDate.getTime());
        endDate = formatted;
        Toast.makeText(getActivity(), endDate, Toast.LENGTH_SHORT).show();
        try {
            System.out.println(format1.parse(formatted));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void saveWeekDays(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("available days").child("abdullah");

        Map<String,String> map = new HashMap<>();
        map.put("from",startDate);
        map.put("to",endDate);
        mDatabase.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    alertDialog.dismiss();
                    Toast.makeText(getActivity(), "successfully make appointment", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void customActionBar() {
        android.support.v7.app.ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        ImageView ivBack = mCustomView.findViewById(R.id.ivBack);
        TextView tvTitle = mCustomView.findViewById(R.id.title);
        tvTitle.setText("Set your availabilty");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.show();

    }

}
