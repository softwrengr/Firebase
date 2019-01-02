package com.techease.appointment.fragments.loginSignupFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
    @BindView(R.id.et_retailer_name)
    EditText etRetailer;
    @BindView(R.id.et_password)
    EditText etPassword;
    View view;
    private FirebaseAuth auth;
    String strEmail, strName, strPassword, strUserType;
    private boolean valid = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        auth = FirebaseAuth.getInstance();

        strUserType = GeneralUtils.getUserType(getActivity());
        initUI();
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);

        if (strUserType.equals("customer")) {
            Log.d("ok", "all is okay");
        } else {
            etRetailer.setVisibility(View.VISIBLE);
            etRetailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDropDownMenu(etRetailer);
                }
            });
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    alertDialog = AlertUtils.createProgressDialog(getActivity());
                    alertDialog.show();

                    if (strUserType.equals("customer")) {
                        customerLogin();
                    } else {
                        if (strName.isEmpty()) {
                            Toast.makeText(getActivity(), "please select retailer", Toast.LENGTH_SHORT).show();
                            valid = false;
                        } else {
                            retailerLogin();
                            GeneralUtils.putStringValueInEditor(getActivity(), "retailer_name", strName);
                        }
                    }
                }
            }
        });

        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragment(getActivity(), new SignUpFragment());
            }
        });
    }

    private void customerLogin() {

        auth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    alertDialog.dismiss();
                    Toast.makeText(getActivity(), "your email or password is incorrect", Toast.LENGTH_SHORT).show();
                } else {
                    alertDialog.dismiss();
                    GeneralUtils.putBooleanValueInEditor(getActivity(), "customer_loggedIn", true).commit();
                    GeneralUtils.connectFragment(getActivity(), new CustomerHomeFragment());
                }
            }
        });
    }

    private void retailerLogin() {

        auth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    alertDialog.dismiss();
                    Toast.makeText(getActivity(), "your email or password is incorrect", Toast.LENGTH_SHORT).show();
                } else {
                    alertDialog.dismiss();
                    GeneralUtils.putStringValueInEditor(getActivity(), "retailer_name", strName);
                    GeneralUtils.putBooleanValueInEditor(getActivity(), "retailer_loggedIn", true).commit();
                    GeneralUtils.connectFragment(getActivity(), new RetailerHomeFragment());
                }
            }
        });
    }

    private boolean validate() {
        valid = true;

        strEmail = etEmail.getText().toString();
        strPassword = etPassword.getText().toString();
        GeneralUtils.putStringValueInEditor(getActivity(), "email", strEmail);

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


    private void showDropDownMenu(EditText layout) {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(getActivity(), layout);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.retailer_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.retailer_one:
                        strName = "Abdullah";
                        etRetailer.setHint("Abdullah");
                        break;
                    case R.id.retailer_two:
                        strName = "Adam";
                        etRetailer.setHint("Adam");
                        break;
                    case R.id.retailer_three:
                        strName = "Danyal";
                        etRetailer.setHint("Danyal");
                        break;

                }
                return true;
            }
        });

        popup.show();
    }
}
