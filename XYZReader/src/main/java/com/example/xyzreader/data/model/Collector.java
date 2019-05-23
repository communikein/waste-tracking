package com.example.xyzreader.data.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;


public class Collector extends Block implements Parcelable {

    @SerializedName(BlockEntry.COLUMN_COLLECTOR_ID)
    private String id;


    public Collector(String id) {
        super(null);

        setId(id);

        super.setJson(this.getJson());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof  Collector)) return false;

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

    public static ContentValues writeToContentValues(ChangeOwnership origin) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BlockEntry.COLUMN_COLLECTOR_ID, origin.getPrevWasteID());

        return contentValues;
    }

    public JsonObject toJSON() {
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

    public static final Parcelable.Creator<Collector> CREATOR = new Parcelable.Creator<Collector>() {

        public Collector createFromParcel(Parcel origin) {
            return Collector.fromParcel(origin);
        }

        public Collector[] newArray(int size) {
            return new Collector[size];
        }
    };

}
