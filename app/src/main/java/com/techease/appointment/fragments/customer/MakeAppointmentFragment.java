package com.techease.appointment.fragments.customer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techease.appointment.R;
import com.techease.appointment.models.Users;
import com.techease.appointment.utilities.GeneralUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MakeAppointmentFragment extends Fragment {
    View view;
    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    @BindView(R.id.et_company)
    EditText etCompanyName;
    @BindView(R.id.phone)
    EditText etPhone;
    @BindView(R.id.address)
    EditText etAddress;
    @BindView(R.id.date)
    EditText etDate;
    @BindView(R.id.tv_select_unit)
    TextView tvSelectUnit;
    @BindView(R.id.btn_make_appointment)
    Button btnConfirmAppointment;
    @BindView(R.id.layout_select_unit)
    LinearLayout layoutSelectUnit;

    private DatabaseReference mDatabase,databaseReference;
    FirebaseDatabase firebaseDatabase;
    private String strFirstName, strLastName,strEmail, strChildNode,strCompanyName,strAddress,strPhone,strDate,strUnit="";
    private boolean valid = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_make_appointment, container, false);
        customActionBar();
        getActivity().setTitle("Book your appointment");
        strEmail = GeneralUtils.getEmail(getActivity());
        String[] splitStr = strEmail.split("@");
        strChildNode = splitStr[0];

        initUI();

        Bundle bundle = this.getArguments();
        strDate = bundle.getString("selected_date");
        etDate.setText(strDate);

        return view;
    }

    private void initUI(){
        ButterKnife.bind(this,view);

        layoutSelectUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropDownMenu(layoutSelectUnit);
            }
        });

        btnConfirmAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){

                    databaseReference = FirebaseDatabase.getInstance().getReference("users");
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("single_user_data").child(strChildNode);
                    Users users = new Users(strAddress,strCompanyName,strDate,strFirstName,strLastName,strPhone,strUnit);

                    databaseReference.push().setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "data save", Toast.LENGTH_SHORT).show();
                        }
                    });

                    mDatabase.push().setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "data save", Toast.LENGTH_SHORT).show();
                            GeneralUtils.connectFragment(getActivity(),new CustomerHomeFragment());
                        }
                    });

                }
            }
        });


    }

    private boolean validate() {
        valid = true;

        strFirstName = etFirstName.getText().toString();
        strLastName = etLastName.getText().toString();
        strCompanyName = etCompanyName.getText().toString();
        strPhone = etPhone.getText().toString();
        strAddress = etAddress.getText().toString();

        if (strFirstName.isEmpty()) {
            etFirstName.setError("enter a your first name");
            valid = false;
        } else {
            etFirstName.setError(null);
        }
        if (strLastName.isEmpty()) {
            etLastName.setError("enter a your last name");
            valid = false;
        } else {
            etLastName.setError(null);
        }

        if (strCompanyName.isEmpty()) {
            etCompanyName.setError("enter a your company name");
            valid = false;
        } else {
            etCompanyName.setError(null);
        }
        if (strPhone.isEmpty()) {
            etPhone.setError("enter your phone number");
            valid = false;
        } else {
            etPhone.setError(null);
        }
        if (strAddress.isEmpty()) {
            etAddress.setError("enter a valid address");
            valid = false;
        } else {
            etAddress.setError(null);
        }


        return valid;
    }


    private void showDropDownMenu(LinearLayout layout) {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(getActivity(), layout);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.food_trucks:
                        strUnit = "Food Trucks";
                        tvSelectUnit.setText(strUnit);
                        break;
                    case R.id.food_trailer:
                        strUnit = "Food Trailers";
                        tvSelectUnit.setText(strUnit);
                        break;

                }
                return true;
            }
        });

        popup.show();
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
        tvTitle.setText("Make Appointment");
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragment(getActivity(),new ShowCalendarFragment());
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.show();

    }

}
