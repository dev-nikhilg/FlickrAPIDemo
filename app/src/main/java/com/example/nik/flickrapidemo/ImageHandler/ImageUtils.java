package com.example.nik.flickrapidemo.ImageHandler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.example.nik.flickrapidemo.Utils.CommonFunctionsUtil;

import java.util.HashMap;
import java.util.Map;

public class ImageUtils implements DownloadCallback {
    private static ImageUtils instance;
    private Context context;
    private DownloadImageTask networkTask;

    private Map<View, ImageViewToURLAndCallbackMappingObject> viewToURLAndCallbackMap;
    private Map<String, View> urlToViewMap;

    private ImageUtils(Context context) {
        this.context = context;
    }

    public static ImageUtils getInstance(Context context) {
        if (instance == null) {
            instance = new ImageUtils(context);
            instance.viewToURLAndCallbackMap = new HashMap<>();
            instance.urlToViewMap = new HashMap<>();
        }
        return instance;
    }

    public void getImage(String url, View view, ImageResponseCallback callback) {
        if (CommonFunctionsUtil.isNetworkConnectivityAvailable(context)) {
            networkTask = new DownloadImageTask(this);
            networkTask.execute(url);
            ImageViewToURLAndCallbackMappingObject obj =
                    new ImageViewToURLAndCallbackMappingObject(callback, url);
            viewToURLAndCallbackMap.put(view, obj);
            urlToViewMap.put(url, view);
        } else {
            callback.onBitmapReceived(null);
        }
    }

    @Override
    public void startedDownloading() {

    }

    @Override
    public void finishedDownloading(DownloadImageResult result) {
        String downloadURL = result.url;
        View view = urlToViewMap.get(downloadURL);
        if (view != null && viewToURLAndCallbackMap.get(view) != null) {
            String newURLForView = viewToURLAndCallbackMap.get(view).url;
            if (downloadURL.equals(newURLForView)) {
                viewToURLAndCallbackMap.get(view).callback.onBitmapReceived(result.result);
                viewToURLAndCallbackMap.remove(view);
            }
        }
        urlToViewMap.remove(downloadURL);
    }

    private class ImageViewToURLAndCallbackMappingObject {
        @NonNull
        public ImageResponseCallback callback;
        @NonNull
        public String url;

        public ImageViewToURLAndCallbackMappingObject(@NonNull ImageResponseCallback callback, @NonNull String url) {
            this.callback = callback;
            this.url = url;
        }
    }
}
