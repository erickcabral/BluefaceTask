package com.blueface.codingtest.bluefacetask.androidTestUtils;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.blueface.codingtest.bluefacetask.MainActivity;

import org.junit.Rule;

public class MainActivityScenarioRule extends UIBaseTestUtils {

    @Rule
    public ActivityScenarioRule scenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

}
