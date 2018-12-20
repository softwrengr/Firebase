package com.techease.appointment.fragments.customer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    private String strRetailerName="abdullah", strFrom, strTo,strSelectDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_show_calendar, container, false);
        getActivity().setTitle("Schedule Appointment");
        initUI();

        Bundle bundle = this.getArguments();
        if(bundle!=null){
            strRetailerName = bundle.getString("name");
            Toast.makeText(getActivity(), strRetailerName, Toast.LENGTH_SHORT).show();
            tvRetailerName.setText(strRetailerName);
        }
        else {
            Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();

        }
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);
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
        showRetailerDate();
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
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        String formatted = format1.format(sDate.getTime());
        strSelectDate = formatted;
        Bundle bundle = new Bundle();
        bundle.putString("selected_date",strSelectDate);
        GeneralUtils.connectFragmentWithBack(getActivity(),new MakeAppointmentFragment()).setArguments(bundle);
        try {
            System.out.println(format1.parse(formatted));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
