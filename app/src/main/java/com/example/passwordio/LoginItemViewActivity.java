package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.passwordio.models.Login;
import com.google.android.material.textfield.TextInputEditText;

public class LoginItemViewActivity extends AppCompatActivity {

    TextInputEditText username;
    TextInputEditText password;
    TextInputEditText uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_item_view);
        username = findViewById(R.id.loginItemViewUsername);
        password = findViewById(R.id.loginItemViewPassowrd);
        uri = findViewById(R.id.loginItemViewURI);

        Login login = (Login) getIntent().getSerializableExtra("login");
        Toast.makeText(this, login.username, Toast.LENGTH_SHORT).show();

        username.setEnabled(false);
        password.setEnabled(false);
        uri.setEnabled(false);

        username.setText(login.username);
        password.setText(login.password);
        uri.setText(login.url);

    }
}