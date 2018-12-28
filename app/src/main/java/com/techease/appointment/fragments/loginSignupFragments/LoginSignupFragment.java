package com.techease.appointment.fragments.loginSignupFragments;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.techease.appointment.R;
import com.techease.appointment.actvities.MainActivity;
import com.techease.appointment.fragments.customer.CustomerHomeFragment;
import com.techease.appointment.fragments.retailers.RetailerHomeFragment;
import com.techease.appointment.utilities.GeneralUtils;


public class LoginSignupFragment extends Fragment {
    Button btnGoLogin,btnGoSignup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_signup, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().hide();
        btnGoLogin = view.findViewById(R.id.btn_go_login);
        btnGoSignup = view.findViewById(R.id.btn_go_signup);

        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });
        btnGoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragment(getActivity(),new SignUpFragment());
            }
        });
        return view;
    }

    private void showDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.check_user_layout);
        Button btnCustomer,btnRetailer;
        btnCustomer = dialog.findViewById(R.id.customer);
        btnRetailer = dialog.findViewById(R.id.retailer);

        btnRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RetailerHomeFragment();
                GeneralUtils.putStringValueInEditor(getActivity(),"type","retailer");
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                dialog.dismiss();
            }
        });

        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LoginFragment();
                GeneralUtils.putStringValueInEditor(getActivity(),"type","customer");
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
