package com.example.patyernewtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patyernewtest.Model.PlaceMark;
import com.example.patyernewtest.Presenter.LocationUser;
import com.example.patyernewtest.Presenter.PlaceMarkPresenter;
import com.example.patyernewtest.View.ILocationView;
import com.example.patyernewtest.View.UpdatePlaceMark;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

public class MapActivity extends AppCompatActivity implements UserLocationObjectListener, InputListener, ILocationView, UpdatePlaceMark {
    private final String MAPKIT_API_KEY = "c4e25bdd-cf32-46b8-bf87-9c547fa9b989";
    private final Point TARGET_LOCATION = new Point(59.878951, 29.860782);

    private MapView mapView;
    private UserLocationLayer userLocationLayer;
    private MapObjectCollection mapObjects;
    LocationUser locationUser;
    ImageButton buttonMyLocation;
    boolean isPermissionDone;
    ImageButton buttonAddPlaceMark;
    PlaceMarkPresenter placeMarkPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_maps);
        super.onCreate(savedInstanceState);
        mapView = findViewById(R.id.mapview);
        mapView.getMap().setRotateGesturesEnabled(true);
        mapView.getMap().move(new CameraPosition(TARGET_LOCATION, 14, 0, 0));



        mapView.getMap().addInputListener(this);

        mapObjects = mapView.getMap().getMapObjects();

        placeMarkPresenter = new PlaceMarkPresenter(this);
        placeMarkPresenter.readPlaceMark();

        buttonAddPlaceMark = findViewById(R.id.buttonAddPlaceMark);
        locationUser = new LocationUser(this, this, this);
        locationUser.permissionLocation();
        buttonMyLocation = findViewById(R.id.myLocation);

        buttonMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ERROR", "isPermissionDone = " + isPermissionDone);
                if(isPermissionDone){
                    mapView.getMap().move(
                            new CameraPosition(userLocationLayer.cameraPosition().getTarget(), 15.0f, 0.0f, 0.0f),
                            new Animation(Animation.Type.SMOOTH, 1),
                            null);
                } else {
                    LocationUser locationUser2 = new LocationUser(MapActivity.this, MapActivity.this, MapActivity.this);
                    locationUser2.permissionLocation();
                }

            }
        });

        buttonAddPlaceMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        MapActivity.this, R.style.BottomSheetDialogTheme
                );
                View bottonSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet, (LinearLayout)findViewById(R.id.bottomSheetContainer));
                bottomSheetDialog.setContentView(bottonSheetView);
                bottomSheetDialog.show();
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
    public void onMapTap(@NonNull Map map, @NonNull Point point) {
        Log.d("MAP_TAG", "point: " + point.getLatitude() + ", " + point.getLongitude());

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                MapActivity.this, R.style.BottomSheetDialogTheme
        );
        View bottonSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet, (LinearLayout)findViewById(R.id.bottomSheetContainer));
        bottomSheetDialog.setContentView(bottonSheetView);
        TextView textPoint = bottomSheetDialog.findViewById(R.id.textPoint);
        textPoint.setText("point: " + point.getLatitude() + ", " + point.getLongitude());
        bottomSheetDialog.show();

        Button buttonYes = bottomSheetDialog.findViewById(R.id.buttonYes);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent (MapActivity.this, AddNewPlaceMark.class);
                intent.putExtra("latitude", point.getLatitude());
                intent.putExtra("longitude", point.getLongitude());
                startActivity(intent);

            }
        });

    }

    @Override
    public void onMapLongTap(@NonNull Map map, @NonNull Point point) {

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
    public void isPermissionLocation(boolean isPermission) {
        if(isPermission){
            isPermissionDone = true;
            MapKit mapKit = MapKitFactory.getInstance();
            userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
            userLocationLayer.setVisible(true);
            userLocationLayer.setHeadingEnabled(true);
            userLocationLayer.setObjectListener(this);
            userLocationLayer.cameraPosition();
        } else {
            isPermissionDone = false;
        }
        Log.d("ERROR", "isPermissionDone = " + isPermissionDone);
    }

    @Override
    public void showPlaceMark(PlaceMark mark) {
        Point pointMark = new Point(mark.getLatitude(), mark.getLongitude());
        PlacemarkMapObject viewPlacemark = mapObjects.addPlacemark(pointMark, ImageProvider.fromResource(
                this, R.drawable.search_result));
        viewPlacemark.setUserData(mark.getEmailUser());
        viewPlacemark.addTapListener(placemarkMapObjectTapListener);
    }

    @Override
    public void errorUpdatePlaceMark(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private MapObjectTapListener placemarkMapObjectTapListener = new MapObjectTapListener() {
        @Override
        public boolean onMapObjectTap(MapObject mapObject, Point point) {
            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    "Circle with id " + mapObject.getUserData(),
                    Toast.LENGTH_SHORT);
            toast.show();

            return true;
        }
    };
}