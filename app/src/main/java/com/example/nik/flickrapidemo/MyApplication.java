package com.example.nik.flickrapidemo;

import android.app.Application;

import com.example.nik.flickrapidemo.Networking.NetworkUtils;

public class MyApplication extends Application {

    private NetworkUtils networkUtils;

    @Override
    public void onCreate() {
        super.onCreate();

        // initialise network utils
        networkUtils = NetworkUtils.getInstance(this);
    }

    public NetworkUtils getNetworkUtils() {
        return networkUtils;
    }
}
