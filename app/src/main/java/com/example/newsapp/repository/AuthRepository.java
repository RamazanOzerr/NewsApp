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

public class AuthRepository {

    private APIService apiService;

    public AuthRepository(){
        apiService = RetrofitAuthClient.getRetrofitInstance().create(APIService.class);

    }

    public void login(String email, String password, final AuthCallback<LoginResponse> callback){
        LoginRequest loginRequest = new LoginRequest(email, password);
        Call<LoginResponse> call = apiService.login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Login failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    public void signUp(String email, String password, final AuthCallback<SignupResponse> callback){
        SignupRequest signupRequest = new SignupRequest(email, password);
        Call<SignupResponse> call = apiService.signup(signupRequest);

        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Signup failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    public interface AuthCallback<T> {
        void onSuccess(T response);
        void onFailure(String error);
    }
}
