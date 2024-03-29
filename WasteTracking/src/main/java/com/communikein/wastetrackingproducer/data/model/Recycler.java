package com.communikein.wastetrackingproducer.data.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * This class represents the Recycler Facility object. A Recycler facility is a factory where the
 * Waste is recycled into reusable material.
 */
public class Recycler implements Parcelable {

    @SerializedName(BlockEntry.COLUMN_RECYCLE_ID)
    private String id;


    public Recycler(String id) {
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



    public static Recycler fromParcel(Parcel origin) {
        String id = origin.readString();

        return new Recycler(id);
    }

    public static Recycler fromContentValues(ContentValues origin) {
        String id = origin.getAsString(BlockEntry.COLUMN_RECYCLE_ID);

        return new Recycler(id);
    }

    public static Recycler fromJson(JsonObject origin) {
        return new Gson().fromJson(origin, Recycler.class);
    }

    public static ContentValues toContentValues(ChangeOwnership origin) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BlockEntry.COLUMN_RECYCLE_ID, origin.getPrevWasteID());

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

    public static final Creator<Recycler> CREATOR = new Creator<Recycler>() {

        public Recycler createFromParcel(Parcel origin) {
            return Recycler.fromParcel(origin);
        }

        public Recycler[] newArray(int size) {
            return new Recycler[size];
        }
    };

}