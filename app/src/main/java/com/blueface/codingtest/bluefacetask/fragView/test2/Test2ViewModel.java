package com.blueface.codingtest.bluefacetask.fragView.test2;

import android.location.Address;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blueface.codingtest.bluefacetask.supportClasses.ContCityInfoBinderModel;
import com.blueface.codingtest.bluefacetask.supportClasses.models.City;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class Test2ViewModel extends ViewModel {

    private final MutableLiveData<Address> lvdAddress = new MutableLiveData<>();
    private final MutableLiveData<City> lvdCity = new MutableLiveData<>();
    private final MutableLiveData<Boolean> lvdIsAttributeValid = new MutableLiveData<>();

    private final MutableLiveData<ContCityInfoBinderModel> lvdContNameBinderModel = new MutableLiveData<>();
    private final MutableLiveData<ContCityInfoBinderModel> lvdContRankBinderModel = new MutableLiveData<>();

    private final MutableLiveData<ContCityInfoBinderModel> lvdConLongitude = new MutableLiveData<>();
    private final MutableLiveData<ContCityInfoBinderModel> lvdConLatitude = new MutableLiveData<>();
    private final MutableLiveData<ContCityInfoBinderModel> lvdConCountry = new MutableLiveData<>();
    private final MutableLiveData<ContCityInfoBinderModel> lvdConLocation = new MutableLiveData<>();

    @Inject
    public Test2ViewModel() {

    }

    public void createCityObject(String name, String rank, Address address) {
        City city = new City();
        city.name = name;
        city.rank = Integer.parseInt(rank);
        this.lvdCity.postValue(city);
        this.lvdAddress.postValue(address);
    }

    public LiveData<City> getLvdCity() {
        return this.lvdCity;
    }

    public LiveData<Boolean> lvdIsAttributeValid() {
        return this.lvdIsAttributeValid;
    }

    public void isAttributeValid(String attr) {
        boolean isValid = true;
        if (attr == null) {
            isValid = false;
        } else if (attr.isEmpty()) {
            isValid = false;
        }
        this.lvdIsAttributeValid.postValue(isValid);
    }

    public void setContNameBinderModel(String label, String info) {
        this.lvdContNameBinderModel.postValue(new ContCityInfoBinderModel(label, info));
    }

    public void setContRankBinderModel(String label, String info) {
        this.lvdContRankBinderModel.postValue(new ContCityInfoBinderModel(label, info));
    }

    public void setContLongitudeBinderModel(String label, String info) {
        this.lvdConLongitude.postValue(new ContCityInfoBinderModel(label, info));
    }

    public void setContLatitudeBinderModel(String label, String info) {
        this.lvdConLatitude.postValue(new ContCityInfoBinderModel(label, info));
    }

    public void setContDeviceLocation(String label, String info) {
        this.lvdConLocation.postValue(new ContCityInfoBinderModel(label, info));
    }

    public void setContCountryBinderModel(String label, String info) {
        this.lvdConCountry.postValue(new ContCityInfoBinderModel(label,info));
    }

    // ============== GETTERS ================ //
    public LiveData<ContCityInfoBinderModel> getContNameBinderModel() {
        return this.lvdContNameBinderModel;
    }

    public LiveData<ContCityInfoBinderModel> getLvdContRankBinderModel() {
        return this.lvdContRankBinderModel;
    }


    public LiveData<Address> getLvdDeviceAddress() {
        return this.lvdAddress;
    }


    public LiveData<ContCityInfoBinderModel> getLvdContLongitude() {
        return this.lvdConLongitude;
    }


    public LiveData<ContCityInfoBinderModel> getLvdContLatitude() {
        return this.lvdConLatitude;
    }

    public LiveData<ContCityInfoBinderModel> getLvdLocation() {
        return this.lvdConLocation;
    }

    public LiveData<ContCityInfoBinderModel> getLvdContCountry() {
        return this.lvdConCountry;
    }
}
