package com.example.xyzreader.data.model;

import android.arch.persistence.room.Ignore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Block {

    @Ignore
    private JSONObject json;

    public Block(JSONObject origin) {
        this.json = origin;
    }

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }


    public static <T> ArrayList<T>  fromJSONArray(JSONArray array, Class<T> base) {
        ArrayList<T> result = new ArrayList<>();

        if (array != null) for (int i=0; i<array.length(); i++) {
            Block block = null;
            try {
                block = new Block(array.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (block != null)
                result.add(base.cast(block));
        }

        return result;
    }
}
