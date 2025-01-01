package com.example.newsapp.network;

import static com.example.newsapp.util.URL.AUTH_URL;
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
 * Singleton class to provide a Retrofit instance configured for authentication requests.
 * This instance includes an HTTP client with logging for debugging purposes.
 */
public class RetrofitAuthClient {

    // Retrofit instance for making API calls
    private static Retrofit retrofit;

    /**
     * Returns a singleton instance of the Retrofit object for authentication requests.
     * The instance is initialized with a logging interceptor and Gson converter for JSON parsing.
     *
     * @return A configured Retrofit instance.
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {

            // Set up the logging interceptor to log HTTP request and response data
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Create an OkHttpClient with the logging interceptor for debugging network calls
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            // Build the Retrofit instance with the base URL, HTTP client, and Gson converter
            retrofit = new Retrofit.Builder()
                    .baseUrl(AUTH_URL)   // Base URL for authentication API
                    .client(client)  // HTTP client with logging
                    .addConverterFactory(GsonConverterFactory.create())  /// Gson converter for JSON parsing
                    .build();
        }
        return retrofit;
    }
}
