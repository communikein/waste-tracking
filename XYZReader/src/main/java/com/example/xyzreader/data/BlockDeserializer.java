package com.example.xyzreader.data;

import com.example.xyzreader.data.model.Block;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class BlockDeserializer implements JsonDeserializer<JsonObject> {

    @Override
    public JsonObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        return jsonObject;
    }
}
