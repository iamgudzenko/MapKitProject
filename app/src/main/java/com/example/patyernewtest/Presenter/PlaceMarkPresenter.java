package com.example.patyernewtest.Presenter;

import com.example.patyernewtest.Model.PlaceMark;
import com.example.patyernewtest.View.ILoginView;
import com.example.patyernewtest.View.IPlaceMarkView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlaceMarkPresenter implements IPlaceMarkPresenter{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    IPlaceMarkView pleceMarkView;

    public PlaceMarkPresenter(IPlaceMarkView pleceMarkView) {
        this.pleceMarkView = pleceMarkView;
    }

    @Override
    public void writePlaceMarkToDB(String name, double latitude, double longitude, String emailUser, String description, String contact) {
        PlaceMark mark = new PlaceMark(name, latitude, longitude, emailUser, description, contact);
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("PlaceMark");
        ref.push().setValue(mark);
        pleceMarkView.writePlaceMarkDone("Туса добавлена!");
    }
}
