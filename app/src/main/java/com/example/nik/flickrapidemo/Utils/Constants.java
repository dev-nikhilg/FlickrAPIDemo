package com.example.nik.flickrapidemo.Utils;

public class Constants {
    // network call read timeout constant
    public static final int READ_TIMEOUT_CONSTANT = 5000;
    // network call connect timeout constant
    public static final int CONNECT_TIMEOUT_CONSTANT = 5000;

    // network call url constants
    public static final String SEARCH_IMAGES_BASE_URL = "https://api.flickr.com/services/rest";
    public static final String FLICKR_API_METHOD_CONSTANT = "method";
    public static final String FLICKR_API_METHOD_VALUE = "flickr.photos.search";
    public static final String FLICKR_API_KEY_CONSTANT = "api_key";
    public static final String FLICKR_API_KEY_VALUE = "3e7cc266ae2b0e0d78e279ce8e361736";
    public static final String FLICKR_API_RESPONSE_FORMAT_CONSTANT = "format";
    public static final String FLICKR_API_RESPONSE_FORMAT_VALUE = "json";
    public static final String FLICKR_API_NOJSONCALLBACK_CONSTANT = "nojsoncallback";
    public static final String FLICKR_API_NOJSONCALLBACK_VALUE = "1";
    public static final String FLICKR_API_SAFESEARCH_CONSTANT = "safe_search";
    public static final String FLICKR_API_SAFESEARCH_VALUE = "1";
    public static final String FLICKR_API_QUERYTEXT_CONSTANT = "text";
    public static final String FLICKR_API_PERPAGE_CONSTANT = "per_page";
    public static final String FLICKR_API_PERPAGE_VALUE = "50";
    public static final String FLICKR_API_PAGE_CONSTANT = "page";

    // network call response status constants
    public static final int NETWORK_CALL_SUCCESS = 1;
    public static final int NETWORK_CALL_ERROR = -1;
    public static final int NETWORK_CALL_INPROGRESS = 0;
}
