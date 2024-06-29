package com.example.playandroid.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.playandroid.MyApplication;

public class TokenManager {

    private final static SharedPreferences sharedPreferences = MyApplication.getContext()
            .getSharedPreferences("token_pref", Context.MODE_PRIVATE);


    public static String getSavedTokenFromStorage() {
        return sharedPreferences.getString("token", null);
    }

    public static void saveTokenToStorage(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static boolean isTokenExist(){
        return sharedPreferences.contains("token");
    }

}
