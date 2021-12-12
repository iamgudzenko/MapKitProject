package com.example.patyernewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.patyernewtest.Model.PlaceMark;
import com.example.patyernewtest.Presenter.PlaceMarkPresenter;
import com.example.patyernewtest.View.UpdatePlaceMark;

public class ListPlaceMark extends AppCompatActivity implements UpdatePlaceMark {
    PlaceMarkPresenter placeMarkPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place_mark);

        placeMarkPresenter = new PlaceMarkPresenter(this);
        placeMarkPresenter.readPlaceMark();
    }

    @Override
    public void showPlaceMark(PlaceMark mark) {

    }

    @Override
    public void showInfoPlaceMarkView(PlaceMark mark) {

    }

    @Override
    public void errorUpdatePlaceMark(String message) {

    }
}