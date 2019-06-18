package com.communikein.wastetrackingproducer.data.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class TreatmentPlant implements Parcelable {

    @SerializedName(BlockEntry.COLUMN_TREATMENT_TYPE)
    private String id;


    public TreatmentPlant(String id) {
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
        if (! (obj instanceof TreatmentPlant)) return false;

        TreatmentPlant entry = (TreatmentPlant) obj;
        return entry.getId().equals(this.getId());
    }



    public static TreatmentPlant fromParcel(Parcel origin) {
        String id = origin.readString();

        return new TreatmentPlant(id);
    }

    public static TreatmentPlant fromContentValues(ContentValues origin) {
        String id = origin.getAsString(BlockEntry.COLUMN_TREATMENT_TYPE);

        return new TreatmentPlant(id);
    }

    public static TreatmentPlant fromJson(JsonObject origin) {
        return new Gson().fromJson(origin, TreatmentPlant.class);
    }

    public static ContentValues toContentValues(ChangeOwnership origin) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BlockEntry.COLUMN_TREATMENT_TYPE, origin.getPrevWasteID());

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

    public static final Creator<TreatmentPlant> CREATOR = new Creator<TreatmentPlant>() {

        public TreatmentPlant createFromParcel(Parcel origin) {
            return TreatmentPlant.fromParcel(origin);
        }

        public TreatmentPlant[] newArray(int size) {
            return new TreatmentPlant[size];
        }
    };

}
