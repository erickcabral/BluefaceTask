package com.blueface.codingtest.bluefacetask.viewModels;

import com.blueface.codingtest.bluefacetask.baseTestUtils.BaseTestUtils;
import com.blueface.codingtest.bluefacetask.fragView.Test2ViewModel;
import com.blueface.codingtest.bluefacetask.supportClasses.ContCityInfoBinderModel;
import com.blueface.codingtest.bluefacetask.supportClasses.models.City;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test2ViewModelShould extends BaseTestUtils {

    private Test2ViewModel viewModel;

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
    public void create_city_object_and_update_live_data() {
        String name = "Paris";
        String rank = "7";
        this.viewModel.createCityObject(name, rank);

        City city = this.viewModel.getLvdCity().getValue();
        assertEquals("Paris", city.name);
        assertEquals(7, city.rank);
    }

    @Test
    public void set_container_name_binder_model() {
        String label = "City Name";
        String info = "Paris";

        this.viewModel.setContNameBinderModel(label, info);
        ContCityInfoBinderModel contName = this.viewModel.getContNameBinderModel().getValue();
        assertEquals("City Name", contName.getLabel());
        assertEquals("Paris", contName.getInfo());
    }

    @Test
    public void set_container_rank_binder_model() {
        String label = "City Rank";
        String info = "999";

        this.viewModel.setContRankBinderModel(label, info);
        ContCityInfoBinderModel contName = this.viewModel.getContRankBinderModel().getValue();
        assertEquals("City Rank", contName.getLabel());
        assertEquals("999", contName.getInfo());
    }
}
