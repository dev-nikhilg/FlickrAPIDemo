package com.example.nik.flickrapidemo.ImageHandler;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DownloadImageResult {
    @Nullable
    public Bitmap result;
    @NonNull
    public String url;
    @Nullable
    public String error;

    private DownloadImageResult(@Nullable Bitmap result, @NonNull String url, @Nullable String error) {
        this.result = result;
        this.url = url;
        this.error = error;
    }

    public static DownloadImageResult downloadSuccess(@NonNull Bitmap result, @NonNull String url) {
        return new DownloadImageResult(result, url, null);
    }

    public static DownloadImageResult downloadFailure(@NonNull String error, @NonNull String url) {
        return new DownloadImageResult(null, url, error);
    }
}
