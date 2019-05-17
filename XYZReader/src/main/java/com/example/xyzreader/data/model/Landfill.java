package com.example.xyzreader.data.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Landfill extends Block implements Parcelable {

    @SerializedName(BlockEntry.COLUMN_LANDFILL_ID)
    private String id;


    public Landfill(String id) {
        super(null);

        setId(id);

        super.setJson(this.toJSON());
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



    public static Landfill fromParcel(Parcel origin) {
        String id = origin.readString();

        return new Landfill(id);
    }

    public static Landfill fromContentValues(ContentValues origin) {
        String id = origin.getAsString(BlockEntry.COLUMN_LANDFILL_ID);

        return new Landfill(id);
    }

    public static ContentValues writeToContentValues(ChangeOwnership origin) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BlockEntry.COLUMN_LANDFILL_ID, origin.getPrevWasteID());

        return contentValues;
    }

    public JSONObject toJSON() {
        JSONObject dest = new JSONObject();

        try {
            dest.put(BlockEntry.COLUMN_LANDFILL_ID, getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dest;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
    }

    public static final Parcelable.Creator<Landfill> CREATOR = new Parcelable.Creator<Landfill>() {

        public Landfill createFromParcel(Parcel origin) {
            return Landfill.fromParcel(origin);
        }

        public Landfill[] newArray(int size) {
            return new Landfill[size];
        }
    };

}