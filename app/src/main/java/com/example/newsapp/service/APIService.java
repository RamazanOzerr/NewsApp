package com.example.newsapp.service;

import com.example.newsapp.model.NewsResponse;
import com.example.newsapp.model.auth.LoginRequest;
import com.example.newsapp.model.auth.LoginResponse;
import com.example.newsapp.model.auth.SignupRequest;
import com.example.newsapp.model.auth.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    /**
     * Endpoint to fetch news articles based on a query.
     * This API is used to retrieve news articles related to the specified query (e.g., "bitcoin").
     *
     * @param query The search query (e.g., "bitcoin", "technology", etc.).
     * @param apiKey The API key for authenticating the request.
     * @return A Call object for the NewsResponse containing the fetched articles.
     */

    // GET https://newsapi.org/v2/everything?q=bitcoin&apiKey=yourapikey
    @GET("v2/everything")
    Call<NewsResponse> getNews(@Query("q") String query, @Query("apiKey") String apiKey);

    /**
     * Endpoint to fetch top headlines for a specific country.
     * This API is used to retrieve the top news headlines based on a specific country code (e.g., "us" for United States).
     *
     * @param country The country code (e.g., "us" for the United States).
     * @param apiKey The API key for authenticating the request.
     * @return A Call object for the NewsResponse containing the top headlines.
     */

    // GET https://newsapi.org/v2/top-headlines?country=us&apiKey=yourapikey
    @GET("v2/top-headlines")
    Call<NewsResponse> getHeadlines(@Query("country") String country, @Query("apiKey") String apiKey);

    /**
     * Endpoint to handle user login.
     * This API is used to authenticate a user with their email and password.
     *
     * @param loginRequest The request object containing the user's email and password.
     * @return A Call object for the LoginResponse containing the result of the login attempt.
     */
    @POST("api/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    /**
     * Endpoint to handle user signup.
     * This API is used to create a new user account with an email and password.
     *
     * @param signupRequest The request object containing the user's email and password.
     * @return A Call object for the SignupResponse containing the result of the signup attempt.
     */
    @POST("api/signup")
    Call<SignupResponse> signup(@Body SignupRequest signupRequest);

}
