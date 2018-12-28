package com.techease.appointment.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.techease.appointment.R;

/**
 * Created by eapple on 18/12/2018.
 */

public class GeneralUtils {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static Fragment connectFragment(Context context,Fragment fragment){
        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        return fragment;
    }

    public static Fragment connectFrag(Context context,Fragment fragment){
        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.retailer_container,fragment).commit();
        return fragment;
    }

    public static Fragment connectCustomerFragment(Context context,Fragment fragment){
        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.customer_fragment_container,fragment).commit();
        return fragment;
    }

    public static Fragment connectFragmentWithBack(Context context,Fragment fragment){
        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("").commit();
        return fragment;
    }

    public static SharedPreferences.Editor putStringValueInEditor(Context context, String key, String value) {
        sharedPreferences = getSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putString(key, value).commit();
        return editor;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("appoint", 0);
    }

    public static String getUserType(Context context) {
        return getSharedPreferences(context).getString("type","");
    }

    public static String getDate(Context context) {
        return getSharedPreferences(context).getString("child_date","");
    }

    public static String singleDate(Context context) {
        return getSharedPreferences(context).getString("single_date","");
    }

    public static String getEmail(Context context){
        return getSharedPreferences(context).getString("email","");
    }

    public static String getName(Context context){
        return getSharedPreferences(context).getString("name","");
    }
}
