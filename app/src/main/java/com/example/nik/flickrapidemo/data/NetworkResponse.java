package com.example.nik.flickrapidemo.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.nik.flickrapidemo.Utils.Constants;

public class NetworkResponse<T> {

    @NonNull
    public final Integer status;
    @Nullable
    public final T data;
    @Nullable
    public final String message;

    private NetworkResponse(@NonNull Integer status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> NetworkResponse<T> success(@NonNull T data) {
        return new NetworkResponse<T>(Constants.NETWORK_CALL_SUCCESS, data, null);
    }

    public static <T> NetworkResponse<T> error(String msg, @Nullable T data) {
        return new NetworkResponse<>(Constants.NETWORK_CALL_ERROR, data, msg);
    }

    public static <T> NetworkResponse<T> loading(@Nullable T data) {
        return new NetworkResponse<>(Constants.NETWORK_CALL_INPROGRESS, data, null);
    }
}
