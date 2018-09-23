package com.example.nik.flickrapidemo.Networking;

import android.content.Context;

import com.example.nik.flickrapidemo.R;
import com.example.nik.flickrapidemo.Utils.CommonFunctionsUtil;

public class NetworkUtils {
    private static NetworkUtils instance;
    private Context context;
    private NetworkTask networkTask;

    private NetworkUtils(Context context) {
        this.context = context;
    }

    public static NetworkUtils getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkUtils(context);
        }
        return instance;
    }

    public void makeNetworkCall(String url, NetworkCallback callback) {
        if (CommonFunctionsUtil.isNetworkConnectivityAvailable(context)) {
            cancelNetworkCall();
            networkTask = new NetworkTask(callback);
            networkTask.execute(url);
        } else {
            callback.finishedNetworkCall(
                    NetworkRawResult.callFailure(context.getString(R.string.no_internet_connection))
            );
        }
    }

    public void cancelNetworkCall() {
        if (networkTask != null) {
            networkTask.cancel(true);
        }
    }
}
