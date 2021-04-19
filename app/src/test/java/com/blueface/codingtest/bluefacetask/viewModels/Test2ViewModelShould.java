package com.blueface.codingtest.bluefacetask.viewModels;

import android.location.Address;

import com.blueface.codingtest.bluefacetask.baseTestUtils.BaseTestUtils;
import com.blueface.codingtest.bluefacetask.fragView.test2.Test2ViewModel;
import com.blueface.codingtest.bluefacetask.supportClasses.ContCityInfoBinderModel;
import com.blueface.codingtest.bluefacetask.supportClasses.models.City;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test2ViewModelShould extends BaseTestUtils {

    private Test2ViewModel viewModel;
    @Mock
    Address address;

    @Before
    public void setup() {
        this.viewModel = new Test2ViewModel();
    }

    @Test
    public void verify_submitted_attributes_and_set_live_data_TRUE_when_attributes_VALID() {
        String name = "Paris";
        String rank = "7";
        this.viewModel.isAttributeValid(name);
        assertTrue(this.viewModel.lvdIsAttributeValid().getValue());
        this.viewModel.isAttributeValid(rank);
        assertTrue(this.viewModel.lvdIsAttributeValid().getValue());
    }

    @Test
    public void verify_submitted_attributes_and_set_live_data_FALSE_when_attributes_NULL() {
        String name = null;
        String rank = null;
        this.viewModel.isAttributeValid(name);
        assertFalse(this.viewModel.lvdIsAttributeValid().getValue());
        this.viewModel.isAttributeValid(rank);
        assertFalse(this.viewModel.lvdIsAttributeValid().getValue());
    }

    @Test
    public void verify_submitted_attributes_and_set_live_data_FALSE_when_attributes_EMPTY() {
        String name = "";
        String rank = "";

        this.viewModel.isAttributeValid(name);
        assertFalse(this.viewModel.lvdIsAttributeValid().getValue());

        this.viewModel.isAttributeValid(rank);
        assertFalse(this.viewModel.lvdIsAttributeValid().getValue());
    }

    @Test
    public void create_city_object_and_update_city_live_data_address_live_data() {
        String name = "Paris";
        String rank = "7";
        this.viewModel.createCityObject(name, rank);

        City city = this.viewModel.getLvdCity().getValue();
        assertEquals("Paris", city.name);
        assertEquals(7, city.rank);
    }

    @Test
    public void set_device_location() {
        String location = "Place - Country";
        String latitude = "12.00009";
        String longitude = "-32.9999";

        this.viewModel.setDeviceLocation(location);
        String liveLocation = this.viewModel.getLvdDeviceLocation().getValue();
        assertEquals(location, liveLocation);
    }

    @Test
    public void set_container_name_binder_model() {
        String label = "City Name";
        String info = "Paris";

        this.viewModel.setContNameBinderModel(label, info);
        ContCityInfoBinderModel value = this.viewModel.getContNameBinderModel().getValue();
        assertEquals("City Name", value.getLabel());
        assertEquals("Paris", value.getInfo());
    }

    @Test
    public void set_container_rank_binder_model() {
        String label = "City Rank";
        String info = "999";

        this.viewModel.setContRankBinderModel(label, info);
        ContCityInfoBinderModel value = this.viewModel.getLvdContRankBinderModel().getValue();
        assertEquals("City Rank", value.getLabel());
        assertEquals("999", value.getInfo());
    }

    @Test
    public void set_container_device_latitude_binder_model() {
        String label = "Latitude";
        String info = "12.4223423";

        this.viewModel.setContLatitudeBinderModel(label, info);
        ContCityInfoBinderModel value = this.viewModel.getLvdContLatitude().getValue();
        assertEquals("Latitude", value.getLabel());
        assertEquals("12.4223423", value.getInfo());
    }

    @Test
    public void set_container_device_longitude_binder_model() {
        String label = "Longitude";
        String info = "32.44567";

        this.viewModel.setContLongitudeBinderModel(label, info);
        ContCityInfoBinderModel value = this.viewModel.getLvdContLongitude().getValue();
        assertEquals("Longitude", value.getLabel());
        assertEquals("32.44567", value.getInfo());
    }
}
