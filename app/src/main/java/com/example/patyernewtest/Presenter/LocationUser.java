package com.example.patyernewtest.Presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.example.patyernewtest.View.ILocationView;

public class LocationUser implements ILocationUser{

    Context context;
    Activity activity;
    ILocationView locationUser;
    public LocationUser(Context context, Activity activity, ILocationView locationUser){
        this.context = context;
        this.activity = activity;
        this.locationUser = locationUser;
    }

    @Override
    public void permissionLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            locationUser.isPermissionLocation(false);
        } else {
            locationUser.isPermissionLocation(true);
        }
    }
}
