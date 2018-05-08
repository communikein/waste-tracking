package com.example.xyzreader.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.xyzreader.data.model.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ArticlesDatabase extends RoomDatabase {

    public static final String NAME = "xyz_reader";

    public abstract ArticlesDao articlesDao();
}
