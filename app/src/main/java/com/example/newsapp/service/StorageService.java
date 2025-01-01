package com.example.newsapp.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.newsapp.util.Constants;

// class to manage sharedPreferences operations
public class StorageService {

    // SharedPreferences instance to manage key-value pairs in storage
    private final SharedPreferences sharedPreferences;

    /**
     * Constructor that initializes the SharedPreferences instance.
     *
     * @param context The context from which SharedPreferences is initialized.
     */
    public StorageService(Context context) {
        // Initialize SharedPreferences with a specific name and private mode
        sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Saves a string value into SharedPreferences.
     *
     * @param key The key to associate with the value.
     * @param value The string value to store.
     */
    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply(); // Asynchronous save
    }

    /**
     * Retrieves a string value from SharedPreferences.
     *
     * @param key The key associated with the value.
     * @param defaultValue The default value to return if the key doesn't exist.
     * @return The string value associated with the key, or the default value if not found.
     */
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * Saves an integer value into SharedPreferences.
     *
     * @param key The key to associate with the value.
     * @param value The integer value to store.
     */
    public void saveInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Retrieves an integer value from SharedPreferences.
     *
     * @param key The key associated with the value.
     * @param defaultValue The default value to return if the key doesn't exist.
     * @return The integer value associated with the key, or the default value if not found.
     */
    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * Saves a boolean value into SharedPreferences.
     *
     * @param key The key to associate with the value.
     * @param value The boolean value to store.
     */
    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Retrieves a boolean value from SharedPreferences.
     *
     * @param key The key associated with the value.
     * @param defaultValue The default value to return if the key doesn't exist.
     * @return The boolean value associated with the key, or the default value if not found.
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * Removes a specific key-value pair from SharedPreferences.
     *
     * @param key The key associated with the value to be removed.
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * Clears all data stored in SharedPreferences.
     */
    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}