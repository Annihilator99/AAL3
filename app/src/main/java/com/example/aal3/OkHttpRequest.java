package com.example.aal3;

import java.io.IOException;

import okhttp3.Request;

public class OkHttpRequest {

    public Request getResponse() throws IOException {

        return new Request.Builder()
                .url("https://run.mocky.io/v3/fefcfbeb-5c12-4722-94ad-b8f92caad1ad")
                .method("GET", null)
                .build();


    }
}
