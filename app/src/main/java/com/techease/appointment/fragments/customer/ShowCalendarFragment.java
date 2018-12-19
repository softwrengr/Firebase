package com.techease.appointment.fragments.customer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techease.appointment.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShowCalendarFragment extends Fragment {

    @BindView(R.id.show_calendar)
    DateRangeCalendarView dateRangeCalendarView;

    View view;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String strChild;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_show_calendar, container, false);
        return view;
    }

    private void initUI(){
        ButterKnife.bind(this,view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        showRetailerDate();
    }

    private void showRetailerDate() {
        databaseReference = firebaseDatabase.getReference("available days").child(strChild);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                }
                else {
                    String from = dataSnapshot.child("from").getValue().toString();
                    String to = dataSnapshot.child("to").getValue().toString();

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("error",error.getMessage());
            }
        });
    }
}
