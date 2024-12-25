package com.example.newsapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.newsapp.model.Article;

import java.util.List;

@Dao
public interface NewsDao {

//    @Insert
//    void insertNewsResponse(NewsResponseEntity newsResponse);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateArticle(Article article);

//    @Insert
//    void insertSource(SourceEntity source);

//    @Query("SELECT * FROM news_response")
//    List<NewsResponseEntity> getAllNewsResponses();

    @Query("SELECT * FROM articles")
    List<Article> getAllArticles();

    @Delete
    void deleteArticle(Article article);

//    @Query("SELECT * FROM sources")
//    List<SourceEntity> getAllSources();
}
