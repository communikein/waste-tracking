package com.example.xyzreader.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.xyzreader.data.contentprovider.ArticleContract;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import static com.example.xyzreader.data.contentprovider.ArticleContract.ArticleEntry.COLUMN_ASPECT_RATIO;
import static com.example.xyzreader.data.contentprovider.ArticleContract.ArticleEntry.COLUMN_AUTHOR;
import static com.example.xyzreader.data.contentprovider.ArticleContract.ArticleEntry.COLUMN_BODY;
import static com.example.xyzreader.data.contentprovider.ArticleContract.ArticleEntry.COLUMN_ID;
import static com.example.xyzreader.data.contentprovider.ArticleContract.ArticleEntry.COLUMN_PHOTO_URL;
import static com.example.xyzreader.data.contentprovider.ArticleContract.ArticleEntry.COLUMN_PUBLISHED_DATE;
import static com.example.xyzreader.data.contentprovider.ArticleContract.ArticleEntry.COLUMN_THUMBNAIL_URL;
import static com.example.xyzreader.data.contentprovider.ArticleContract.ArticleEntry.COLUMN_TITLE;


@Entity(tableName = ArticleContract.ArticleEntry.TABLE_NAME)
public class Article implements Parcelable {

    @SerializedName(COLUMN_ID)
    @PrimaryKey
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long id;

    @SerializedName(COLUMN_TITLE)
    @ColumnInfo(name = COLUMN_TITLE)
    private String title;

    @SerializedName(COLUMN_AUTHOR)
    @ColumnInfo(name = COLUMN_AUTHOR)
    private String author;

    @SerializedName(COLUMN_BODY)
    @ColumnInfo(name = COLUMN_BODY)
    private String body;

    @SerializedName(COLUMN_THUMBNAIL_URL)
    @ColumnInfo(name = COLUMN_THUMBNAIL_URL)
    private String thumbnailUrl;

    @SerializedName(COLUMN_PHOTO_URL)
    @ColumnInfo(name = COLUMN_PHOTO_URL)
    private String photoUrl;

    @SerializedName(COLUMN_ASPECT_RATIO)
    @ColumnInfo(name = COLUMN_ASPECT_RATIO)
    private float aspectRatio;

    @SerializedName(COLUMN_PUBLISHED_DATE)
    @ColumnInfo(name = COLUMN_PUBLISHED_DATE)
    private Date publishedDate;


    public Article(long id, String title, String author, String body, String thumbnailUrl,
                   String photoUrl, float aspectRatio, Date publishedDate) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setBody(body);
        setThumbnailUrl(thumbnailUrl);
        setPhotoUrl(photoUrl);
        setAspectRatio(aspectRatio);
        setPublishedDate(publishedDate);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }


    public static Article fromParcel(Parcel origin) {
        long id = origin.readLong();
        String title = origin.readString();
        String author = origin.readString();
        String body = origin.readString();
        String thumbnailUrl = origin.readString();
        String photoUrl = origin.readString();
        float aspectRatio = origin.readFloat();
        Date publishedDate = new Date(origin.readLong());

        return new Article(id, title, author, body, thumbnailUrl, photoUrl,
                aspectRatio, publishedDate);
    }

    public static Article fromContentValues(ContentValues origin) {
        long id = origin.getAsLong(COLUMN_ID);
        String title = origin.getAsString(COLUMN_TITLE);
        String author = origin.getAsString(COLUMN_AUTHOR);
        String body = origin.getAsString(COLUMN_BODY);
        String thumbnailUrl = origin.getAsString(COLUMN_THUMBNAIL_URL);
        String photoUrl = origin.getAsString(COLUMN_PHOTO_URL);
        float aspectRatio = origin.getAsFloat(COLUMN_ASPECT_RATIO);
        Date publishedDate = new Date(origin.getAsLong(COLUMN_PUBLISHED_DATE));

        return new Article(id, title, author, body, thumbnailUrl, photoUrl,
                aspectRatio, publishedDate);
    }

    public static ContentValues writeToContentValues(Article article) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, article.getId());
        contentValues.put(COLUMN_TITLE, article.getTitle());
        contentValues.put(COLUMN_AUTHOR, article.getAuthor());
        contentValues.put(COLUMN_BODY, article.getBody());
        contentValues.put(COLUMN_THUMBNAIL_URL, article.getThumbnailUrl());
        contentValues.put(COLUMN_PHOTO_URL, article.getPhotoUrl());
        contentValues.put(COLUMN_ASPECT_RATIO, article.getAspectRatio());
        contentValues.put(COLUMN_PUBLISHED_DATE, article.getPublishedDate().getTime());

        return contentValues;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(getTitle());
        dest.writeString(getAuthor());
        dest.writeString(getBody());
        dest.writeString(getThumbnailUrl());
        dest.writeString(getPhotoUrl());
        dest.writeFloat(getAspectRatio());
        dest.writeLong(getPublishedDate().getTime());
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {

        public Article createFromParcel(Parcel origin) {
            return Article.fromParcel(origin);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

}
