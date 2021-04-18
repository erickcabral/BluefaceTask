package com.blueface.codingtest.bluefacetask.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.blueface.codingtest.bluefacetask.R;
import com.blueface.codingtest.bluefacetask.supportClasses.HiltApplication;
import com.blueface.codingtest.bluefacetask.supportClasses.OutputManager;
import com.blueface.codingtest.bluefacetask.supportClasses.models.City;
import com.blueface.codingtest.bluefacetask.supportClasses.models.JsonData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "<<_ACT_MAIN_>>";
    private static final int GOOGLE_SERVICE_ERROR = 1812;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int PERMISSION_REQUEST_CODE = 1979;

    private boolean permissionsGranted = false;


    private MainViewModel vModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.isGoogleServicesReady(this);

        this.vModel = new ViewModelProvider(this).get(MainViewModel.class);
        this.initializeLiveData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String fixedData = this.vModel.getFixedJsonString(JsonData.paris);
        this.vModel.convertData(fixedData);
        fixedData = this.vModel.getFixedJsonString(JsonData.parisWithTemp);
        this.vModel.convertData(fixedData);
    }

    private void initializeLiveData() {
        this.vModel.getLvdCity().observe(this, new Observer<City>() {
            @Override
            public void onChanged(City city) {
                OutputManager.logInfo(TAG, String.format("City Name: %s", city.name));
                OutputManager.logInfo(TAG, String.format("City Rank: %s", city.rank));
                OutputManager.logInfo(TAG, String.format("City Temperature: %s", city.temperature));
            }
        });

        this.vModel.getLvdError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                OutputManager.logError(TAG, s);
            }
        });
    }

    public boolean isGoogleServicesReady(Context context) {
        int isReady = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (isReady == ConnectionResult.SUCCESS) {
            OutputManager.logInfo(TAG, "Google Services READY");
            this.requestCurrentLocation(this);
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(isReady)) {
            OutputManager.logWarning(TAG, "Google Services READY");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, isReady, GOOGLE_SERVICE_ERROR);
            dialog.show();

        } else {
            OutputManager.logError(TAG, "Your device is not able to use this app");
            throw new RuntimeException("GOOGLE SERVICE IS NOT AVAILABLE FOR THIS DEVICE");
        }

        return false;
    }

    // GET LOCATION //
    // ================= GET LOCATION ===================== //

    @SuppressLint("MissingPermission")
    private void requestCurrentLocation(Context context) {

        LocationServices.getFusedLocationProviderClient(this).getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }

            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }

        }).addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task != null) {
                    Geocoder geocoder = new Geocoder(context);
                    Location result = task.getResult();
                    try {
                        List<Address> fromLocation = geocoder.getFromLocation(result.getLatitude(), result.getLongitude(), 1);
                        Address address = fromLocation.get(0);
                        HiltApplication.setCurrentLocation(address);
                        return;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    // ============== PERMISSION CHECKER ================ //
    private void checkPermissions() {
        boolean finePermissionGranted = ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean coarsePermissionGranted = ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (!finePermissionGranted || !coarsePermissionGranted) {
            String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);

        } else {
            permissionsGranted = true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsGranted = false;

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i <= permissions.length; i++) {
                        if (permissions[i] == FINE_LOCATION || permissions[i] == COARSE_LOCATION) {
                            permissionsGranted = true;
                            return;
                        }
                    }
                }
            }
        }
    }
}