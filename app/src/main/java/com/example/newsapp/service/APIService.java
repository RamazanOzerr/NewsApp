package com.example.newsapp.service;

import com.example.newsapp.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    // NewsAPI endpoint for "everything"
    // GET https://newsapi.org/v2/everything?q=bitcoin&apiKey=yourapikey
    @GET("v2/everything")
    Call<NewsResponse> getNews(@Query("q") String query, @Query("apiKey") String apiKey);

    // GET https://newsapi.org/v2/top-headlines?country=us&apiKey=yourapikey
    //GET https://newsapi.org/v2/top-headlines?country=us&apiKey=766e71e132ef4250a9ed004c4c8c9153
    @GET("v2/top-headlines")
    Call<NewsResponse> getHeadlines(@Query("country") String country, @Query("apiKey") String apiKey);
}
