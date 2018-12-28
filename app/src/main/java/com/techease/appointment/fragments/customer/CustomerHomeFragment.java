package com.techease.appointment.fragments.customer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.techease.appointment.R;
import com.techease.appointment.actvities.MainActivity;
import com.techease.appointment.utilities.GeneralUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerHomeFragment extends Fragment {
    View view;
    @BindView(R.id.customer_navigation)
    BottomNavigationView bottomNavigationView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_customer_home, container, false);
        customActionBar();
        initUI();
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        GeneralUtils.connectCustomerFragment(getActivity(),new ShowRetailersFragment());
        return view;
    }

    private void initUI(){
        ButterKnife.bind(this,view);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    GeneralUtils.connectCustomerFragment(getActivity(),new ShowRetailersFragment());
                    return true;
                case R.id.navigation_schedule:
                    GeneralUtils.connectCustomerFragment(getActivity(),new CustomerAppointmentFragment());
                    return true;
                case R.id.navigation_profile:
                    Toast.makeText(getActivity(), "profile", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };


    public void customActionBar() {
        android.support.v7.app.ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView tvTitle = mCustomView.findViewById(R.id.title);
        tvTitle.setText("All Retailer");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.show();

    }
}
