package com.example.xyzreader.data.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangeOwnership extends Block implements Parcelable {

    @SerializedName(BlockEntry.COLUMN_PREV_WASTE_ID)
    private String prevWasteID;

    @SerializedName(BlockEntry.COLUMN_PREV_WASTE_ID)
    private String prevOwnerID;

    @SerializedName(BlockEntry.COLUMN_PREV_WASTE_ID)
    private String nextWasteID;

    @SerializedName(BlockEntry.COLUMN_PREV_WASTE_ID)
    private String nextOwnerID;

    @SerializedName(BlockEntry.COLUMN_PREV_WASTE_ID)
    private String coordinates;

    @SerializedName(BlockEntry.COLUMN_PREV_WASTE_ID)
    private String date;


    public ChangeOwnership(String prevWasteID, String prevOwnerID, String nextWasteID, String nextOwnerID, String coordinates, String date) {
        super(null);

        setPrevWasteID(prevWasteID);
        setPrevOwnerID(prevOwnerID);
        setNextWasteID(nextWasteID);
        setNextOwnerID(nextOwnerID);
        setCoordinates(coordinates);
        setDate(date);

        super.setJson(this.toJSON());
    }

    public String getId() {
        return getPrevWasteID() + "-" + getPrevOwnerID() + "-" + getNextWasteID() + "-" + getNextOwnerID();
    }

    public String getPrevWasteID() {
        return prevWasteID;
    }

    public void setPrevWasteID(String prevWasteID) {
        this.prevWasteID = prevWasteID;
    }

    public String getPrevOwnerID() {
        return prevOwnerID;
    }

    public void setPrevOwnerID(String prevOwnerID) {
        this.prevOwnerID = prevOwnerID;
    }

    public String getNextWasteID() {
        return nextWasteID;
    }

    public void setNextWasteID(String nextWasteID) {
        this.nextWasteID = nextWasteID;
    }

    public String getNextOwnerID() {
        return nextOwnerID;
    }

    public void setNextOwnerID(String nextOwnerID) {
        this.nextOwnerID = nextOwnerID;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof  ChangeOwnership)) return false;

        ChangeOwnership entry = (ChangeOwnership) obj;
        return entry.getPrevOwnerID().equals(this.getPrevOwnerID()) &&
                entry.getNextOwnerID().equals(this.getNextOwnerID()) &&
                entry.getPrevWasteID().equals(this.getPrevWasteID()) &&
                entry.getNextWasteID().equals(this.getNextWasteID());
    }



    public static ChangeOwnership fromParcel(Parcel origin) {
        String prevWasteID = origin.readString();
        String prevOwnerID = origin.readString();
        String nextWasteID = origin.readString();
        String nextOwnerID = origin.readString();
        String coordinates = origin.readString();
        String date = origin.readString();

        return new ChangeOwnership(prevWasteID, prevOwnerID, nextWasteID, nextOwnerID, coordinates, date);
    }

    public static ChangeOwnership fromContentValues(ContentValues origin) {
        String prevWasteID = origin.getAsString(BlockEntry.COLUMN_PREV_WASTE_ID);
        String prevOwnerID = origin.getAsString(BlockEntry.COLUMN_PREV_OWNER_ID);
        String nextWasteID = origin.getAsString(BlockEntry.COLUMN_NEXT_WASTE_ID);
        String nextOwnerID = origin.getAsString(BlockEntry.COLUMN_NEXT_OWNER_ID);
        String coordinates = origin.getAsString(BlockEntry.COLUMN_COORDINATES);
        String date = origin.getAsString(BlockEntry.COLUMN_DATE);

        return new ChangeOwnership(prevWasteID, prevOwnerID, nextWasteID, nextOwnerID, coordinates, date);
    }

    public static ContentValues writeToContentValues(ChangeOwnership origin) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BlockEntry.COLUMN_PREV_WASTE_ID, origin.getPrevWasteID());
        contentValues.put(BlockEntry.COLUMN_PREV_OWNER_ID, origin.getPrevOwnerID());
        contentValues.put(BlockEntry.COLUMN_NEXT_WASTE_ID, origin.getNextWasteID());
        contentValues.put(BlockEntry.COLUMN_NEXT_OWNER_ID, origin.getNextOwnerID());
        contentValues.put(BlockEntry.COLUMN_COORDINATES, origin.getCoordinates());
        contentValues.put(BlockEntry.COLUMN_DATE, origin.getDate());

        return contentValues;
    }

    public JSONObject toJSON() {
        JSONObject dest = new JSONObject();

        try {
            dest.put(BlockEntry.COLUMN_PREV_WASTE_ID, getPrevWasteID());
            dest.put(BlockEntry.COLUMN_PREV_OWNER_ID, getPrevOwnerID());
            dest.put(BlockEntry.COLUMN_NEXT_WASTE_ID, getNextWasteID());
            dest.put(BlockEntry.COLUMN_NEXT_OWNER_ID, getNextOwnerID());
            dest.put(BlockEntry.COLUMN_COORDINATES, getCoordinates());
            dest.put(BlockEntry.COLUMN_DATE, getDate());
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
        dest.writeString(getPrevWasteID());
        dest.writeString(getPrevOwnerID());
        dest.writeString(getNextWasteID());
        dest.writeString(getNextOwnerID());
        dest.writeString(getCoordinates());
        dest.writeString(getDate());
    }

    public static final Parcelable.Creator<ChangeOwnership> CREATOR = new Parcelable.Creator<ChangeOwnership>() {

        public ChangeOwnership createFromParcel(Parcel origin) {
            return ChangeOwnership.fromParcel(origin);
        }

        public ChangeOwnership[] newArray(int size) {
            return new ChangeOwnership[size];
        }
    };

}
