package com.example.patyernewtest.Presenter;

import org.joda.time.DateTime;

public interface IPlaceMarkPresenter {
    void writePlaceMarkToDB(String name, double latitude, double longitude, String emailUser, String description, String contact, String dataTime, String timeTysa, int removeInHours);
    void readPlaceMark();
    void showInfoPlaceMark(String id);
}
