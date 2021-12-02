package com.example.patyernewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.patyernewtest.Presenter.LocationUser;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;

public class PointSelectionMap extends AppCompatActivity {
    private final String MAPKIT_API_KEY2 = "c4e25bdd-cf32-46b8-bf87-9c547fa9b989";
    private final Point TARGET_LOCATION = new Point(59.878951, 29.860782);

    private MapView mapView;
    private UserLocationLayer userLocationLayer;
    LocationUser locationUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY2);
        MapKitFactory.initialize(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_selection_map);
        mapView = findViewById(R.id.pointSelectionMapview);
        mapView.getMap().setRotateGesturesEnabled(true);
        mapView.getMap().move(new CameraPosition(TARGET_LOCATION, 14, 0, 0));

    }

    @Override
    protected void onStop() {
        // Вызов onStop нужно передавать инстансам MapView и MapKit.
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        // Вызов onStart нужно передавать инстансам MapView и MapKit.
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }
}