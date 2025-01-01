package com.example.newsapp.repository;

import com.example.newsapp.model.auth.LoginRequest;
import com.example.newsapp.model.auth.LoginResponse;
import com.example.newsapp.model.auth.SignupRequest;
import com.example.newsapp.model.auth.SignupResponse;
import com.example.newsapp.network.RetrofitAuthClient;
import com.example.newsapp.network.RetrofitClient;
import com.example.newsapp.service.APIService;
import com.example.newsapp.service.StorageService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository class responsible for handling authentication requests
 * (login and signup) by interacting with the API service.
 */
public class AuthRepository {

    // API service instance to make network calls
    private APIService apiService;

    /**
     * Constructor to initialize the APIService instance.
     * This uses the RetrofitAuthClient to get the Retrofit instance.
     */
    public AuthRepository(){
        apiService = RetrofitAuthClient.getRetrofitInstance().create(APIService.class);

    }

    /**
     * Handles the login process by sending the email and password
     * to the API and triggering the callback with the result.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @param callback The callback to handle the response or failure.
     */
    public void login(String email, String password, final AuthCallback<LoginResponse> callback){

        // Create a LoginRequest object with the provided email and password
        LoginRequest loginRequest = new LoginRequest(email, password);

        // Make the login request using the API service
        Call<LoginResponse> call = apiService.login(loginRequest);

        // Asynchronously execute the login request
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                // Check if the response is successful
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());  // Success, return response
                } else {
                    // Failure, return message
                    callback.onFailure("Login failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Failure due to network error
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    /**
     * Handles the signup process by sending the email and password
     * to the API and triggering the callback with the result.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @param callback The callback to handle the response or failure.
     */
    public void signUp(String email, String password, final AuthCallback<SignupResponse> callback){
        // Create a SignupRequest object with the provided email and password
        SignupRequest signupRequest = new SignupRequest(email, password);

        // Make the signup request using the API service
        Call<SignupResponse> call = apiService.signup(signupRequest);

        // Asynchronously execute the signup request
        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                // Check if the response is successful
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());  // Success, return response
                } else {
                    // Failure, return message
                    callback.onFailure("Signup failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                // Failure due to network error
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    /**
     * Callback interface to handle the success or failure of authentication requests.
     *
     * @param <T> The type of response (LoginResponse or SignupResponse).
     */
    public interface AuthCallback<T> {
        void onSuccess(T response);  // Called when the request is successful
        void onFailure(String error);  // Called when the request fails
    }
}
