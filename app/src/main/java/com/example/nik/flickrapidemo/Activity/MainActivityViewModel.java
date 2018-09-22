package com.example.nik.flickrapidemo.Activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.nik.flickrapidemo.R;

public class MainActivityViewModel extends ViewModel {

    private MainActivityRepository repository;

    private MutableLiveData<String> titleLiveData;
    private MutableLiveData<String> searchQueryLiveData;

    public void init(Context applicationContext) {
        if (repository != null) {
            return;
        }

        /**
         * init repository
         */
        repository = new MainActivityRepository();
        repository.init(applicationContext);

        /**
         * init live data objects
         */
        titleLiveData = new MutableLiveData<>();
        titleLiveData.setValue(applicationContext.getString(R.string.app_name));
        searchQueryLiveData = new MutableLiveData<>();
        searchQueryLiveData.setValue("");
    }

    public LiveData<String> getTitle() {
        return titleLiveData;
    }

    public LiveData<String> getLastSearchQuery() {
        return searchQueryLiveData;
    }

    public void searchImages(String query) {
        titleLiveData.setValue(query);
        searchQueryLiveData.setValue(query);
    }
}
