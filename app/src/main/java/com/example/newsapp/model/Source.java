package com.example.newsapp.model;

/**
 * Model class representing the source of a news article.
 * Contains the ID and name of the source.
 */
public class Source {

    private String id;    // The unique identifier for the source (can be null for some APIs).
    private String name;  // The name of the source (e.g., "BBC News").

    /**
     * Constructor to initialize a Source object with an ID and name.
     *
     * @param id   The unique identifier for the source.
     * @param name The name of the source.
     */
    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters

    /**
     * Gets the ID of the source.
     *
     * @return The source ID as a string.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the source.
     *
     * @param id The source ID as a string.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the source.
     *
     * @return The source name as a string.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the source.
     *
     * @param name The source name as a string.
     */
    public void setName(String name) {
        this.name = name;
    }
}

