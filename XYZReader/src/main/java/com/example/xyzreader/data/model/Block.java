package com.example.xyzreader.data.model;

import android.arch.persistence.room.Ignore;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Block {

    @Ignore
    private JsonObject json;

    public Block(JsonObject origin) {
        this.json = origin;
    }

    public JsonObject getJson() {
        return json;
    }

    public void setJson(JsonObject json) {
        this.json = json;
    }


    public static <T> ArrayList<T>  fromJSONArray(ArrayList<JsonObject> array, Class<T> base) {
        ArrayList<T> result = new ArrayList<>();

        if (array != null) for (JsonObject object : array) {
            Block block = new Block(object);
            result.add(base.cast(block));
        }

        return result;
    }
}
