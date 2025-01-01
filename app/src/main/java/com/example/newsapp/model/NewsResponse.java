package com.example.newsapp.model;

import java.util.List;

/**
 * Model class representing the response of a news API request.
 * Contains the overall status of the response, the total number of results,
 * and a list of articles retrieved from the API.
 */
public class NewsResponse {

    private String status;        // The status of the API response (e.g., "ok" or "error").
    private int totalResults;     // The total number of articles available.
    private List<Article> articles; // A list of articles retrieved from the API.
    // Getters and Setters

    /**
     * Gets the status of the API response.
     *
     * @return The response status as a string (e.g., "ok").
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the API response.
     *
     * @param status The response status as a string (e.g., "ok").
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the total number of articles available.
     *
     * @return The total results as an integer.
     */
    public int getTotalResults() {
        return totalResults;
    }

    /**
     * Sets the total number of articles available.
     *
     * @param totalResults The total results as an integer.
     */
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * Gets the list of articles retrieved from the API.
     *
     * @return A list of {@link Article} objects.
     */
    public List<Article> getArticles() {
        return articles;
    }

    /**
     * Sets the list of articles retrieved from the API.
     *
     * @param articles A list of {@link Article} objects.
     */
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}

