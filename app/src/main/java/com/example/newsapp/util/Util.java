package com.example.newsapp.util;

import android.content.Context;
import android.widget.Toast;

public class Util {

    public static void createShortToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void createLongToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
