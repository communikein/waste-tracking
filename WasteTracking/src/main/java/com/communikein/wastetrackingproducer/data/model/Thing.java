package com.communikein.wastetrackingproducer.data.model;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Thing {

    private ArrayList<String> identities;
    private String owner;
    private boolean isValid;
    private JsonObject data;

    public Thing(ArrayList<String> identities, String owner, boolean isValid, JsonObject data) {
        this.identities = identities;
        this.owner = owner;
        this.isValid = isValid;
        this.data = data;
    }

    public ArrayList<String> getIdentities() {
        return identities;
    }

    public void setIdentities(ArrayList<String> identities) {
        this.identities = identities;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

}
