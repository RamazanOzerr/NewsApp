package com.example.newsapp.network;

import static com.example.newsapp.util.URL.BASE_URL;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class to provide a Retrofit instance for making network requests.
 * This instance is configured with an HTTP client that includes logging for debugging purposes.
 */
public class RetrofitClient {

    // Singleton Retrofit instance for making API calls
    private static Retrofit retrofit;

    /**
     * Provides a singleton instance of the Retrofit object.
     * The instance is configured with a logging interceptor and Gson converter for JSON parsing.
     *
     * @return A configured Retrofit instance.
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {

            // Set up an HTTP logging interceptor to log request and response details
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Create an OkHttpClient with the logging interceptor for debugging purposes
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)  // Add logging for all HTTP requests and responses
                    .build();

            // Build the Retrofit instance with the base URL, HTTP client, and Gson converter
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)  // Base URL for the News API
                    .client(client)  // HTTP client with logging
                    .addConverterFactory(GsonConverterFactory.create())  // Gson converter for JSON parsing
                    .build();
        }
        return retrofit;
    }
}
