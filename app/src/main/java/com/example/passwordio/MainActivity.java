package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText email, password, repassword, loginPassword;
    Settings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = new Intent(this, App.class);
//        startActivity(intent);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Settings.setCustomActionBar(getSupportActionBar());
        getSupportActionBar().getCustomView().setVisibility(View.GONE);
        settings = new Settings(getApplicationContext());


        if (settings.isNewUser()) {
            setContentView(R.layout.activity_signup_account);

            findViewById(R.id.createAccountButton).setOnClickListener(this);
        } else {
            setContentView(R.layout.activity_login_account);
            findViewById(R.id.aLoSubmit).setOnClickListener(this);
            TextView loggedInTextView = findViewById(R.id.tvActivityLoginAccountLoginStatus);
            loggedInTextView.setText("Logged in as "+settings.getUserName()+" on passworio");
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
                loginPassword = findViewById(R.id.aLoPassword);
                if (!settings.isNewUser() && settings.authenticated(loginPassword.getText().toString())) {
                    startActivity(new Intent(this, App.class));
                } else {
                    loginPassword.setError("Wrong Password");
                }

                break;
            case R.id.aCrSubmit:
                String pswd, repswd, mail;
                email = findViewById(R.id.aCrEmail);
                password = findViewById(R.id.aCrPassword);
                repassword = findViewById(R.id.aCrRePassword);
                pswd = password.getText().toString();
                repswd = repassword.getText().toString();
                mail = email.getText().toString();
                if (mail.isEmpty()) {
                    email.setError("Email should not be empty");
                    break;
                }
                if (pswd.isEmpty()) {
                    password.setError("password should not be empty");
                    break;
                }
                if (!pswd.equals(repswd)) {
                    repassword.setError("Password not Equal");
                    break;
                } else if (pswd.length() < 6) {
                    password.setError("Password length should be greater than 6");
                    break;
                }

                settings.createNewUser(mail, pswd);
                startActivity(new Intent(this, App.class));
                break;
        }
    }
}