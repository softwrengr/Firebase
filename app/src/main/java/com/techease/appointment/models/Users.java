package com.techease.appointment.models;

/**
 * Created by eapple on 18/12/2018.
 */

public class Users {

    public String name;
    public String email;
    public String phone;
    public String address;
    public String date;
    public String company;
    public String unit;

    public Users(String name, String email,String date, String phone, String address, String company, String unit) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.date = date;
        this.company = company;
        this.unit = unit;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
