package com.p.assignmentp.util;

import android.content.Context;

public class SharedPrefrence {

    public static String getLogin(Context context) {
        return  context.getSharedPreferences("login", Context.MODE_PRIVATE).getString("isLogin", "");
    }

    public static void setLogin(Context context,String token) {
        context.getSharedPreferences("login", Context.MODE_PRIVATE).edit().putString("isLogin", token).apply();
    }
}
