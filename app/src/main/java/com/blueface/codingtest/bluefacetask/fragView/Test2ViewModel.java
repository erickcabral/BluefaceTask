package com.blueface.codingtest.bluefacetask.fragView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blueface.codingtest.bluefacetask.supportClasses.ContCityInfoBinderModel;
import com.blueface.codingtest.bluefacetask.supportClasses.models.City;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class Test2ViewModel extends ViewModel {

    private final MutableLiveData<City> lvdCity = new MutableLiveData<>();
    private final MutableLiveData<Boolean> lvdCityInfoIsValid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> lvdIsAttributeValid = new MutableLiveData<>();

    private final MutableLiveData<ContCityInfoBinderModel> lvdContNameBinderModel = new MutableLiveData<>();
    private final MutableLiveData<ContCityInfoBinderModel> lvdContRankBinderModel = new MutableLiveData<>();

    @Inject
    public Test2ViewModel() {

    }

    public void createCityObject(String name, String rank) {
        City city = new City();
        city.name = name;
        city.rank = Integer.parseInt(rank);
        this.lvdCity.postValue(city);
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

    // ============== GETTERS ================ //
    public LiveData<ContCityInfoBinderModel> getContNameBinderModel() {
        return this.lvdContNameBinderModel;
    }

    public LiveData<ContCityInfoBinderModel> getContRankBinderModel() {
        return this.lvdContRankBinderModel;
    }

    public void setContRankBinderModel(String label, String info) {
        this.lvdContRankBinderModel.postValue(new ContCityInfoBinderModel(label, info));
    }
}
