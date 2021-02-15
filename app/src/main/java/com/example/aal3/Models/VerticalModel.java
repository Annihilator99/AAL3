package com.example.aal3.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VerticalModel {

    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("design_type")
    String designType;

    @SerializedName("is_scrollable")
    boolean is_scrollable;

    @SerializedName("height")
    int height;

    @SerializedName("cards")
    ArrayList<HorizontalModel> cards;

    public ArrayList<HorizontalModel> getCards() {
        return cards;
    }

    public void setCards(ArrayList<HorizontalModel> cards) {
        this.cards = cards;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignType() {
        return designType;
    }

    public void setDesignType(String designType) {
        this.designType = designType;
    }

    public boolean isIs_scrollable() {
        return is_scrollable;
    }

    public void setIs_scrollable(boolean is_scrollable) {
        this.is_scrollable = is_scrollable;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
