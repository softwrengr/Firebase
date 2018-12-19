package com.techease.appointment.fragments.customer;

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
import com.techease.appointment.fragments.customer.MakeAppointmentFragment;
import com.techease.appointment.fragments.customer.ShowCalendarFragment;
import com.techease.appointment.utilities.GeneralUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowRetailersFragment extends Fragment {
    View view;
    @BindView(R.id.customer_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.retailer_one)
    TextView tvRetailerOne;
    @BindView(R.id.retailer_two)
    TextView tvRetailerTwo;
    @BindView(R.id.retailer_three)
    TextView tvRetailerThree;

    Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_show_retailers, container, false);
        initUI();
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        return view;
    }

    private void initUI(){
        ButterKnife.bind(this,view);
        showAllRetailers();
        bundle = new Bundle();

        tvRetailerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("name","abdullah");
                GeneralUtils.connectFragmentWithBack(getActivity(),new ShowCalendarFragment()).setArguments(bundle);
            }
        });

        tvRetailerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("name","adam");
                GeneralUtils.connectFragmentWithBack(getActivity(),new ShowCalendarFragment()).setArguments(bundle);
            }
        });

        tvRetailerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("name","kashif");
                GeneralUtils.connectFragmentWithBack(getActivity(),new ShowCalendarFragment()).setArguments(bundle);
            }
        });
    }

    private void showAllRetailers(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("available days");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> authors = new ArrayList<String>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.getKey();
                    authors.add(name);
                    tvRetailerOne.setText(authors.get(0));
//                    tvRetailerTwo.setText(authors.get(1));
//                    tvRetailerThree.setText(authors.get(2));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(eventListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Toast.makeText(getActivity(), "home", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_schedule:
                    GeneralUtils.connectFragmentWithBack(getActivity(),new MakeAppointmentFragment());
                    return true;
                case R.id.navigation_profile:
                    Toast.makeText(getActivity(), "profile", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };
}
