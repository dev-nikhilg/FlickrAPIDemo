package com.example.nik.flickrapidemo.Activity;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

public class MainActivityViewModel extends ViewModel {

    private MainActivityRepository repository;

    public void init(Context applicationContext) {
        if (repository != null) {
            return;
        }

        /**
         * init repository
         */
        repository = new MainActivityRepository();
        repository.init(applicationContext);
    }
}
