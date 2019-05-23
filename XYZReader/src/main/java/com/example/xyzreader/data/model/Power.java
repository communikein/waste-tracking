package com.example.xyzreader.data.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;


public class Power extends Block implements Parcelable {

    @SerializedName(BlockEntry.COLUMN_POWER_ID)
    private String id;


    public Power(String id) {
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



    public static Power fromParcel(Parcel origin) {
        String id = origin.readString();

        return new Power(id);
    }

    public static Power fromContentValues(ContentValues origin) {
        String id = origin.getAsString(BlockEntry.COLUMN_POWER_ID);

        return new Power(id);
    }

    public static ContentValues writeToContentValues(ChangeOwnership origin) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BlockEntry.COLUMN_POWER_ID, origin.getPrevWasteID());

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

    public static final Parcelable.Creator<Power> CREATOR = new Parcelable.Creator<Power>() {

        public Power createFromParcel(Parcel origin) {
            return Power.fromParcel(origin);
        }

        public Power[] newArray(int size) {
            return new Power[size];
        }
    };

}