package com.example.patyernewtest.View;

import com.example.patyernewtest.Model.PlaceMark;

public interface UpdatePlaceMark {
    void showPlaceMark(PlaceMark mark, boolean isActual);
    void showInfoPlaceMarkView(PlaceMark mark);
    void errorUpdatePlaceMark(String message);
    void readListUserPlaceMark(String idPlaceMark);
}
