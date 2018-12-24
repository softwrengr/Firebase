package com.techease.appointment.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by eapple on 24/12/2018.
 */

public class NotificationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "working perfect", Toast.LENGTH_SHORT).show();
        context.startService(new Intent(context,NotificationServices.class));
    }

}
