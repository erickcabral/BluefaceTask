package com.blueface.codingtest.bluefacetask.fragView;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.blueface.codingtest.bluefacetask.R;
import com.blueface.codingtest.bluefacetask.databinding.ViewTest2Binding;

public class Test2View extends Fragment {

    private ViewTest2Binding binder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.binder = DataBindingUtil.inflate(inflater, R.layout.view_test_2, null, false);

        return this.binder.getRoot();
    }
}