package com.example.xyzreader.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;


public class Producer extends Block implements Parcelable {

    @SerializedName(BlockEntry.COLUMN_PRODUCER_ID)
    @PrimaryKey
    @ColumnInfo(index = true, name = BlockEntry.COLUMN_PRODUCER_ID)
    private String id;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("score")
    @ColumnInfo(name = "score")
    private double score;

    @SerializedName("location")
    @ColumnInfo(name = "location")
    private String location;

    @SerializedName("family_size")
    @ColumnInfo(name = "family_size")
    private int familySize;


    public Producer(String id, String name, double score, String location, int familySize) {
        super(null);

        setId(id);
        setName(name);
        setScore(score);
        setLocation(location);
        setFamilySize(familySize);

        super.setJson(this.toJSON());
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

        Producer producer = new Producer(id, name, score, location, familySize);
        return producer;
    }

    public static Producer fromContentValues(ContentValues origin) {
        String id = origin.getAsString(BlockEntry.COLUMN_PRODUCER_ID);
        String name = origin.getAsString("name");
        double score = origin.getAsDouble("score");
        String location = origin.getAsString("location");
        int familySize = origin.getAsInteger("family_size");

        return new Producer(id, name, score, location, familySize);
    }

    public static ContentValues writeToContentValues(Producer producer) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BlockEntry.COLUMN_PRODUCER_ID, producer.getId());
        contentValues.put("name", producer.getName());
        contentValues.put("score", producer.getScore());
        contentValues.put("location", producer.getLocation());
        contentValues.put("family_size", producer.getFamilySize());

        return contentValues;
    }

    public JSONObject toJSON() {
        JSONObject dest = new JSONObject();

        try {
            dest.put(BlockEntry.COLUMN_PRODUCER_ID, getId());
            dest.put("name", getName());
            dest.put("score", getScore());
            dest.put("location", getLocation());
            dest.put("family_size", getFamilySize());
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
