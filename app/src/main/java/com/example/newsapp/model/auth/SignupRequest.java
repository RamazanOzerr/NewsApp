package com.example.newsapp.model.auth;

/**
 * Model class representing a signup request.
 * Used to encapsulate the user's email and password for account creation.
 */
public class SignupRequest {

    private String email;
    private String password;

    /**
     * Constructor to initialize a signup request with email and password.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     */
    public SignupRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}

