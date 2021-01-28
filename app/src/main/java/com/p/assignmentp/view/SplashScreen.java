package com.p.assignmentp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.p.assignmentp.R;
import com.p.assignmentp.util.SharedPrefrence;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPrefrence.getLogin(getApplicationContext()).trim().isEmpty()) {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class).addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                } else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class).addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));

                }
            }
        }, 1000);
    }
}