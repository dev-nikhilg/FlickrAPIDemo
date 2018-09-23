package com.example.nik.flickrapidemo;

import android.app.Application;

import com.example.nik.flickrapidemo.ImageHandler.ImageUtils;
import com.example.nik.flickrapidemo.Networking.NetworkUtils;

public class MyApplication extends Application {

    private NetworkUtils networkUtils;

    private ImageUtils imageUtils;

    @Override
    public void onCreate() {
        super.onCreate();

        // initialise network utils
        networkUtils = NetworkUtils.getInstance(this);

        // initialise image utils
        imageUtils = ImageUtils.getInstance(this);
    }

    public NetworkUtils getNetworkUtils() {
        return networkUtils;
    }

    public ImageUtils getImageUtils() {
        return imageUtils;
    }
}
