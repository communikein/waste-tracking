package com.example.xyzreader.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.example.xyzreader.data.model.Article;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addArticle(Article entry);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] addArticles(List<Article> entries);


    @Update
    int updateArticle(Article article);


    @Query("DELETE FROM articles WHERE id = :id")
    int deleteArticle(long id);

    @Query("DELETE FROM articles")
    int deleteArticles();


    @Query("SELECT * FROM articles WHERE id = :id")
    Article getArticle(long id);

    @Query("SELECT * FROM articles WHERE id = :id")
    Cursor getArticleAsCursor(long id);

    @Query("SELECT * FROM articles WHERE id = :id")
    LiveData<Article> getArticleAsObservable(long id);


    @Query("SELECT * FROM articles ORDER BY id")
    List<Article> getArticles();

    @Query("SELECT * FROM articles ORDER BY id")
    Cursor getArticlesAsCursor();

    @Query("SELECT * FROM articles ORDER BY id")
    LiveData<List<Article>> getArticlesAsObservable();


    @Query("SELECT COUNT(*) FROM articles")
    int getArticlesCount();

}