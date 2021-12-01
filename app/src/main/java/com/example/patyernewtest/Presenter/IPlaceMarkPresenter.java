package com.example.patyernewtest.Presenter;

public interface IPlaceMarkPresenter {
    void writePlaceMarkToDB(String name, double latitude, double longitude, String emailUser, String description, String contact);
    void readPlaceMark();
    void showInfoPlaceMark(String id);
}
