package com.example.patyernewtest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.auth.FirebaseAuth;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.ScreenPoint;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.MapWindow;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.VisibleRegion;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MapActivity extends AppCompatActivity implements UserLocationObjectListener, InputListener, ILocationView, UpdatePlaceMark, CameraListener {
    private final String MAPKIT_API_KEY = "c4e25bdd-cf32-46b8-bf87-9c547fa9b989";
    private final Point TARGET_LOCATION = new Point(59.874541, 29.828604);

    private MapView mapView;
    private UserLocationLayer userLocationLayer;
    private FirebaseAuth mAuth;
    private MapObjectCollection mapObjects;
    LocationUser locationUser;
    ImageButton buttonMyLocation;
    boolean isPermissionDone;
    ImageButton buttonAddPlaceMark;
    PlaceMarkPresenter placeMarkPresenter;
    BottomSheetDialog bottomSheetDialogInfoPlace;
    ImageButton buttonProfileUser;
    ImageButton buttonListPlace;
    Point pointZoom;
    ImageButton plusZoom;
    ImageButton minusZoom;
    ImageButton updateButton;
    float zoom;

    double pointBottomRightLatitude;
    double pointBottomRightLongitude;
    double pointTopLeftLatitude;
    double pointTopLeftLongitude;

    HashSet<String> userPlaceMarkIdList = new HashSet<String>();
    Bundle arguments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapView = findViewById(R.id.mapview);
        mapView.getMap().setRotateGesturesEnabled(true);
        mapView.getMap().move(new CameraPosition(TARGET_LOCATION, 17, 0, 0));

        mapView.getMap().addInputListener(this);

        mapObjects = mapView.getMap().getMapObjects();
        mapView.getMap().addCameraListener(this);
        mAuth = FirebaseAuth.getInstance();

        arguments = getIntent().getExtras();
        placeMarkPresenter = new PlaceMarkPresenter(this);

        placeMarkPresenter.listUserPlaceMarkId(arguments.getString("emailUser"));


        placeMarkPresenter.readPlaceMark();


        buttonProfileUser = findViewById(R.id.buttonProfileUser);
        buttonAddPlaceMark = findViewById(R.id.buttonAddPlaceMark);
        locationUser = new LocationUser(this, this, this);
        locationUser.permissionLocation();
        buttonMyLocation = findViewById(R.id.myLocation);
        buttonListPlace = findViewById(R.id.buttonListPlace);
        plusZoom = findViewById(R.id.plusZoom);
        minusZoom = findViewById(R.id.minusZoom);
        updateButton = findViewById(R.id.updateButton);

        // кнопка update для тус
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapObjects.clear();
                placeMarkPresenter.readPlaceMark();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Тусы обновились", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        plusZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapView.getMap().move(
                        new CameraPosition(pointZoom, zoom + 2, 0.0f, 0.0f),
                        new Animation(Animation.Type.SMOOTH, 1),
                        null);

            }
        });
        minusZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapView.getMap().move(
                        new CameraPosition(pointZoom, zoom - 2, 0.0f, 0.0f),
                        new Animation(Animation.Type.SMOOTH, 1),
                        null);
            }
        });

        buttonListPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MapActivity.this, ListPlaceMark.class);
                intent.putExtra("pointBottomRightLatitude", pointBottomRightLatitude);
                intent.putExtra("pointBottomRightLongitude", pointBottomRightLongitude);
                intent.putExtra("pointTopLeftLatitude", pointTopLeftLatitude);
                intent.putExtra("pointTopLeftLongitude", pointTopLeftLongitude);
                startActivity(intent);
            }
        });

        buttonMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ERROR", "isPermissionDone = " + isPermissionDone);
                if(isPermissionDone){
                    mapView.getMap().move(
                            new CameraPosition(userLocationLayer.cameraPosition().getTarget(), 17.0f, 0.0f, 0.0f),
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
                Button buttonYes = bottomSheetDialog.findViewById(R.id.buttonYes);
                Button buttonСancelAddPlaceMark = bottomSheetDialog.findViewById(R.id.buttonСancelAddPlaceMark);

                buttonYes.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent (MapActivity.this, AddNewPlaceMark.class);
                        intent.putExtra("isPoint", false);
                        intent.putExtra("userLatitude", userLocationLayer.cameraPosition().getTarget().getLatitude());
                        intent.putExtra("userLongitude", userLocationLayer.cameraPosition().getTarget().getLongitude());
                        startActivity(intent);
                        bottomSheetDialog.hide();
                    }
                });

                buttonСancelAddPlaceMark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.hide();
                    }
                });

            }
        });

        buttonProfileUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        MapActivity.this, R.style.BottomSheetDialogTheme
                );
                View bottonSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_profile_user_bar, (LinearLayout)findViewById(R.id.bottomSheetContainerProfile));
                bottomSheetDialog.setContentView(bottonSheetView);
                bottomSheetDialog.show();

                Button exitProfileUser;
                exitProfileUser = bottomSheetDialog.findViewById(R.id.buttonYes);
                exitProfileUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAuth.signOut();
                        Intent intent = new Intent (MapActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
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
        bottomSheetDialog.show();

        Button buttonYes = bottomSheetDialog.findViewById(R.id.buttonYes);
        Button buttonСancelAddPlaceMark = bottomSheetDialog.findViewById(R.id.buttonСancelAddPlaceMark);


        buttonYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent (MapActivity.this, AddNewPlaceMark.class);
                intent.putExtra("isPoint", true);
                intent.putExtra("latitude", point.getLatitude());
                intent.putExtra("longitude", point.getLongitude());
                startActivity(intent);
                bottomSheetDialog.hide();

            }
        });
        buttonСancelAddPlaceMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.hide();
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
    public void showPlaceMark(PlaceMark mark, boolean isActual) {

        Point pointMark = new Point(mark.getLatitude(), mark.getLongitude());
        PlacemarkMapObject viewPlacemark;
        if(isActual){
            if(userPlaceMarkIdList.contains(mark.getId())){
                viewPlacemark = mapObjects.addPlacemark(pointMark, ImageProvider.fromResource(
                        this, R.drawable.star2));
                viewPlacemark.setUserData(mark.getId());
                viewPlacemark.addTapListener(placemarkMapObjectTapListener);
            } else {
                viewPlacemark = mapObjects.addPlacemark(pointMark, ImageProvider.fromResource(
                        this, R.drawable.star));
                viewPlacemark.setUserData(mark.getId());
                viewPlacemark.addTapListener(placemarkMapObjectTapListener);
            }
        }
    }

    @Override
    public void showInfoPlaceMarkView(PlaceMark mark) {
        TextView textNamePlacemark, textDescriptionPlacemark, textContactPlacemark, textDataTimeStart, textDataTimeFinish, usersOfJoin;
        textNamePlacemark = bottomSheetDialogInfoPlace.findViewById(R.id.textNamePlacemark);
        textDescriptionPlacemark = bottomSheetDialogInfoPlace.findViewById(R.id.textDescriptionPlacemark);
        textContactPlacemark = bottomSheetDialogInfoPlace.findViewById(R.id.textContactPlacemark);
        textDataTimeStart = bottomSheetDialogInfoPlace.findViewById(R.id.textDataTimeStart);
        textDataTimeFinish = bottomSheetDialogInfoPlace.findViewById(R.id.textDataTimeFinish);
        usersOfJoin = bottomSheetDialogInfoPlace.findViewById(R.id.usersOfJoin);
        DateTime dataTime = new DateTime(mark.getDataTime());

        textNamePlacemark.setText(mark.getName());
        textDescriptionPlacemark.setText(mark.getDescription());
        textContactPlacemark.setText("Контакт: " + mark.getContact());
        textDataTimeStart.setText("Начало в: " + mark.getTimeTysa());
        textDataTimeFinish.setText("присоединится можно до " + dataTime.plusHours(mark.getRemoveInHours()).toString("dd.MM.yyyy HH:mm"));
        usersOfJoin.setText("пойдут " + mark.getNumberOfJoinUsers() + " человек ");

        Button joinButton;
        Button notJoinButton;
        notJoinButton = bottomSheetDialogInfoPlace.findViewById(R.id.notJoinButton);
        joinButton = bottomSheetDialogInfoPlace.findViewById(R.id.joinButton);

        if(userPlaceMarkIdList.contains(mark.getId())){
            notJoinButton.setVisibility(View.VISIBLE);
            joinButton.setVisibility(View.GONE);
        } else {
            joinButton.setVisibility(View.VISIBLE);
            notJoinButton.setVisibility(View.GONE);
        }

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mark.setNumberOfJoinUsers(mark.getNumberOfJoinUsers() + 1);
                placeMarkPresenter.userJoinPlaceMark(mark.getId(), mark.getNumberOfJoinUsers());
                placeMarkPresenter.userPlaceMarkIdListAdd(mark.getId(), arguments.getString("emailUser"));
                joinButton.setVisibility(View.GONE);
                notJoinButton.setVisibility(View.VISIBLE);
                userPlaceMarkIdList.add(mark.getId());
                placeMarkPresenter.userPlaceMarkIdListAdd(mark.getId(), arguments.getString("emailUser"));
                mapObjects.clear();
                placeMarkPresenter.readPlaceMark();
                usersOfJoin.setText("пойдут " + (mark.getNumberOfJoinUsers() + 1) + " человек ");
            }
        });

        notJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mark.setNumberOfJoinUsers(mark.getNumberOfJoinUsers() - 1);
                placeMarkPresenter.userJoinPlaceMark(mark.getId(), mark.getNumberOfJoinUsers());
                notJoinButton.setVisibility(View.GONE);
                joinButton.setVisibility(View.VISIBLE);
                userPlaceMarkIdList.remove(mark.getId());
                placeMarkPresenter.userPlaceMarkIdListDelete(mark.getId(), arguments.getString("emailUser"));
                mapObjects.clear();
                placeMarkPresenter.readPlaceMark();
                usersOfJoin.setText("пойдут " + (mark.getNumberOfJoinUsers() - 1) + " человек ");
            }
        });

    }

    @Override
    public void errorUpdatePlaceMark(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void readListUserPlaceMark(String idPlaceMark) {
        userPlaceMarkIdList.add(idPlaceMark);

    }

    private MapObjectTapListener placemarkMapObjectTapListener = new MapObjectTapListener() {
        @Override
        public boolean onMapObjectTap(MapObject mapObject, Point point) {

            bottomSheetDialogInfoPlace = new BottomSheetDialog(
                    MapActivity.this, R.style.BottomSheetDialogTheme
            );
            View bottonSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_place_mark_info_bar, (LinearLayout)findViewById(R.id.bottomSheetContainerInfo));
            bottomSheetDialogInfoPlace.setContentView(bottonSheetView);

            placeMarkPresenter.showInfoPlaceMark(mapObject.getUserData().toString());
            bottomSheetDialogInfoPlace.show();

            return true;
        }
    };


    @Override
    public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
        ScreenPoint bottomRight = new ScreenPoint(mapView.getRight(), mapView.getBottom());
        ScreenPoint topLeft = new ScreenPoint(mapView.getLeft(), mapView.getTop());
        Point pointBottomRight = mapView.getMapWindow().screenToWorld(bottomRight);
        Point pointTopLeft = mapView.getMapWindow().screenToWorld(topLeft);

        pointZoom = new Point(cameraPosition.getTarget().getLatitude(), cameraPosition.getTarget().getLongitude());
        zoom = cameraPosition.getZoom();
    }
}