package com.techease.appointment.fragments.customer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.techease.appointment.utilities.GeneralUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerAppointmentFragment extends Fragment {
    @BindView(R.id.customer_tv_name)
    TextView tvCustomerName;
    @BindView(R.id.customer_tv_address)
    TextView tvCustomerAddress;
    @BindView(R.id.customer_tv_company_name)
    TextView tvCustomerCompanyName;
    @BindView(R.id.customer_tv_phone_no)
    TextView tvCustomerPhoneNo;
    @BindView(R.id.customer_tv_unit)
    TextView tvCustomerUnit;

    View view;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String strEmail, strChildNode;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_customer, container, false);
        strEmail = GeneralUtils.getEmail(getActivity());
        String[] splitStr = strEmail.split("@");
        strChildNode = splitStr[0];
        initUI();
        return view;
    }

    private void initUI(){
        ButterKnife.bind(this,view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        showCustomerData();
    }



    private void showCustomerData() {
        databaseReference = firebaseDatabase.getReference("single_user_data").child(strChildNode);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                }
                else {
                    String first_name = dataSnapshot.child("first name").getValue().toString();
                    String last_name = dataSnapshot.child("last name").getValue().toString();
                    String phone = dataSnapshot.child("phone").getValue().toString();
                    String address = dataSnapshot.child("address").getValue().toString();
                    String companyName = dataSnapshot.child("company").getValue().toString();
                    String unit = dataSnapshot.child("unit").getValue().toString();

                    tvCustomerName.setText(first_name+" "+last_name);
                    tvCustomerPhoneNo.setText(phone);
                    tvCustomerCompanyName.setText(companyName);
                    tvCustomerUnit.setText(unit);
                    tvCustomerAddress.setText(address);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("error",error.getMessage());
            }
        });
    }
}
