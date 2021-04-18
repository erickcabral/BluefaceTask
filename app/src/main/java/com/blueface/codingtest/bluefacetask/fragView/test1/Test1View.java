package com.blueface.codingtest.bluefacetask.fragView.test1;

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
import androidx.navigation.Navigation;

import com.blueface.codingtest.bluefacetask.R;
import com.blueface.codingtest.bluefacetask.databinding.ViewTest1Binding;
import com.blueface.codingtest.bluefacetask.supportClasses.OutputManager;
import com.blueface.codingtest.bluefacetask.supportClasses.models.City;
import com.blueface.codingtest.bluefacetask.supportClasses.models.JsonData;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Test1View extends Fragment implements View.OnClickListener {
    private static final String TAG = "<<_VIEW_TEST_1_>>";

    private ViewTest1Binding binding;
    private MainViewModel vModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.vModel = new ViewModelProvider(this).get(MainViewModel.class);
        this.initializeLiveData(getParentFragment());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.view_test_1, container, false);

        this.binding.btnLoadFixedData.setOnClickListener(this::onClick);
        this.binding.btnLoadGivenData.setOnClickListener(this::onClick);
        this.binding.btnTest2.setOnClickListener(this::onClick);
        this.binding.btnTest3.setOnClickListener(this::onClick);

        return this.binding.getRoot();
    }

    private void displayInitialLog() {
        String fixedData = this.vModel.getFixedJsonString(JsonData.paris);
        this.vModel.convertData(fixedData);
        fixedData = this.vModel.getFixedJsonString(JsonData.parisWithTemp);
        this.vModel.convertData(fixedData);
    }

    private void initializeLiveData(Fragment parent) {
        this.vModel.getLvdCity().observe(parent, new Observer<City>() {
            @Override
            public void onChanged(City city) {
                if (city != null) {
                    OutputManager.logInfo(TAG, String.format(getString(R.string.log_city_name), city.name));
                    OutputManager.logInfo(TAG, String.format(getString(R.string.log_city_rank), city.rank));
                    OutputManager.logInfo(TAG, String.format(getString(R.string.log_city_temp), city.temperature));

                    String message = String.format(getString(R.string.dialog_city_info) +
                                    getString(R.string.dialog_city_rank) +
                                    getString(R.string.dialog_city_temp),
                            city.name,
                            city.rank,
                            city.temperature);

                    OutputManager.displayDialog(getContext(), "City Data:", message);
                }
            }
        });

        this.vModel.getLvdError().observe(parent, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                OutputManager.logError(TAG, message);
                OutputManager.displayDialog(getContext(), "Something went wrong:", message);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTest2: {
                Navigation.findNavController(getView()).navigate(R.id.test2View);
                break;
            }
            case R.id.btnTest3: {
                Navigation.findNavController(getView()).navigate(R.id.test3View);
                break;
            }
            case R.id.btnLoadGivenData: {
                String givenData = this.vModel.getFixedJsonString(JsonData.paris);
                this.vModel.convertData(givenData);
                break;
            }
            case R.id.btnLoadFixedData: {
                String givenDataWithTemperature = this.vModel.getFixedJsonString(JsonData.parisWithTemp);
                this.vModel.convertData(givenDataWithTemperature);
                break;
            }

        }
    }
}


