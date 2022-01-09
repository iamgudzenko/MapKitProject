package com.example.patyernewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.patyernewtest.Model.CustomListAdapter;
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
    ArrayAdapter<PlaceMark> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place_mark);

//        arguments = getIntent().getExtras();
//        placeMarkPresenter = new PlaceMarkPresenter(this);
//        placeMarkPresenter.listUserPlaceMarkId(arguments.getString("emailUser"));
//        placeMarkPresenter.readPlaceMark();
//
//        final ListView listView = (ListView) findViewById(R.id.placeMarkList);
//        listView.setAdapter(new CustomListAdapter(this, listPlaceMark));

    }

    @Override
    public void showPlaceMark(PlaceMark mark, boolean isActual) {
        if (userPlaceMarkIdList.contains(mark.getId())){
            listPlaceMark.add(mark);
            adapter.notifyDataSetChanged();
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