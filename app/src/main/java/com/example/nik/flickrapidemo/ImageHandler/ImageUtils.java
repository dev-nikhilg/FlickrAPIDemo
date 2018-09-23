package com.example.nik.flickrapidemo.ImageHandler;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.view.View;

import com.example.nik.flickrapidemo.Utils.CommonFunctionsUtil;

import java.util.HashMap;
import java.util.Map;

public class ImageUtils implements DownloadCallback {
    private static ImageUtils instance;
    private Context context;
    private DownloadImageTask networkTask;

    private Map<View, ImageViewToURLAndCallbackMappingObject> viewToURLAndCallbackMap;
    private Map<String, View> urlToViewMap;

    private LruCache<String, Bitmap> mMemoryCache;

    private ImageUtils(Context context) {
        this.context = context;
        initCache();
    }

    private void initCache() {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/4th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 4;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
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
        if (mMemoryCache.get(url) != null) {
            callback.onBitmapReceived(mMemoryCache.get(url));
            return;
        }
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
        mMemoryCache.put(downloadURL, result.result);
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
