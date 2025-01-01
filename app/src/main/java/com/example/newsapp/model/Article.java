package com.example.newsapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Model class representing an article.
 * This class is annotated as an entity for use with Room, mapping to the "articles" table in the database.
 */
@Entity(tableName = "articles")
public class Article {

    /**
     * Primary key for the Article entity.
     * Auto-generated to ensure a unique identifier for each article.
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String author;        // The name of the author of the article.
    private String title;         // The title of the article.
    private String description;   // A brief description or summary of the article.
    private String url;           // The URL of the article.
    private String urlToImage;    // The URL to the image associated with the article.
    private String publishedAt;   // The publication date of the article.
    private String content;       // The full content of the article.
    private boolean isLiked;      // A flag to indicate if the article is marked as "liked" by the user.

    // Getters and Setters

    /**
     * Gets the unique ID of the article.
     *
     * @return The article ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID of the article.
     *
     * @param id The article ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the author of the article.
     *
     * @return The author's name.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the article.
     *
     * @param author The author's name.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the title of the article.
     *
     * @return The title of the article.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the article.
     *
     * @param title The title of the article.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the article.
     *
     * @return The article's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the article.
     *
     * @param description The article's description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the URL of the article.
     *
     * @return The article's URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL of the article.
     *
     * @param url The article's URL.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the URL to the image associated with the article.
     *
     * @return The URL to the image.
     */
    public String getUrlToImage() {
        return urlToImage;
    }

    /**
     * Sets the URL to the image associated with the article.
     *
     * @param urlToImage The URL to the image.
     */
    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    /**
     * Gets the publication date of the article.
     *
     * @return The publication date.
     */
    public String getPublishedAt() {
        return publishedAt;
    }

    /**
     * Sets the publication date of the article.
     *
     * @param publishedAt The publication date.
     */
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    /**
     * Gets the content of the article.
     *
     * @return The article's content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the article.
     *
     * @param content The article's content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Checks if the article is marked as "liked."
     *
     * @return `true` if the article is liked, `false` otherwise.
     */
    public boolean isLiked() {
        return isLiked;
    }

    /**
     * Sets the "liked" status of the article.
     *
     * @param liked `true` to mark the article as liked, `false` otherwise.
     */
    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
