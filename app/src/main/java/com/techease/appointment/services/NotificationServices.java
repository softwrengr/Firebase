package com.techease.appointment.services;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.techease.appointment.helpers.AppointCrud;
import com.techease.appointment.utilities.AlertUtils;
import com.techease.appointment.utilities.GeneralUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class NotificationServices extends Service {
    AppointCrud appointCrud;

    public NotificationServices() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "services started", Toast.LENGTH_SHORT).show();
        appointCrud = new AppointCrud(getApplicationContext());
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c);

                Toast.makeText(NotificationServices.this, formattedDate, Toast.LENGTH_SHORT).show();

                if(appointCrud.checkItemChart(formattedDate)){
                    Toast.makeText(NotificationServices.this, "matched", Toast.LENGTH_SHORT).show();
                    AlertUtils.createNotification(getApplicationContext(),"appointment","check your today appointment",9);
                }
                else {
                    Toast.makeText(NotificationServices.this, "not matched", Toast.LENGTH_SHORT).show();
                }

            }
        },3000);

        return super.onStartCommand(intent, flags, startId);
    }


//    private void showData(){
//
//        Cursor cursor = appointCrud.getData();
//        while (cursor.moveToNext()) {
//            String strActionName = cursor.getString(1);
//            Log.d("act", strActionName);
//
//        }
//    }
}



