package com.example.nik.flickrapidemo.Activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.nik.flickrapidemo.MyApplication;
import com.example.nik.flickrapidemo.Networking.NetworkCallback;
import com.example.nik.flickrapidemo.Networking.NetworkRawResult;
import com.example.nik.flickrapidemo.Networking.NetworkUtils;
import com.example.nik.flickrapidemo.Utils.CommonFunctionsUtil;
import com.example.nik.flickrapidemo.Utils.Constants;
import com.example.nik.flickrapidemo.data.ImageResponseDto;
import com.example.nik.flickrapidemo.data.NetworkResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivityRepository {

    private Context applicationContext;
    private NetworkUtils networkUtils;

    private MutableLiveData<NetworkResponse<ImageResponseDto>> imagesLiveData;

    private String query;
    private int currentPageNumber;
    private ImageResponseDto responseDto;

    public void init(Context context) {
        applicationContext = context;
        networkUtils = ((MyApplication) applicationContext).getNetworkUtils();

        imagesLiveData = new MutableLiveData<NetworkResponse<ImageResponseDto>>();
    }

    public LiveData<NetworkResponse<ImageResponseDto>> getImagesFromNetwork(String searchQuery) {
        if (!CommonFunctionsUtil.isValidString(searchQuery)) {
            return null;
        }

        // init variables for a new search
        query = searchQuery;
        currentPageNumber = 1;
        responseDto = new ImageResponseDto(new ArrayList<>());

        // makeNetworkCall
        makeNetworkCall();

        // set loading status
        imagesLiveData.setValue(NetworkResponse.loading(responseDto));

        return imagesLiveData;
    }

    private void makeNetworkCall() {
        String url = new StringBuilder(Constants.SEARCH_IMAGES_BASE_URL)
                .append("?").append(Constants.FLICKR_API_METHOD_CONSTANT).append("=").append(Constants.FLICKR_API_METHOD_VALUE)
                .append("&").append(Constants.FLICKR_API_KEY_CONSTANT).append("=").append(Constants.FLICKR_API_KEY_VALUE)
                .append("&").append(Constants.FLICKR_API_RESPONSE_FORMAT_CONSTANT).append("=").append(Constants.FLICKR_API_RESPONSE_FORMAT_VALUE)
                .append("&").append(Constants.FLICKR_API_NOJSONCALLBACK_CONSTANT).append("=").append(Constants.FLICKR_API_NOJSONCALLBACK_VALUE)
                .append("&").append(Constants.FLICKR_API_SAFESEARCH_CONSTANT).append("=").append(Constants.FLICKR_API_SAFESEARCH_VALUE)
                .append("&").append(Constants.FLICKR_API_QUERYTEXT_CONSTANT).append("=").append(query)
                .append("&").append(Constants.FLICKR_API_PERPAGE_CONSTANT).append("=").append(Constants.FLICKR_API_PERPAGE_VALUE)
                .append("&").append(Constants.FLICKR_API_PAGE_CONSTANT).append("=").append(currentPageNumber)
                .toString();

        networkUtils.makeNetworkCall(url, new NetworkCallback() {
            @Override
            public void startedNetworkCall() {
                Log.d("NetworkResponse", "URL is - " + url);
            }

            @Override
            public void finishedNetworkCall(NetworkRawResult result) {
                if (CommonFunctionsUtil.isValidString(result.result)) {
                    try {
                        List<String> images = parseNetworkResponse(result.result);
                        responseDto.addItemsToList(images);
                        imagesLiveData.setValue(NetworkResponse.success(responseDto));
                    } catch (JSONException e) {
                        imagesLiveData.setValue(NetworkResponse.error(e.getMessage(), null));
                    }
                } else {
                    imagesLiveData.setValue(NetworkResponse.error(result.error, null));
                }
            }
        });
    }

    private List<String> parseNetworkResponse(String response) throws JSONException {
        List<String> imagesList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONObject photosJSONObject = jsonObject.getJSONObject("photos");
        if (photosJSONObject != null) {
            currentPageNumber = photosJSONObject.getInt("page");
            JSONArray photosArray = photosJSONObject.getJSONArray("photo");
            if (photosArray != null || photosArray.length() > 0) {
                for (int i=0; i<photosArray.length(); i++) {
                    JSONObject photo = photosArray.getJSONObject(i);
                    String imageURL = new StringBuilder("http://farm")
                            .append(photo.get("farm"))
                            .append(".static.flickr.com/")
                            .append(photo.get("server"))
                            .append("/")
                            .append(photo.get("id"))
                            .append("_")
                            .append(photo.get("secret"))
                            .append(".jpg")
                            .toString();
                    imagesList.add(imageURL);
                }
            }
        }
        return imagesList;
    }
}
