package com.example.passwordio;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Settings.setCustomActionBar(getSupportActionBar());
        Settings settings = new Settings(getApplicationContext());

        if (settings.isNewUser()) {
            setContentView(R.layout.activity_create_account);
        } else {
            setContentView(R.layout.activity_login_account);
        }

    }
}