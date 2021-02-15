package com.example.aal3.Models;

import com.google.gson.annotations.SerializedName;

public class CTA {

    @SerializedName("text")
    String text;

    @SerializedName("bg_color")
    String bg_color;

    @SerializedName("text_color")
    String text_color;

    @SerializedName("url_choice")
    String url_choice;

    @SerializedName("url")
    String action_url;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBg_color() {
        return bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public String getText_color() {
        return text_color;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }

    public String getUrl_choice() {
        return url_choice;
    }

    public void setUrl_choice(String url_choice) {
        this.url_choice = url_choice;
    }

    public String getAction_url() {
        return action_url;
    }

    public void setAction_url(String action_url) {
        this.action_url = action_url;
    }
}
