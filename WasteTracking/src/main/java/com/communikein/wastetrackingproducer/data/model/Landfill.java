package com.communikein.wastetrackingproducer.data.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;


public class Landfill implements Parcelable {

    @SerializedName(BlockEntry.COLUMN_LANDFILL_ID)
    private String id;


    public Landfill(String id) {
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



    public static Landfill fromParcel(Parcel origin) {
        String id = origin.readString();

        return new Landfill(id);
    }

    public static Landfill fromContentValues(ContentValues origin) {
        String id = origin.getAsString(BlockEntry.COLUMN_LANDFILL_ID);

        return new Landfill(id);
    }

    public static Landfill fromJson(JsonObject origin) {
        return new Gson().fromJson(origin, Landfill.class);
    }

    public static ContentValues toContentValues(ChangeOwnership origin) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BlockEntry.COLUMN_LANDFILL_ID, origin.getPrevWasteID());

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

    public static final Creator<Landfill> CREATOR = new Creator<Landfill>() {

        public Landfill createFromParcel(Parcel origin) {
            return Landfill.fromParcel(origin);
        }

        public Landfill[] newArray(int size) {
            return new Landfill[size];
        }
    };

}