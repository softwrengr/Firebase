package com.techease.appointment.actvities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techease.appointment.R;
import com.techease.appointment.fragments.LoginFragment;
import com.techease.appointment.fragments.LoginSignupFragment;
import com.techease.appointment.utilities.GeneralUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GeneralUtils.connectFragment(this,new LoginSignupFragment());
    }
}
