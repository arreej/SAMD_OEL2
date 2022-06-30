package com.example.innovativebanking.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

    Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public int getCurrentUserId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("currentUserID", 1);
    }

    public String[] getUserCredentials() {
        String[] userCredentials = new String[2];
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        userCredentials[0] = sharedPreferences.getString("userEmail", "");
        userCredentials[1] = sharedPreferences.getString("userPass", "");
        return userCredentials;
    }

    public String getUserAccountTarget() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("accountBalanceTarget", "1000");
    }


}
