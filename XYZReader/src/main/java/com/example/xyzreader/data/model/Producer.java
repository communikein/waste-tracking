package com.example.xyzreader.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;


public class Producer implements Parcelable {

    @SerializedName(BlockEntry.COLUMN_PRODUCER_ID)
    @PrimaryKey
    @ColumnInfo(index = true, name = BlockEntry.COLUMN_PRODUCER_ID)
    private String id;

    @SerializedName(BlockEntry.COLUMN_PRODUCER_NAME)
    @ColumnInfo(name = BlockEntry.COLUMN_PRODUCER_NAME)
    private String name;

    @SerializedName(BlockEntry.COLUMN_PRODUCER_SCORE)
    @ColumnInfo(name = BlockEntry.COLUMN_PRODUCER_SCORE)
    private double score;

    @SerializedName(BlockEntry.COLUMN_PRODUCER_LOCATION)
    @ColumnInfo(name = BlockEntry.COLUMN_PRODUCER_LOCATION)
    private String location;

    @SerializedName(BlockEntry.COLUMN_PRODUCER_FAMILY_SIZE)
    @ColumnInfo(name = BlockEntry.COLUMN_PRODUCER_FAMILY_SIZE)
    private int familySize;


    public Producer(String id, String name, double score, String location, int familySize) {
        setId(id);
        setName(name);
        setScore(score);
        setLocation(location);
        setFamilySize(familySize);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFamilySize() {
        return familySize;
    }

    public void setFamilySize(int familySize) {
        this.familySize = familySize;
    }



    @Ignore
    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Producer)) return false;

        Producer entry = (Producer) obj;
        return entry.getId().equals(this.getId());
    }


    public static Producer fromParcel(Parcel origin) {
        String id = origin.readString();
        String name = origin.readString();
        double score = origin.readDouble();
        String location = origin.readString();
        int familySize = origin.readInt();

        return new Producer(id, name, score, location, familySize);
    }

    public static Producer fromContentValues(ContentValues origin) {
        String id = origin.getAsString(BlockEntry.COLUMN_PRODUCER_ID);
        String name = origin.getAsString(BlockEntry.COLUMN_PRODUCER_NAME);
        double score = origin.getAsDouble(BlockEntry.COLUMN_PRODUCER_SCORE);
        String location = origin.getAsString(BlockEntry.COLUMN_PRODUCER_LOCATION);
        int familySize = origin.getAsInteger(BlockEntry.COLUMN_PRODUCER_FAMILY_SIZE);

        return new Producer(id, name, score, location, familySize);
    }

    public static Producer fromJson(JsonObject origin) {
        return new Gson().fromJson(origin, Producer.class);
    }

    public static ContentValues toContentValues(Producer producer) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BlockEntry.COLUMN_PRODUCER_ID, producer.getId());
        contentValues.put(BlockEntry.COLUMN_PRODUCER_NAME, producer.getName());
        contentValues.put(BlockEntry.COLUMN_PRODUCER_SCORE, producer.getScore());
        contentValues.put(BlockEntry.COLUMN_PRODUCER_LOCATION, producer.getLocation());
        contentValues.put(BlockEntry.COLUMN_PRODUCER_FAMILY_SIZE, producer.getFamilySize());

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
        dest.writeString(getName());
        dest.writeDouble(getScore());
        dest.writeString(getLocation());
        dest.writeInt(getFamilySize());
    }

    public static final Parcelable.Creator<Producer> CREATOR = new Parcelable.Creator<Producer>() {

        public Producer createFromParcel(Parcel origin) {
            return Producer.fromParcel(origin);
        }

        public Producer[] newArray(int size) {
            return new Producer[size];
        }
    };

}
