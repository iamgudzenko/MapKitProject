package com.example.patyernewtest.Presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.patyernewtest.Model.PlaceMark;
import com.example.patyernewtest.View.UpdatePlaceMark;
import com.example.patyernewtest.View.AddPlaceMark;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlaceMarkPresenter implements IPlaceMarkPresenter{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    AddPlaceMark addMarkView;
    UpdatePlaceMark updatePlaceMark;


    public PlaceMarkPresenter(AddPlaceMark addMarkView) {
        this.addMarkView = addMarkView;
    }
    public PlaceMarkPresenter(UpdatePlaceMark updatePlaceMark) {
        this.updatePlaceMark = updatePlaceMark;
    }

    @Override
    public void writePlaceMarkToDB(String name, double latitude, double longitude, String emailUser, String description, String contact) {
        PlaceMark mark = new PlaceMark(name, latitude, longitude, emailUser, description, contact);
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("PlaceMark");
        ref.push().setValue(mark);
        addMarkView.writePlaceMarkDone("Туса добавлена!");
    }

    @Override
    public void readPlaceMark() {
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("PlaceMark").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    PlaceMark mark = ds.getValue(PlaceMark.class);
                    //Log.w("ERROR", mark.name);
                    updatePlaceMark.showPlaceMark(mark);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("ERROR", "Failed to read value.", error.toException());
                updatePlaceMark.errorUpdatePlaceMark("Failed to read value." + error.toException());
            }
        });
    }
}
