package com.techease.appointment.models;

/**
 * Created by eapple on 18/12/2018.
 */

public class Users {

    public String name;
    public String email;
    public String phone;


    public Users(String name, String email,String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
