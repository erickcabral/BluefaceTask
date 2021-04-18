package com.blueface.codingtest.bluefacetask.fragView.test3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.blueface.codingtest.bluefacetask.R;
import com.blueface.codingtest.bluefacetask.databinding.ViewTest3Binding;
import com.blueface.codingtest.bluefacetask.supportClasses.ContCityInfoBinderModel;
import com.blueface.codingtest.bluefacetask.supportClasses.HiltApplication;
import com.blueface.codingtest.bluefacetask.supportClasses.OutputManager;
import com.blueface.codingtest.bluefacetask.supportClasses.models.City;
import com.google.android.material.textfield.TextInputLayout;

public class Test3View extends Fragment {
    private static final String TAG = "<<<_VIEW_TEST_3_>>>";
    private Test3ViewModel vModel;
    private ViewTest3Binding binder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.vModel = new ViewModelProvider(requireActivity()).get(Test3ViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binder = DataBindingUtil.inflate(inflater, R.layout.view_test_3, container, false);
        this.binder.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestWeather();
            }
        });
        this.initializeLiveData();
        return this.binder.getRoot();
    }

    private void initializeLiveData() {
        this.vModel.getWeatherResult().observe(getViewLifecycleOwner(), new Observer<City>() {
            @Override
            public void onChanged(City city) {
                if (city != null) {
                    OutputManager.logInfo(TAG, String.format("City Name: %s", city.name));
                    OutputManager.logInfo(TAG, String.format("City Name: %.1f", city.temperature));
                    updateLayout(city);
                }
            }
        });

        this.vModel.getLvdProgress().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null) {
                    binder.setEnableProgress(aBoolean);
                }
            }
        });
    }

    private void updateLayout(City city) {
        this.vModel.setLvdProgress(false);
        binder.setCity(city);
        binder.setDeviceLocation(HiltApplication.getDeviceLocation());
        binder.setContLatBinderModel(new ContCityInfoBinderModel(getResources().getString(R.string.label_latitude), HiltApplication.getLatitude()));
        binder.setContLongBinderModel(new ContCityInfoBinderModel(getResources().getString(R.string.label_longitude), HiltApplication.getLongitude()));
    }

    private void requestWeather() {
        TextInputLayout inputLayout = this.binder.inpCityName;
        String cityName = inputLayout.getEditText().getText().toString().trim();
        if ((cityName != null)) {
            if (cityName.isEmpty()) {
                inputLayout.setError(getResources().getString(R.string.warning_invalid_field));
            } else {
                inputLayout.setError(null);
                this.binder.setCity(null);
                this.vModel.setLvdProgress(true);
                this.vModel.requestWeatherInfo(cityName);
            }
        } else {
            inputLayout.setError(getResources().getString(R.string.warning_invalid_field));
        }
    }
}
