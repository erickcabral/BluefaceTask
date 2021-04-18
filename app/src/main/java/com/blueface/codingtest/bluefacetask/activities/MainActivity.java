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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.blueface.codingtest.bluefacetask.R;
import com.blueface.codingtest.bluefacetask.supportClasses.HiltApplication;
import com.blueface.codingtest.bluefacetask.supportClasses.OutputManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;

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

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.customToolbar);
        setSupportActionBar(toolbar);

        this.navController = Navigation.findNavController(this, R.id.navHostMain);
        this.appBarConfiguration = new AppBarConfiguration.Builder(this.navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        this.isGoogleServicesReady(this);
    }

    public boolean isGoogleServicesReady(Context context) {
        int isReady = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (isReady == ConnectionResult.SUCCESS) {
            OutputManager.logInfo(TAG, "Google Services READY");
            checkPermissions();
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

        } else { // Permission Granted
            permissionsGranted = true;
            this.requestCurrentLocation(this);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
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