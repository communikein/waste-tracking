package com.example.xyzreader.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("_ID")
    @PrimaryKey
    @ColumnInfo(index = true, name = "_ID")
    private String id;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("score")
    @ColumnInfo(name = "score")
    private double score;


    public User(String id, String name, double score) {
        setId(id);
        setName(name);
        setScore(score);
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



    @Ignore
    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof  User)) return false;

        User entry = (User) obj;
        return entry.getId().equals(this.getId());
    }


    public static User fromParcel(Parcel origin) {
        String id = origin.readString();
        String name = origin.readString();
        double score = origin.readDouble();

        User user = new User(id, name, score);
        return user;
    }

    public static User fromContentValues(ContentValues origin) {
        String id = origin.getAsString("_ID");
        String name = origin.getAsString("name");
        double score = origin.getAsDouble("score");

        User user = new User(id, name, score);
        return user;
    }

    public static ContentValues writeToContentValues(Waste waste) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("_ID", waste.getId());
        contentValues.put("name", waste.getType());
        contentValues.put("score", waste.getWeight());

        return contentValues;
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
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        public User createFromParcel(Parcel origin) {
            return User.fromParcel(origin);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
