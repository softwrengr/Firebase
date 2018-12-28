package com.techease.appointment.models;

/**
 * Created by eapple on 18/12/2018.
 */

public class Users {

    public String address;
    public String company;
    public String date;
    public String firstname;
    public String last_name;
    public String phone;
    public String unit;

    public Users(String address, String company, String date, String firstname, String last_name, String phone, String unit) {
        this.address = address;
        this.company = company;
        this.date = date;
        this.firstname = firstname;
        this.last_name = last_name;
        this.phone = phone;
        this.unit = unit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
