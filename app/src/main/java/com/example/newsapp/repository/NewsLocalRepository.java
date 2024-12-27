package com.example.newsapp.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.db.NewsDao;
import com.example.newsapp.db.NewsDatabase;
import com.example.newsapp.model.Article;

import java.util.List;

public class NewsLocalRepository {

    private NewsDao newsDao;
    private MutableLiveData<List<Article>> allArticles;

    public NewsLocalRepository(Context context) {
        // Initialize the DAO
//        newsDao = AppDatabase.getDatabase(context).newsDao();
        newsDao = NewsDatabase.getInstance(context).getNewsDao();
    }


    // Insert Article
    public void insertArticle(Article article) {
        new Thread(() -> newsDao.insertOrUpdateArticle(article)).start();
    }

    // Fetch all Articles
    public MutableLiveData<List<Article>> getAllArticles() {
//        if (allArticles == null) {
//            allArticles = new MutableLiveData<>();
//            loadAllArticles();
//        }
//        return allArticles;
        allArticles = new MutableLiveData<>();
        loadAllArticles();
        return allArticles;
    }

    private void loadAllArticles() {
        new Thread(() -> {
            List<Article> articleList = newsDao.getAllArticles();
            allArticles.postValue(articleList);
        }).start();
    }

    public void deleteArticle(Article article) {
        new Thread(() -> newsDao.deleteArticle(article)).start();
    }
}
