package com.example.aal3.Models;

import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("image_url")
    String image_url;

    @SerializedName("image_type")
    String image_type;

    @SerializedName("aspect_ratio")
    String aspect_ratio;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    public String getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(String aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }
}
