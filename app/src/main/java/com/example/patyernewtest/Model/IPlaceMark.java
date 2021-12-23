package com.example.patyernewtest.Model;

import org.joda.time.DateTime;

import java.util.Date;

public interface IPlaceMark {
    String getName();
    double getLatitude();
    double getLongitude();
    String getDataTime();
    String getEmailUser();
    String getDescription();
    String getContact();
    String getTimeTysa();
    int getRemoveInHours();
    int getNumberOfJoinUsers();
}
