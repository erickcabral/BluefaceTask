package com.blueface.codingtest.bluefacetask.fragView;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.blueface.codingtest.bluefacetask.R;
import com.blueface.codingtest.bluefacetask.databinding.ViewTest2Binding;
import com.blueface.codingtest.bluefacetask.supportClasses.ContCityInfoBinderModel;
import com.blueface.codingtest.bluefacetask.supportClasses.models.City;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.List;

public class Test2View extends Fragment implements View.OnClickListener {
    private static final String TAG = "<<_VIEW_TEST_2_>>";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int PERMISSION_REQUEST_CODE = 1979;

    private boolean permissionsGranted = false;

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
        checkPermissions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.binder = DataBindingUtil.inflate(inflater, R.layout.view_test_2, null, false);
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
                if (city != null) {
                    vModel.setContNameBinderModel(getResources().getString(R.string.label_name), city.name);
                    vModel.setContRankBinderModel(getResources().getString(R.string.label_rank), Integer.toString(city.rank));
                    binder.linCityInfo.setVisibility(View.VISIBLE);
                } else {
                    binder.linCityInfo.setVisibility(View.GONE);
                }
            }
        });

        this.vModel.getLvdDeviceAddress().observe(getViewLifecycleOwner(), new Observer<Address>() {
            @Override
            public void onChanged(Address address) {
                if(address!=null){
                    vModel.setContCountryBinderModel(getResources().getString(R.string.label_country), address.getCountryName());
                    vModel.setContDeviceLocation(getResources().getString(R.string.label_location), address.getAdminArea());
                    vModel.setContLatitudeBinderModel(getResources().getString(R.string.label_latitude), Double.toString(address.getLatitude()));
                    vModel.setContLongitudeBinderModel(getResources().getString(R.string.label_longitude), Double.toString(address.getLatitude()));
                }
            }
        });

        this.vModel.getContNameBinderModel().observe(getViewLifecycleOwner(), new Observer<ContCityInfoBinderModel>() {
            @Override
            public void onChanged(ContCityInfoBinderModel contCityInfoBinderModel) {
                if (contCityInfoBinderModel != null) {
                    binder.setContNameBinderModel(contCityInfoBinderModel);
                }
            }
        });

        this.vModel.getLvdContRankBinderModel().observe(getViewLifecycleOwner(), new Observer<ContCityInfoBinderModel>() {
            @Override
            public void onChanged(ContCityInfoBinderModel contCityInfoBinderModel) {
                if (contCityInfoBinderModel != null) {
                    binder.setContRankBinderModel(contCityInfoBinderModel);
                }
            }
        });

        this.vModel.getLvdContCountry().observe(getViewLifecycleOwner(), new Observer<ContCityInfoBinderModel>() {
            @Override
            public void onChanged(ContCityInfoBinderModel contCityInfoBinderModel) {
                if (contCityInfoBinderModel != null) {
                    binder.setContDeviceCountryBinderModel(contCityInfoBinderModel);
                }
            }
        });

        this.vModel.getLvdLocation().observe(getViewLifecycleOwner(), new Observer<ContCityInfoBinderModel>() {
            @Override
            public void onChanged(ContCityInfoBinderModel contCityInfoBinderModel) {
                if (contCityInfoBinderModel != null) {
                    binder.setContDeviceLocationBinderModel(contCityInfoBinderModel);
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
            submitCityData(name, rank);
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

    // ================= GET LOCATION ===================== //

    @SuppressLint("MissingPermission")
    private void submitCityData(String name, String rank) {

        LocationServices.getFusedLocationProviderClient(getContext()).getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
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
                    Geocoder geocoder = new Geocoder(requireContext());
                    Location result = task.getResult();
                    try {
                        List<Address> fromLocation = geocoder.getFromLocation(result.getLatitude(), result.getLongitude(), 1);
                        Address address = fromLocation.get(0);
                        vModel.createCityObject(name, rank, address);
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
        boolean finePermissionGranted = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean coarsePermissionGranted = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (!finePermissionGranted || !coarsePermissionGranted) {
            String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(getActivity(), permissions, PERMISSION_REQUEST_CODE);

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