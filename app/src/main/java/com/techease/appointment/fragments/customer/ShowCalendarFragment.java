package com.techease.appointment.fragments.customer;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techease.appointment.R;
import com.techease.appointment.utilities.GeneralUtils;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;



public class ShowCalendarFragment extends Fragment {

    @BindView(R.id.show_calendar)
    DateRangeCalendarView calendarView;
    @BindView(R.id.tvRetailerName)
    TextView tvRetailerName;
    @BindView(R.id.tvRetailerDays)
    TextView tvRetailerDays;

    View view;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String strRetailerName, strFrom, strTo,strSelectDate;
    int total;
    SimpleDateFormat sdf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_show_calendar, container, false);
        customActionBar();
        initUI();


        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if(bundle!=null){
            strRetailerName = bundle.getString("name");
            Toast.makeText(getActivity(), strRetailerName, Toast.LENGTH_SHORT).show();
            tvRetailerName.setText(strRetailerName);
        }
        else {
            strRetailerName="abdullah";

        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        showRetailerDate();
        bookApppointment();
    }

    private void showRetailerDate() {
        databaseReference = firebaseDatabase.getReference("available days").child(strRetailerName);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) {
                    Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                } else {
                    strFrom = dataSnapshot.child("from").getValue().toString();
                    strTo = dataSnapshot.child("to").getValue().toString();
                    countTotalCost(strFrom,strTo);
                    String habitnumber = strFrom + "<b>" + "    To    " + "</b> " + strTo;
                    tvRetailerDays.setText(Html.fromHtml(habitnumber ));

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("error", error.getMessage());
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        initUI();
    }

    private void bookApppointment(){
        calendarView.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar date) {
                convertStartDateToString(date);
            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {

            }
        });
    }

    private void convertStartDateToString(Calendar sDate) {

        sDate.add(Calendar.DATE, 0);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = sdf.format(sDate.getTime());
        strSelectDate = formatted;
        Bundle bundle = new Bundle();
        bundle.putString("selected_date",strSelectDate);
        GeneralUtils.connectFragmentWithBack(getActivity(),new MakeAppointmentFragment()).setArguments(bundle);
        try {
            System.out.println(sdf.parse(formatted));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public void countTotalCost(String startDate,String endDate) {

        //don't change the time and date format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


        try {
            Date d1 = format.parse(startDate);
            Date d2 = format.parse(endDate);


            DateTime dt1 = new DateTime(d1);
            DateTime dt2 = new DateTime(d2);

            total = Days.daysBetween(dt1, dt2).getDays();
            Toast.makeText(getActivity(), String.valueOf(total), Toast.LENGTH_SHORT).show();
            retailerDate(dt1,total);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void retailerDate(DateTime initialDate,int days){
        Log.d("show",String.valueOf(initialDate));

        Calendar startSelectionDate = Calendar.getInstance();
        startSelectionDate.add(Calendar.MONTH, -1);
        Calendar endSelectionDate = (Calendar) startSelectionDate.clone();
        endSelectionDate.add(Calendar.DATE, 30+days);

        Log.d("start",startSelectionDate.toString());
        calendarView.setSelectedDateRange(startSelectionDate, endSelectionDate);

    }

    public void customActionBar() {
        android.support.v7.app.ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        ImageView ivBack = mCustomView.findViewById(R.id.ivBack);
        ivBack.setVisibility(View.VISIBLE);
        TextView tvTitle = mCustomView.findViewById(R.id.title);
        tvTitle.setText("Book your appointment");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragment(getActivity(),new CustomerHomeFragment());
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.show();

    }
}
