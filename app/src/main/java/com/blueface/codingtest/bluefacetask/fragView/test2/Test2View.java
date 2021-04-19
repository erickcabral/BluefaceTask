package com.blueface.codingtest.bluefacetask.fragView.test2;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.blueface.codingtest.bluefacetask.R;
import com.blueface.codingtest.bluefacetask.databinding.ViewTest2Binding;
import com.blueface.codingtest.bluefacetask.supportClasses.ContCityInfoBinderModel;
import com.blueface.codingtest.bluefacetask.supportClasses.HiltApplication;
import com.blueface.codingtest.bluefacetask.supportClasses.models.City;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Test2View extends Fragment implements View.OnClickListener {
    private static final String TAG = "<<_VIEW_TEST_2_>>";

    private ViewTest2Binding binder;
    private Test2ViewModel vModel;

    private TextInputLayout inpName;
    private TextInputLayout inpRank;
    private TextInputEditText edName;
    private TextInputEditText edRank;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.vModel = new ViewModelProvider(this).get(Test2ViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.binder = DataBindingUtil.inflate(inflater, R.layout.view_test_2, container, false);
        if (this.binder != null) {
            inpName = this.binder.inpCityName;
            inpRank = this.binder.inpCityRank;
            edName = this.binder.edCityName;
            edRank = this.binder.edCityRank;

            this.binder.btnSubmit.setOnClickListener(this);
        }
        this.initializeLiveData();
        return this.binder.getRoot();
    }

    private void initializeLiveData() {
        this.vModel.getLvdCity().observe(getViewLifecycleOwner(), new Observer<City>() {
            @Override
            public void onChanged(City city) {
                binder.setCity(city);
                String location = HiltApplication.getDeviceLocation();
                String latitude = HiltApplication.getLatitude();
                String longitude = HiltApplication.getLongitude();

                vModel.setDeviceLocation(location);
                vModel.setContLatitudeBinderModel(getResources().getString(R.string.label_latitude), latitude);
                vModel.setContLongitudeBinderModel(getResources().getString(R.string.label_longitude), longitude);
            }
        });

        this.vModel.getLvdDeviceLocation().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String deviceLocation) {
                if (deviceLocation != null) {
                    binder.setDeviceLocation(deviceLocation);
                }
            }
        });

        this.vModel.getLvdContLatitude().observe(getViewLifecycleOwner(), new Observer<ContCityInfoBinderModel>() {
            @Override
            public void onChanged(ContCityInfoBinderModel contCityInfoBinderModel) {
                if (contCityInfoBinderModel != null) {
                    binder.setContLatBinderModel(contCityInfoBinderModel);
                }
            }
        });

        this.vModel.getLvdContLongitude().observe(getViewLifecycleOwner(), new Observer<ContCityInfoBinderModel>() {
            @Override
            public void onChanged(ContCityInfoBinderModel contCityInfoBinderModel) {
                if (contCityInfoBinderModel != null) {
                    binder.setContLongBinderModel(contCityInfoBinderModel);
                }
            }
        });
    }

    private void onButtonClicked() {
        int validFields = 0;
        if (edName.getText() == null) {
            inpName.setError(getResources().getString(R.string.warning_invalid_field));
        } else if (edName.getText().toString().isEmpty()) {
            inpName.setError(getResources().getString(R.string.warning_invalid_field));
        } else {
            inpName.setError(null);
            validFields++;
        }

        if (edRank.getText() == null) {
            inpRank.setError(getResources().getString(R.string.warning_invalid_field));
        } else if (edRank.getText().toString().isEmpty()) {
            inpRank.setError(getResources().getString(R.string.warning_invalid_field));
        } else {
            inpRank.setError(null);
            validFields++;
        }
        if (validFields == 2) {
            String name = edName.getText().toString();
            String rank = edRank.getText().toString();
            this.vModel.createCityObject(name, rank);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit: {
                onButtonClicked();
            }

        }
    }
}