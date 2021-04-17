package com.blueface.codingtest.bluefacetask.supportClasses;

public class ContCityInfoBinderModel {
    private final String label;
    private String info;

    public ContCityInfoBinderModel(String label, String info) {
        this.label = label;
        this.info = info;
    }



    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLabel() {
        return label;
    }
}
