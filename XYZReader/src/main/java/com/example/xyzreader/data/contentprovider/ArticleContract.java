package com.example.xyzreader.data.contentprovider;

import android.net.Uri;

import static com.example.xyzreader.data.contentprovider.ArticlesProvider.BASE_CONTENT_URI;

public class ArticleContract {

    public static final long INVALID_ARTICLE_ID = -1;

    public static final class ArticleEntry {

        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_THUMBNAIL_URL = "thumb";
        public static final String COLUMN_PHOTO_URL = "photo";
        public static final String COLUMN_ASPECT_RATIO = "aspect_ratio";
        public static final String COLUMN_PUBLISHED_DATE = "published_date";

        private static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();

        public static Uri buildDirUri() {
            return CONTENT_URI;
        }

        public static Uri buildItemUri(long id) {
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_ID, String.valueOf(id))
                    .build();
        }

    }

}
