package com.blueface.codingtest.bluefacetask.supportClasses.di;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AppModule {


    @Provides
    @Singleton
    public static Gson providesGson(){
        return new Gson();
    }
}
