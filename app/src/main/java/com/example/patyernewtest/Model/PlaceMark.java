package com.example.patyernewtest.Model;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class PlaceMark implements IPlaceMark{
    private FirebaseDatabase database;
    String name;
    double latitude;
    double longitude;
    Date date1;
    Date date2;
    String emailUser;
    String description;
    String contact;

//    PlaceMar(String name, double latitude, double longitude, Date date1, Date date2 ,String emailUser, String description, String contacts){
//        this.name = name;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.date1 = date1;
//        this.date2 = date2;
//        this.emailUser = emailUser;
//        this.description = description;
//        this.contacts = contacts;
//    }
public PlaceMark(String name, double latitude, double longitude, String emailUser, String description, String contact){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.emailUser = emailUser;
        this.description = description;
        this.contact = contact;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public Date getDate1() {
        return date1;
    }

    @Override
    public Date getDate2() {
        return date2;
    }

    @Override
    public String getEmailUser() {
        return emailUser;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getContact() {
        return contact;
    }
}
