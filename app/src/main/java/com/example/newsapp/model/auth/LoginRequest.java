package com.example.newsapp.model.auth;

/**
 * Model class representing a login request.
 * Used to encapsulate the user's email and password for authentication.
 */
public class LoginRequest {
    private String email;
    private String password;

    /**
     * Constructor to initialize a login request with email and password.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     */
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}