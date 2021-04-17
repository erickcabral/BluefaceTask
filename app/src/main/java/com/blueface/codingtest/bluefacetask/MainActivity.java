package com.blueface.codingtest.bluefacetask;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.blueface.codingtest.bluefacetask.supportClasses.OutputManager;
import com.blueface.codingtest.bluefacetask.supportClasses.models.City;
import com.blueface.codingtest.bluefacetask.supportClasses.models.JsonData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "<<_ACT_MAIN_>>";
    private static final int GOOGLE_SERVICE_ERROR = 1812;


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

}