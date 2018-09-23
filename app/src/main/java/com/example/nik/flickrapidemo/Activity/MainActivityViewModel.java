package com.example.nik.flickrapidemo.Activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.nik.flickrapidemo.R;
import com.example.nik.flickrapidemo.data.ImageResponseDto;
import com.example.nik.flickrapidemo.data.NetworkResponse;

public class MainActivityViewModel extends ViewModel {

    private Context context;
    private MainActivityRepository repository;

    private MutableLiveData<String> titleLiveData;
    private MutableLiveData<String> searchQueryLiveData;

    private LiveData<NetworkResponse<ImageResponseDto>> imagesLiveData;

    public void init(Context applicationContext) {
        if (repository != null) {
            return;
        }

        this.context = applicationContext;

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

        imagesLiveData = Transformations.switchMap(
                searchQueryLiveData, searchQuery -> repository.getImagesFromNetwork(searchQuery)
        );
    }

    public LiveData<String> getTitle() {
        return titleLiveData;
    }

    public LiveData<String> getLastSearchQuery() {
        return searchQueryLiveData;
    }

    public LiveData<NetworkResponse<ImageResponseDto>> getImages() {
        return imagesLiveData;
    }

    public void searchImages(String query) {
        titleLiveData.setValue(query);
        searchQueryLiveData.setValue(query);
    }

    public void serachMoreImages() {
        repository.getMoreImages();
    }
}
