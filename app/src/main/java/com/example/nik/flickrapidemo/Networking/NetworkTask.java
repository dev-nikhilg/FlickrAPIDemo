package com.example.nik.flickrapidemo.Networking;

import android.os.AsyncTask;

import com.example.nik.flickrapidemo.Utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetworkTask extends AsyncTask<String, Integer, NetworkRawResult> {

    private NetworkCallback callback;

    NetworkTask(NetworkCallback callback) {
        this.callback = callback;
    }

    /**
     * make network call for url received as param
     * for current use case, there is only GET calls
     * @param urls
     * @return
     */
    @Override
    protected NetworkRawResult doInBackground(String... urls) {
        // call network call started on callback
        callback.startedNetworkCall();
        NetworkRawResult result = null;
        if (!isCancelled() && urls != null && urls.length > 0) {
            String urlString = urls[0];
            try {
                URL url = new URL(urlString);
                String resultString = makeNetworkCall(url);
                if (resultString != null) {
                    result = NetworkRawResult.callSuccess(resultString);
                } else {
                    throw new IOException("No response received.");
                }
            } catch(Exception e) {
                result = NetworkRawResult.callFailure(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Updates the NetworkCallback with the result.
     */
    @Override
    protected void onPostExecute(NetworkRawResult result) {
        callback.finishedNetworkCall(result);
    }

    /**
     * Given a URL, sets up a connection and gets the HTTP response body from the server.
     * If the network request is successful, it returns the response body in String form. Otherwise,
     * it will throw an IOException.
     */
    private String makeNetworkCall(URL url) throws IOException {
        InputStream stream = null;
        HttpsURLConnection connection = null;
        String result = null;
        try {
            // open HttpsURLConnection
            connection = (HttpsURLConnection) url.openConnection();

            // set connect and read timeouts
            connection.setConnectTimeout(Constants.CONNECT_TIMEOUT_CONSTANT);
            connection.setReadTimeout(Constants.READ_TIMEOUT_CONSTANT);

            // For this use case, set HTTP method to GET.
            connection.setRequestMethod("GET");

            // Open communications link
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            // Retrieve the response body as an InputStream.
            stream = connection.getInputStream();
            if (stream != null) {
                // Converts Stream to String with max length of 10000.
                result = readStream(stream, 10000);
            }
        } finally {
            // Close Stream and disconnect HTTPS connection.
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    /**
     * Converts the contents of an InputStream to a String.
     */
    public String readStream(InputStream stream, int maxReadSize) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            if (readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        return buffer.toString();
    }
}