package com.example.aal3;

import okhttp3.Request;

public class OkHttpRequest {

    private static final String BASE_URL = "https://run.mocky.io/v3/73be2d3d-7ad6-49be-aa84-7c759ca8de51";

    public Request getResponse() {

        return new Request.Builder()
                .url(BASE_URL)
                .method("GET", null)
                .build();
    }
}
