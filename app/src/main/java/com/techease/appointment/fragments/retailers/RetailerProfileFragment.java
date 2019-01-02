package com.techease.appointment.fragments.retailers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techease.appointment.R;
import com.techease.appointment.actvities.MainActivity;
import com.techease.appointment.fragments.loginSignupFragments.LoginSignupFragment;
import com.techease.appointment.utilities.GeneralUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RetailerProfileFragment extends Fragment {
    @BindView(R.id.tv_retailer_profile_name)
    TextView tvProfileName;
    @BindView(R.id.tv_retailer_profile_phone)
    TextView tvProfilePhone;
    @BindView(R.id.tv_retailer_profile_email)
    TextView tvProfileEmail;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    View view;
    private DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private String strCustomerName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_retailer_profile, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().hide();
        strCustomerName = GeneralUtils.getEmail(getActivity());
        String[] splitStr = strCustomerName.split("@");
        strCustomerName = splitStr[0];
        initUI();
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        showProfileInfo();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.putBooleanValueInEditor(getActivity(), "retailer_loggedIn", false).commit();
                GeneralUtils.connectFragment(getActivity(),new LoginSignupFragment());
            }
        });
    }


    private void showProfileInfo() {
        databaseReference = firebaseDatabase.getReference("Profile").child("Retailer_profile").child(strCustomerName);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) {
                    Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                } else {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String phone = dataSnapshot.child("phone").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();

                    tvProfileName.setText(name);
                    tvProfilePhone.setText(phone);
                    tvProfileEmail.setText(email);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        databaseReference.addListenerForSingleValueEvent(eventListener);
    }
}
