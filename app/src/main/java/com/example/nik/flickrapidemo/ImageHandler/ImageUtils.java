package com.example.nik.flickrapidemo.ImageHandler;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.nik.flickrapidemo.Utils.CommonFunctionsUtil;

import java.util.HashMap;
import java.util.Map;

public class ImageUtils implements DownloadCallback {
    private static ImageUtils instance;
    private Context context;
    private DownloadImageTask networkTask;

    private Map<String, View> urlToViewMap;

    private ImageUtils(Context context) {
        this.context = context;
    }

    public static ImageUtils getInstance(Context context) {
        if (instance == null) {
            instance = new ImageUtils(context);
            instance.urlToViewMap = new HashMap<>();
        }
        return instance;
    }

    public void getImage(String url, View view) {
        if (CommonFunctionsUtil.isNetworkConnectivityAvailable(context)) {
            networkTask = new DownloadImageTask(this);
            networkTask.execute(url);
            urlToViewMap.put(url, view);
        }
    }

    @Override
    public void startedDownloading() {

    }

    @Override
    public void finishedDownloading(DownloadImageResult result) {
        String downloadURL = result.url;
        View view = urlToViewMap.get(downloadURL);
        if (view != null && result.result != null) {
            ((ImageView) view).setImageBitmap(result.result);
        }
        urlToViewMap.remove(downloadURL);
    }
}
