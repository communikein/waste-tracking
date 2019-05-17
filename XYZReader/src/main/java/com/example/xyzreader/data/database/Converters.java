package com.example.xyzreader.data.database;

import android.arch.persistence.room.TypeConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


    /*
    @TypeConverter
    public static String jsonToString(JSONObject origin) {
        return origin.toString();
    }

    @TypeConverter
    public static JSONObject stringToJson(String origin) {
        JSONObject dest;

        try {
            dest = new JSONObject(origin);
        } catch (JSONException e) {
            dest = new JSONObject();
        }

        return dest;
    }
    */
}

