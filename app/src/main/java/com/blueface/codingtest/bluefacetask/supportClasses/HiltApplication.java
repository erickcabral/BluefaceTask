package com.blueface.codingtest.bluefacetask.supportClasses;

import android.app.Application;
import android.location.Address;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class HiltApplication extends Application {
    private static String deviceLocation = null;
    private static double latitude = 0;
    private static double longitude = 0;

    public static void setCurrentLocation(Address address) {
        deviceLocation = String.format("%s - %s", address.getAdminArea(), address.getCountryName());
        latitude = address.getLatitude();
        longitude = address.getLongitude();
    }

    public static String getDeviceLocation() {
        return deviceLocation;
    }


    public static String getLatitude() {
        return latitude != 0 ? String.valueOf(latitude) : "Not defined";
    }

    public static String getLongitude() {
        return longitude != 0 ? String.valueOf(longitude) : "Not defined";
    }
}
