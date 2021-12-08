package com.example.patyernewtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.patyernewtest.Presenter.LocationUser;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

public class PointSelectionMap extends AppCompatActivity implements UserLocationObjectListener, CameraListener {
    private final Point TARGET_LOCATION = new Point(59.874541, 29.828604);

    private MapView mapView;
    ImageButton buttonMyLocation;
    private MapObjectCollection mapObjects;
    private UserLocationLayer userLocationLayer;
    Button buttonYesAddMark;
    Button buttonСancel;
    Point selectedPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_selection_map);
        mapView = findViewById(R.id.pointSelectionMapview);
        mapView.getMap().setRotateGesturesEnabled(true);
        mapView.getMap().move(new CameraPosition(TARGET_LOCATION, 14, 0, 0));
        mapView.getMap().addCameraListener(this);
        mapObjects = mapView.getMap().getMapObjects();

        MapKit mapKit = MapKitFactory.getInstance();
        userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);
        userLocationLayer.cameraPosition();

        buttonMyLocation = findViewById(R.id.myLocation2);

        buttonMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapView.getMap().move(
                            new CameraPosition(userLocationLayer.cameraPosition().getTarget(), 15.0f, 0.0f, 0.0f),
                            new Animation(Animation.Type.SMOOTH, 1),
                            null);

            }
        });

        buttonYesAddMark = findViewById(R.id.buttonYesAddMark);
        buttonYesAddMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (PointSelectionMap.this, AddNewPlaceMark.class);
                intent.putExtra("isPoint", true);
                intent.putExtra("latitude", selectedPoint.getLatitude());
                intent.putExtra("longitude", selectedPoint.getLongitude());

                startActivity(intent);
                finish();
            }
        });

        buttonСancel = findViewById(R.id.buttonСancel);
        buttonСancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (PointSelectionMap.this, AddNewPlaceMark.class);
                startActivity(intent);
                finish();
            }
        });

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

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, R.drawable.user_arrow));
        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE & 0x99ffffff);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {

    }

    @Override
    public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
        selectedPoint = new Point(cameraPosition.getTarget().getLatitude(), cameraPosition.getTarget().getLongitude());
    }
}