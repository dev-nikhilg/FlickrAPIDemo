package com.example.nik.flickrapidemo.Networking;

public interface NetworkCallback {
    void startedNetworkCall();
    void finishedNetworkCall(NetworkRawResult result);
}
