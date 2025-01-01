package com.example.newsapp.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.db.NewsDao;
import com.example.newsapp.db.NewsDatabase;
import com.example.newsapp.model.Article;

import java.util.List;

/**
 * Repository class responsible for handling local database operations
 * related to articles (insert, fetch, delete).
 */
public class NewsLocalRepository {

    // DAO for accessing the news articles data in the local database
    private NewsDao newsDao;

    // LiveData to hold the list of all articles
    private MutableLiveData<List<Article>> allArticles;

    /**
     * Constructor to initialize the NewsDao using the NewsDatabase instance.
     *
     * @param context The application context to access the database
     */
    public NewsLocalRepository(Context context) {
        // Initialize the DAO
        newsDao = NewsDatabase.getInstance(context).getNewsDao();
    }

    /**
     * Inserts or updates an article in the local database.
     * This method runs on a separate thread to avoid blocking the UI.
     *
     * @param article The article to be inserted or updated
     */
    public void insertArticle(Article article) {
        // Perform database operation in a new thread to prevent blocking the UI
        new Thread(() -> newsDao.insertOrUpdateArticle(article)).start();
    }

    /**
     * Fetches all articles from the local database and returns them as LiveData.
     * The LiveData will automatically notify observers when the data changes.
     *
     * @return MutableLiveData containing the list of all articles
     */
    public MutableLiveData<List<Article>> getAllArticles() {
        // Initialize the MutableLiveData if not already initialized
        allArticles = new MutableLiveData<>();

        // Load all articles from the database in a background thread
        loadAllArticles();

        // Return the MutableLiveData to the caller
        return allArticles;
    }

    /**
     * Helper method to load all articles from the local database on a separate thread.
     */
    private void loadAllArticles() {
        // Run the database query in a background thread
        new Thread(() -> {
            // Fetch the list of articles from the database
            List<Article> articleList = newsDao.getAllArticles();

            // Post the fetched data to the LiveData object, which will notify the UI
            allArticles.postValue(articleList);
        }).start();
    }

    /**
     * Deletes an article from the local database.
     * This operation is performed on a separate thread to avoid blocking the UI.
     *
     * @param article The article to be deleted
     */
    public void deleteArticle(Article article) {
        // Perform the delete operation in a background thread
        new Thread(() -> newsDao.deleteArticle(article)).start();
    }
}
