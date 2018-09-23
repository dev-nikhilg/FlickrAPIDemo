package com.example.nik.flickrapidemo.ImageHandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, DownloadImageResult> {
    private DownloadCallback callback;

    public DownloadImageTask(DownloadCallback callback) {
        this.callback = callback;
    }

    /**
     * download image for the given url in params
     * @param urls
     * @return
     */
    protected DownloadImageResult doInBackground(String... urls) {
        callback.startedDownloading();
        DownloadImageResult result = null;
        if (!isCancelled() && urls != null && urls.length > 0) {
            String url= urls[0];
            try {
                InputStream stream = new java.net.URL(url).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                result = DownloadImageResult.downloadSuccess(bitmap, url);
            } catch (Exception e) {
                result = DownloadImageResult.downloadFailure(e.getMessage(), url);
            }


        }
        return result;
    }

    protected void onPostExecute(DownloadImageResult result) {
        callback.finishedDownloading(result);
    }
}