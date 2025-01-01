package com.example.newsapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.newsapp.model.Article;
import java.util.List;

/**
 * Data Access Object (DAO) for interacting with the `articles` table in the database.
 * This interface defines methods for performing CRUD operations on Article objects.
 */
@Dao
public interface NewsDao {

    /**
     * Inserts a new article or updates an existing article in the database.
     * If an article with the same primary key already exists, it will be replaced.
     *
     * @param article The Article object to be inserted or updated.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateArticle(Article article);

    /**
     * Retrieves all articles stored in the `articles` table.
     *
     * @return A list of all Article objects from the database.
     */
    @Query("SELECT * FROM articles")
    List<Article> getAllArticles();

    /**
     * Deletes a specific article from the database.
     *
     * @param article The Article object to be deleted.
     */
    @Delete
    void deleteArticle(Article article);

}
