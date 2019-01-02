package com.techease.appointment.actvities;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.techease.appointment.R;
import com.techease.appointment.fragments.customer.CustomerHomeFragment;
import com.techease.appointment.fragments.loginSignupFragments.LoginSignupFragment;
import com.techease.appointment.fragments.retailers.RetailerHomeFragment;
import com.techease.appointment.utilities.GeneralUtils;
import com.techease.appointment.services.NotificationServices;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED
                )
                .withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();

        startService(new Intent(MainActivity.this, NotificationServices.class));

        if(GeneralUtils.customerLogin(MainActivity.this)){
            GeneralUtils.connectFragment(MainActivity.this, new CustomerHomeFragment());
        }
        else if(GeneralUtils.reatailerLogin(MainActivity.this)){
            GeneralUtils.connectFragment(MainActivity.this, new RetailerHomeFragment());
        }
        else {
            GeneralUtils.connectFragment(this,new LoginSignupFragment());
        }
    }
}
