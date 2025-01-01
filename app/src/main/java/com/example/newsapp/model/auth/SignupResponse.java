package com.example.newsapp.model.auth;

/**
 * Model class representing a signup response.
 * Contains the server's response message after a signup request.
 */
public class SignupResponse {
    private String message; // Message from the server (e.g., "Signup successful").

    /**
     * Gets the server's response message.
     *
     * @return The response message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the server's response message.
     *
     * @param message The response message.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}