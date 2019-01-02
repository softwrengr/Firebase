package com.techease.appointment.fragments.retailers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techease.appointment.R;
import com.techease.appointment.fragments.customer.CustomerHomeFragment;
import com.techease.appointment.fragments.customer.ShowCalendarFragment;
import com.techease.appointment.models.Users;
import com.techease.appointment.utilities.GeneralUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MakeScheduleFragment extends Fragment {
    View view;
    @BindView(R.id.et_ret_first_name)
    EditText etFirstName;
    @BindView(R.id.et_ret_last_name)
    EditText etLastName;
    @BindView(R.id.et_ret_company)
    EditText etCompanyName;
    @BindView(R.id.ret_phone)
    EditText etPhone;
    @BindView(R.id.ret_address)
    EditText etAddress;
    @BindView(R.id.ret_date)
    EditText etDate;
    @BindView(R.id.tv_ret_select_unit)
    TextView tvSelectUnit;
    @BindView(R.id.ret_btn_make_appointment)
    Button btnConfirmAppointment;
    @BindView(R.id.ret_layout_select_unit)
    LinearLayout layoutSelectUnit;

    private DatabaseReference mDatabase,databaseReference;
    FirebaseDatabase firebaseDatabase;
    private String strFirstName, strLastName,strEmail, strChildNode,strCompanyName,strAddress,strPhone,strDate,strUnit="";
    private String strRetailerName;
    private boolean valid = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_make_schedule, container, false);
        customActionBar();
        getActivity().setTitle("Book your appointment");
        strRetailerName = GeneralUtils.getRetailerName(getActivity());
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

                    databaseReference = FirebaseDatabase.getInstance().getReference("appointment").child(strRetailerName);
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("single_user_appointment").child(strChildNode);
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
                            GeneralUtils.connectFragment(getActivity(),new RetailerHomeFragment());
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
                GeneralUtils.connectFragment(getActivity(),new RetailerHomeFragment());
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.show();

    }

}
