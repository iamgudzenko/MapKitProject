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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;

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
    public void writePlaceMarkToDB(String name, double latitude, double longitude, String emailUser, String description, String contact, String dataTime, String timeTysa, int removeInHours) {
        PlaceMark mark = new PlaceMark(name, latitude, longitude, emailUser, description, contact, dataTime, timeTysa, removeInHours);
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
                    mark.setId(ds.getKey());

                    DateTime dataTime = new DateTime(mark.getDataTime());

                    if(dataTime.plusHours(mark.getRemoveInHours()).isBeforeNow()){
                        updatePlaceMark.showPlaceMark(mark, false);
                        ds.getRef().setValue(null);

                    } else {
                        updatePlaceMark.showPlaceMark(mark, true);
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("ERROR", "Failed to read value.", error.toException());
                updatePlaceMark.errorUpdatePlaceMark("Failed to read value." + error.toException());
            }
        });
    }

    @Override
    public void showInfoPlaceMark(String id) {

        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("PlaceMark").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if (ds.getKey() == id){
                        Log.w("SUUUUKAAAA", id);
                        PlaceMark mark = ds.getValue(PlaceMark.class);
                        updatePlaceMark.showInfoPlaceMarkView(mark);
                    }

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
