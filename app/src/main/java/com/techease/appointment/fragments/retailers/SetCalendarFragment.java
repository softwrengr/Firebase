package com.techease.appointment.fragments.retailers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.techease.appointment.R;
import com.techease.appointment.fragments.customer.MakeAppointmentFragment;
import com.techease.appointment.helpers.AppointCrud;
import com.techease.appointment.utilities.AlertUtils;
import com.techease.appointment.utilities.GeneralUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SetCalendarFragment extends Fragment {
    @BindView(R.id.retailer_calendar_picker)
    CalendarPickerView calendar;
    @BindView(R.id.btnSaveDate)
    Button btnSaveAppointment;

    View view;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String strRetailName;
    boolean singleUsercheck = false;
    String strDate = "";
    ArrayList<Date> arrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        customActionBar();
        initUI();
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);
        strRetailName = GeneralUtils.getRetailerName(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        arrayList = new ArrayList<>();

        showCalendar();
        getBookedDates();

        btnSaveAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (strDate.equals("") || strDate.isEmpty()) {
                    Toast.makeText(getActivity(), "please select booking date", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("selected_date", strDate);
                    GeneralUtils.connectFragmentWithBack(getActivity(), new MakeScheduleFragment()).setArguments(bundle);
                }
            }
        });
    }


    private void showCalendar() {

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);

        calendar.deactivateDates(list);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM, yyyy", Locale.getDefault());

        calendar.init(lastYear.getTime(), nextYear.getTime(), simpleDateFormat)
                .withSelectedDate(new Date())
                .withDeactivateDates(list)
                .withHighlightedDates(arrayList);


        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                formatedDate(date);
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

    }

    private void formatedDate(Date date) {
        SimpleDateFormat spf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        strDate = spf.format(date);
        if (date == null) {
            Toast.makeText(getActivity(), "no date selected", Toast.LENGTH_SHORT).show();
        } else {

            java.util.Date newDate = null;
            try {
                newDate = spf.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            strDate = sdf.format(newDate);

        }
    }

    private void getBookedDates() {
        databaseReference = firebaseDatabase.getReference("appointment").child(strRetailName);
        databaseReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (!dataSnapshot.exists()) {
                            Toast.makeText(getActivity(), "No appointment is booked currently", Toast.LENGTH_SHORT).show();
                        } else {
                            collectPhoneNumbers((Map<String, Object>) dataSnapshot.getValue());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("error", databaseError.getMessage());
                    }
                });

    }

    private void collectPhoneNumbers(Map<String, Object> users) {

        ArrayList<String> phoneNumbers = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            phoneNumbers.add((String) singleUser.get("date"));
        }


        try {

            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

            for (int i = 0; i < phoneNumbers.size(); i++) {
                Date newdate = dateformat.parse(phoneNumbers.get(i));
                arrayList.add(newdate);
            }

            showCalendar();


        } catch (ParseException e) {
            e.printStackTrace();
        }
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
