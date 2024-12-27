package com.example.newsapp.repository;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.model.Article;
import com.example.newsapp.model.NewsResponse;
import com.example.newsapp.network.RetrofitClient;
import com.example.newsapp.service.APIService;

import java.util.List;

public class NewsRemoteRepository {

    private static final String TAG = "REMOTE_REPOSITORY";

    private APIService apiService;
    private MutableLiveData<List<Article>> newsListLiveData;

//    private APIService apiRemoteService;
//    private MutableLiveData<List<Article>> userFavoriteListLiveData;

    private MutableLiveData<List<Article>> breakingNewsListLiveData;

    public NewsRemoteRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
//        apiRemoteService = RetrofitAuthClient.getRetrofitInstance().create(APIService.class);
    }

//    public MutableLiveData<List<Article>> getFavNewsFromRemote(int userId){
//        apiRemoteService.getUserFavourites(userId).enqueue(new Callback<FavoriteArticleResponse>() {
//            @Override
//            public void onResponse(Call<FavoriteArticleResponse> call, Response<FavoriteArticleResponse> response) {
//                if(response.isSuccessful()){
//                    userFavoriteListLiveData.setValue(response.body().getFavoriteArticles());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FavoriteArticleResponse> call, Throwable throwable) {
//
//            }
//        });
//
//        return userFavoriteListLiveData;
//    }

    public MutableLiveData<List<Article>> getBreakingNews(String country, String apiKey){
        if(breakingNewsListLiveData == null){
            breakingNewsListLiveData = new MutableLiveData<>();
            getBreakingNewsHelper(country, apiKey);
        }
        if(breakingNewsListLiveData.getValue() != null && breakingNewsListLiveData.getValue().isEmpty()){
            breakingNewsListLiveData = new MutableLiveData<>();
            getBreakingNewsHelper(country, apiKey);
        }

        return breakingNewsListLiveData;
    }

    private void getBreakingNewsHelper(String country, String apiKey){
        apiService.getHeadlines(country, apiKey).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    breakingNewsListLiveData.setValue(response.body().getArticles());
                    Log.d(TAG, "onResponse: get breaking news successfully" + response);
                } else {
                    breakingNewsListLiveData.setValue(null);
                    Log.d(TAG, "onResponse: get breaking news failed " + response);
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e(TAG, "Error Code: " + response.code());
                        Log.e(TAG, "Error Body: " + errorBody);
                        breakingNewsListLiveData.setValue(null);
                    } catch (Exception e) {
                        Log.e(TAG, "Error reading error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable throwable) {
                breakingNewsListLiveData.setValue(null); // Handle failure case
                Log.d(TAG, "onResponse: get breaking news failed " + throwable.getMessage());
            }
        });
    }

    // retrieve articles, makes sure it does not perform network operations multiple times
    public MutableLiveData<List<Article>> getNews(String query, String apiKey) {
        //todo: burayı ana ekrandaki haberler için kullancaz
//        if(newsListLiveData == null){
//            newsListLiveData = new MutableLiveData<>();
//            getNewsHelper(query, apiKey);
//        }
//
//        return newsListLiveData;

        newsListLiveData = new MutableLiveData<>();
        getNewsHelper(query, apiKey);
        return newsListLiveData;
    }

    // helper method to retrieve articles
    private void getNewsHelper(String query, String apiKey){
        apiService.getNews(query, apiKey).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    newsListLiveData.setValue(response.body().getArticles());
                    Log.d(TAG, "onResponse: search news successfull" + response);
                } else {
                    newsListLiveData.setValue(null); // Handle failure case
                    Log.d(TAG, "onResponse: search news failed " + response);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                newsListLiveData.setValue(null); // Handle failure case
                Log.d(TAG, "onResponse: search news failed " + t.getMessage());
            }
        });
    }
}
