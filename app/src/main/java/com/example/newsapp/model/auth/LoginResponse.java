package com.example.newsapp.model.auth;

/**
 * Model class representing a login response.
 * Contains information returned by the server after a login request.
 */
public class LoginResponse {

    // Unique identifier for the logged-in user.
    private int id;

    /**
     * Gets the user ID.
     *
     * @return The user ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the user ID.
     *
     * @param id The user ID.
     */
    public void setId(int id) {
        this.id = id;
    }
}