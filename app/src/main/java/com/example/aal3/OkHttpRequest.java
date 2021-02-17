package com.example.aal3;

import okhttp3.Request;

public class OkHttpRequest {

    private static final String BASE_URL = "https://run.mocky.io/v3/fefcfbeb-5c12-4722-94ad-b8f92caad1ad";

    public Request getResponse() {

        return new Request.Builder()
                .url(BASE_URL)
                .method("GET", null)
                .build();
    }
}
