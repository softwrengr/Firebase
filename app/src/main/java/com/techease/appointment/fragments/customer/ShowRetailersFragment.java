package com.techease.appointment.fragments.customer;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techease.appointment.R;

import com.techease.appointment.actvities.MainActivity;
import com.techease.appointment.fragments.customer.ShowCalendarFragment;
import com.techease.appointment.utilities.GeneralUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowRetailersFragment extends Fragment {
    View view;
    @BindView(R.id.retailer_one)
    TextView tvRetailerOne;
    @BindView(R.id.retailer_two)
    TextView tvRetailerTwo;
    @BindView(R.id.retailer_three)
    TextView tvRetailerThree;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_show_retailers, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().hide();
        initUI();
        return view;
    }

    private void initUI(){
        ButterKnife.bind(this,view);
        showAllRetailers();
        final Bundle bundle = new Bundle();

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans_Regular.ttf");
        tvRetailerOne.setTypeface(tf);
        tvRetailerTwo.setTypeface(tf);
        tvRetailerThree.setTypeface(tf);

        tvRetailerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.putStringValueInEditor(getActivity(),"name",tvRetailerOne.getText().toString());
                GeneralUtils.connectFragmentWithBack(getActivity(),new ShowCalendarFragment());
            }
        });

        tvRetailerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.putStringValueInEditor(getActivity(),"name",tvRetailerTwo.getText().toString());
                GeneralUtils.connectFragmentWithBack(getActivity(),new ShowCalendarFragment());
            }
        });

        tvRetailerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.putStringValueInEditor(getActivity(),"name",tvRetailerThree.getText().toString());
                GeneralUtils.connectFragmentWithBack(getActivity(),new ShowCalendarFragment());
            }
        });
    }

    private void showAllRetailers(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("available days");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Toast.makeText(getActivity(), "No Retailer available", Toast.LENGTH_SHORT).show();
                } else {
                    String name = dataSnapshot.child("Abdullah").getKey();
                    String phone = dataSnapshot.child("Adam").getKey();
                    String email = dataSnapshot.child("Danyal").getKey();

                    tvRetailerOne.setText(name);
                    tvRetailerTwo.setText(phone);
                    tvRetailerThree.setText(email);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(eventListener);
    }

}
