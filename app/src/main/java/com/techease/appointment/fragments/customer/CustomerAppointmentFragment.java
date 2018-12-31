package com.techease.appointment.fragments.customer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techease.appointment.R;
import com.techease.appointment.actvities.MainActivity;
import com.techease.appointment.fragments.retailers.SeeApointmentFragment;
import com.techease.appointment.helpers.AppointCrud;
import com.techease.appointment.models.Users;
import com.techease.appointment.utilities.AlertUtils;
import com.techease.appointment.utilities.GeneralUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerAppointmentFragment extends Fragment {
    AlertDialog alertDialog;
    View view;
    @BindView(R.id.rv_single_user_list)
    RecyclerView rvUserList;
    @BindView(R.id.iv_customer_back)
    ImageView ivBack;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter adapter;
    private String strEmail, strChildNode;
    AppointCrud appointCrud;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_customer, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().hide();
        strEmail = GeneralUtils.getEmail(getActivity());
        String[] splitStr = strEmail.split("@");
        strChildNode = splitStr[0];
        initUI();
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);
        context = getActivity();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans_Regular.ttf");
        alertDialog = AlertUtils.createProgressDialog(getActivity());
        alertDialog.show();
        showCustomerData();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectCustomerFragment(getActivity(),new ShowRetailersFragment());
            }
        });
    }


    private void showCustomerData() {
        databaseReference = firebaseDatabase.getReference("single_user_data").child(strChildNode);

        rvUserList.setHasFixedSize(true);
        rvUserList.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(databaseReference, new SnapshotParser<Users>() {
                            @NonNull
                            @Override
                            public Users parseSnapshot(@NonNull DataSnapshot snapshot) {

                                GeneralUtils.putStringValueInEditor(getActivity(), "child_date", snapshot.child("date").getValue().toString());
                                appointCrud = new AppointCrud(getActivity());
                                appointCrud.insertSingleUserDate(snapshot.child("date").getValue().toString());

                                List<String> dateArrayList = new ArrayList<String>();
                                dateArrayList.add(snapshot.child("date").getValue().toString());
                                for (int i = 0; i < dateArrayList.size(); i++) {
                                    Toast.makeText(getActivity(), dateArrayList.get(0), Toast.LENGTH_SHORT).show();
                                }

                                return new Users(
                                        snapshot.child("address").getValue().toString(),
                                        snapshot.child("company").getValue().toString(),
                                        snapshot.child("date").getValue().toString(),
                                        snapshot.child("firstname").getValue().toString(),
                                        snapshot.child("last_name").getValue().toString(),
                                        snapshot.child("phone").getValue().toString(),
                                        snapshot.child("unit").getValue().toString());
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Users, SeeApointmentFragment.UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SeeApointmentFragment.UsersViewHolder holder, int position, @NonNull final Users model) {

                holder.setName(getActivity(), model.getAddress(), model.getCompany(), model.getDate(), model.getFirstname(), model.getLast_name(), model.getPhone(), model.getUnit());

            }

            @NonNull
            @Override
            public SeeApointmentFragment.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_user_layout, parent, false);

                return new SeeApointmentFragment.UsersViewHolder(view);
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

}
