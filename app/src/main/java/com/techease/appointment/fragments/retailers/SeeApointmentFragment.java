package com.techease.appointment.fragments.retailers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techease.appointment.R;
import com.techease.appointment.actvities.MainActivity;
import com.techease.appointment.helpers.AppointCrud;
import com.techease.appointment.models.Users;
import com.techease.appointment.utilities.AlertUtils;
import com.techease.appointment.utilities.GeneralUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeeApointmentFragment extends Fragment {
    AlertDialog alertDialog;
    View view;
    @BindView(R.id.rv_user_list)
    RecyclerView rvUserList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String strEmail;
    private FirebaseRecyclerAdapter adapter;
    Context context;
    AppointCrud appointCrud;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_see_apointment, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().hide();
//        strEmail = GeneralUtils.getEmail(getActivity());
//        String[] splitStr = strEmail.split("@");
//        strChildNode = splitStr[0];
        initUI();
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        alertDialog = AlertUtils.createProgressDialog(getActivity());
        alertDialog.show();
        showCustomerData();
    }

    private void showCustomerData(){
        databaseReference = firebaseDatabase.getReference("users");

        rvUserList.setHasFixedSize(true);
        rvUserList.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(databaseReference, new SnapshotParser<Users>() {
                            @NonNull
                            @Override
                            public Users parseSnapshot(@NonNull DataSnapshot snapshot) {

                                appointCrud  = new AppointCrud(getActivity());
                                appointCrud.insertData(snapshot.child("date").getValue().toString());

                                return new Users(snapshot.child("address").getValue().toString(),
                                        snapshot.child("company").getValue().toString(),
                                        snapshot.child("date").getValue().toString(),
                                        snapshot.child("firstname").getValue().toString(),
                                        snapshot.child("last_name").getValue().toString(),
                                        snapshot.child("phone").getValue().toString(),
                                        snapshot.child("unit").getValue().toString());

                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {
                holder.setName(getActivity(),  model.getAddress(),model.getCompany(),model.getDate(),model.getFirstname(),model.getLast_name(),model.getPhone(),model.getUnit());


            }

            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_user_layout, parent, false);

                return new UsersViewHolder(view);
            }
            @Override
            public void onDataChanged() {
                alertDialog.dismiss();
                rvUserList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };

    }


    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }


        public void setName(FragmentActivity activity, String address, String company, String date, String firstname, String last_name, String phone, String unit) {
            TextView tvCompany = mView.findViewById(R.id.tv_company_name);
            TextView tvName = mView.findViewById(R.id.tv_name);
            TextView tvAddress = mView.findViewById(R.id.tv_address);
            TextView tvDate = mView.findViewById(R.id.tv_date);
            TextView tvPhone = mView.findViewById(R.id.tv_phone_no);
            TextView tvUnit = mView.findViewById(R.id.tv_unit);

            tvName.setText(firstname+" "+last_name);
            tvCompany.setText(company);
            tvAddress.setText(address);
            tvDate.setText(date);
            tvPhone.setText(phone);
            tvUnit.setText(unit);

            dateForNotification(activity,date);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public static void dateForNotification(Activity activity,String date){

       GeneralUtils.putStringValueInEditor(activity,"date",date);
    }

}


