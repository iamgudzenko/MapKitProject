package com.example.patyernewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.patyernewtest.Model.PlaceMark;
import com.example.patyernewtest.Presenter.PlaceMarkPresenter;
import com.example.patyernewtest.View.UpdatePlaceMark;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ListPlaceMark extends AppCompatActivity implements UpdatePlaceMark {
    PlaceMarkPresenter placeMarkPresenter;
    Bundle arguments;
    ArrayList<PlaceMark> listPlaceMark = new ArrayList<PlaceMark>();
    ArrayList<String> userPlaceMarkIdList = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place_mark);

        arguments = getIntent().getExtras();
        placeMarkPresenter = new PlaceMarkPresenter(this);
        placeMarkPresenter.listUserPlaceMarkId(arguments.getString("emailUser"));

        ListView lvMain = (ListView) findViewById(R.id.placeMarkList);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 , userPlaceMarkIdList);
        lvMain.setAdapter(adapter);

    }

    @Override
    public void showPlaceMark(PlaceMark mark, boolean isActual) {
        if (userPlaceMarkIdList.contains(mark.getId())){
            listPlaceMark.add(mark);
        }
    }

    @Override
    public void showInfoPlaceMarkView(PlaceMark mark) {

    }

    @Override
    public void errorUpdatePlaceMark(String message) {

    }

    @Override
    public void readListUserPlaceMark(String idPlaceMark) {
        userPlaceMarkIdList.add(idPlaceMark);
    }
}