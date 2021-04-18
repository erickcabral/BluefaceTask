package com.blueface.codingtest.bluefacetask.supportClasses.di;

import android.content.Context;

import com.blueface.codingtest.bluefacetask.R;
import com.blueface.codingtest.bluefacetask.supportClasses.weatherAPI.WeatherAPI;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AppModule {


    @Provides
    @Singleton
    public static Gson providesGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public static WeatherAPI providesWeatherAPI(@ApplicationContext Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        return retrofit.create(WeatherAPI.class);
    }

    @Provides
    @Singleton
    public static String providesApiKey(@ApplicationContext Context context) {
        return context.getString(R.string.api_key);
    }
}
