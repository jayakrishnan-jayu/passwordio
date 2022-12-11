package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, App.class);
        startActivity(intent);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Settings.setCustomActionBar(getSupportActionBar());
        getSupportActionBar().getCustomView().setVisibility(View.GONE);
        Settings settings = new Settings(getApplicationContext());

        if (settings.isNewUser()) {
            setContentView(R.layout.activity_signup_account);

            findViewById(R.id.createAccountButton).setOnClickListener(this);
        } else {
            setContentView(R.layout.activity_login_account);
            findViewById(R.id.aLoSubmit).setOnClickListener(this);
            TextView loggedInTextView = findViewById(R.id.tvActivityLoginAccountLoginStatus);
            loggedInTextView.setText("Logged in as Jayakrishnan on passworio");
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createAccountButton:
                setContentView(R.layout.activity_create_account);
                findViewById(R.id.aCrSubmit).setOnClickListener(this);
                break;
            case R.id.aLoSubmit:
                Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                break;
            case R.id.aCrSubmit:
                Toast.makeText(getApplicationContext(), "Signup", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}