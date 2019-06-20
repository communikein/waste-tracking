package com.communikein.wastetrackingproducer.data.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * This class represents the Collector. The Collector is the entity that collects the waste from
 * the Producer and brings it to the Treatment Facility.
 */
public class Collector implements Parcelable {

    @SerializedName(BlockEntry.COLUMN_COLLECTOR_ID)
    private String id;


    public Collector(String id) {
        setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Collector)) return false;

        Collector entry = (Collector) obj;
        return entry.getId().equals(this.getId());
    }



    public static Collector fromParcel(Parcel origin) {
        String id = origin.readString();

        return new Collector(id);
    }

    public static Collector fromContentValues(ContentValues origin) {
        String id = origin.getAsString(BlockEntry.COLUMN_COLLECTOR_ID);

        return new Collector(id);
    }

    public static Collector fromJson(JsonObject origin) {
        return new Gson().fromJson(origin, Collector.class);
    }

    public static ContentValues toContentValues(ChangeOwnership origin) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BlockEntry.COLUMN_COLLECTOR_ID, origin.getPrevWasteID());

        return contentValues;
    }

    public JsonObject toJson() {
        return new Gson().fromJson(new Gson().toJson(this), JsonObject.class);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
    }

    public static final Creator<Collector> CREATOR = new Creator<Collector>() {

        public Collector createFromParcel(Parcel origin) {
            return Collector.fromParcel(origin);
        }

        public Collector[] newArray(int size) {
            return new Collector[size];
        }
    };

}
