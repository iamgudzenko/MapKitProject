package com.example.patyernewtest.Model;

import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;


public class PlaceMark implements IPlaceMark{
    private FirebaseDatabase database;
    String id;
    String name;
    double latitude;
    double longitude;
    String dataTime;
    String timeTysa;
    String emailUser;
    String description;
    String contact;
    int removeInHours;
    int numberOfJoinUsers;

public PlaceMark(String name, double latitude, double longitude, String emailUser, String description, String contact, String dataTime, String timeTysa, int removeInHours, int numberOfJoinUsers){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.emailUser = emailUser;
        this.description = description;
        this.contact = contact;
        this.dataTime = dataTime;
        this.timeTysa = timeTysa;
        this.removeInHours = removeInHours;
        this.numberOfJoinUsers = numberOfJoinUsers;
    }

public PlaceMark(){

}

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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
    public String getDataTime() {
        return dataTime;
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

    @Override
    public String getTimeTysa() {
        return timeTysa;
    }

    @Override
    public int getRemoveInHours() {
        return removeInHours;
    }

    @Override
    public int getNumberOfJoinUsers() {
        return numberOfJoinUsers;
    }

    public void setNumberOfJoinUsers(int numberOfJoinUsers) {
        this.numberOfJoinUsers = numberOfJoinUsers;
    }
}
