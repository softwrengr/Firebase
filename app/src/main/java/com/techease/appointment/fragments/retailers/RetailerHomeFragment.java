package com.techease.appointment.fragments.retailers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.techease.appointment.R;
import com.techease.appointment.actvities.MainActivity;
import com.techease.appointment.fragments.retailers.SeeApointmentFragment;
import com.techease.appointment.fragments.retailers.SetCalendarFragment;
import com.techease.appointment.utilities.GeneralUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RetailerHomeFragment extends Fragment {
   View  view;
   @BindView(R.id.navigation)
   BottomNavigationView bottomNavigationView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().show();
        GeneralUtils.connectFrag(getActivity(),new SetCalendarFragment());
        initUI();
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        return view;
    }

    private void initUI(){
        ButterKnife.bind(this,view);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    GeneralUtils.connectFrag(getActivity(),new SetCalendarFragment());
                    return true;
                case R.id.navigation_schedule:
                    GeneralUtils.connectFrag(getActivity(),new SeeApointmentFragment());
                    return true;
                case R.id.navigation_profile:
                    Toast.makeText(getActivity(), "profile", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };
}
