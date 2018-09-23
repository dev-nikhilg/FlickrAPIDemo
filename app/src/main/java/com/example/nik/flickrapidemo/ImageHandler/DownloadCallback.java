package com.example.nik.flickrapidemo.ImageHandler;

public interface DownloadCallback {
    void startedDownloading();
    void finishedDownloading(DownloadImageResult result);
}
