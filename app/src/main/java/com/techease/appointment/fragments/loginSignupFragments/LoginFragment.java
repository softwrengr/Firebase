package com.techease.appointment.fragments.loginSignupFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.techease.appointment.R;
import com.techease.appointment.actvities.MainActivity;
import com.techease.appointment.fragments.customer.CustomerHomeFragment;
import com.techease.appointment.fragments.retailers.RetailerHomeFragment;
import com.techease.appointment.utilities.AlertUtils;
import com.techease.appointment.utilities.GeneralUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginFragment extends Fragment {
    AlertDialog alertDialog;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_new_user)
    TextView tvNewUser;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    View view;
    private FirebaseAuth auth;
    String  strEmail, strPassword,strUserType;
    private boolean valid = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        strUserType = GeneralUtils.getUserType(getActivity());
        Toast.makeText(getActivity(), strUserType, Toast.LENGTH_SHORT).show();
        initUI();
        return view;
    }

    private void initUI(){
        ButterKnife.bind(this, view);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    alertDialog = AlertUtils.createProgressDialog(getActivity());
                    alertDialog.show();
                    customerLogin();
                }
            }
        });

        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragment(getActivity(),new SignUpFragment());
            }
        });
    }

    private void customerLogin() {

      auth.signInWithEmailAndPassword(strEmail,strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if(!task.isSuccessful()){
                  alertDialog.dismiss();
                  Toast.makeText(getActivity(), "your email or password is incorrect", Toast.LENGTH_SHORT).show();
              }
              else {
                  alertDialog.dismiss();
                  if(strUserType.equals("customer")){
                      GeneralUtils.connectFragment(getActivity(),new CustomerHomeFragment());
                  }
                  else {
                      GeneralUtils.connectFragment(getActivity(),new RetailerHomeFragment());
                  }

              }
          }
      });
    }

    private boolean validate() {
        valid = true;

        strEmail = etEmail.getText().toString();
        strPassword = etPassword.getText().toString();
        GeneralUtils.putStringValueInEditor(getActivity(),"email",strEmail);

        if (strEmail.isEmpty()) {
            etEmail.setError("enter a valid email address");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (strPassword.isEmpty() || strPassword.length() < 6) {
            etPassword.setError("Please enter a strong password");
            valid = false;
        } else {
            etPassword.setError(null);
        }
        return valid;
    }
}
