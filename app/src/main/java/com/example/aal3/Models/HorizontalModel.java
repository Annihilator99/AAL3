package com.example.aal3.Models;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

public class HorizontalModel {

    public static final int HC1 = 1;
    public static final int HC3 = 3;
    public static final int HC5 = 5;
    public static final int HC6 = 6;
    public static final int HC9 = 9;

    @SerializedName("name")
    String name;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("url")
    String url;

    @SerializedName("is_disabled")
    boolean is_disabled;

    @SerializedName("@bg_color")
    String bg_color;

    @SerializedName("icon")
    Image icon;

    @SerializedName("bg_image")
    Image bg_image;

    @SerializedName("cta")
    JsonArray cta;

    public HorizontalModel(String name, String title, String description, String url, boolean is_disabled, String bg_color) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.url = url;
        this.is_disabled = is_disabled;
        this.bg_color = bg_color;
    }

    public JsonArray getCta() {
        return cta;
    }

    public void setCta(JsonArray cta) {
        this.cta = cta;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public Image getBg_image() {
        return bg_image;
    }

    public void setBg_image(Image bg_image) {
        this.bg_image = bg_image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isIs_disabled() {
        return is_disabled;
    }

    public void setIs_disabled(boolean is_disabled) {
        this.is_disabled = is_disabled;
    }

    public String getBg_color() {
        return bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
