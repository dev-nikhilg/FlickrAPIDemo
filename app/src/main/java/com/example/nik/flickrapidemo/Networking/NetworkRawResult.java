package com.example.nik.flickrapidemo.Networking;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class NetworkRawResult {
    @Nullable
    public String result;
    @Nullable
    public String error;

    private NetworkRawResult(@Nullable String result, @Nullable String error) {
        this.result = result;
        this.error = error;
    }

    public static NetworkRawResult callSuccess(@NonNull String result) {
        return new NetworkRawResult(result, null);
    }

    public static NetworkRawResult callFailure(@NonNull String error) {
        return new NetworkRawResult(null, error);
    }
}
