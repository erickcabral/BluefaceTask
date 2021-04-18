package com.blueface.codingtest.bluefacetask.fragView.test1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.blueface.codingtest.bluefacetask.R;
import com.blueface.codingtest.bluefacetask.databinding.ViewTest1Binding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Test1View extends Fragment {


    private ViewTest1Binding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.view_test_1, container, false);

        return this.binding.getRoot();
    }
}


