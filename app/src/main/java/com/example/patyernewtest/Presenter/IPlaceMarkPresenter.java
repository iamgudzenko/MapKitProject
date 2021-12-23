package com.example.patyernewtest.Presenter;

import org.joda.time.DateTime;

public interface IPlaceMarkPresenter {
    void writePlaceMarkToDB(String name, double latitude, double longitude, String emailUser, String description, String contact, String dataTime, String timeTysa, int removeInHours, int numberOfJoinUsers);
    void readPlaceMark();
    void showInfoPlaceMark(String id);
    void userJoinPlaceMark(String id, int numberOfJoin);
    void userPlaceMarkIdListAdd(String id, String emailUser);
    void userPlaceMarkIdListDelete(String id, String emailUser);
    void listUserPlaceMarkId(String emailUser);
}
