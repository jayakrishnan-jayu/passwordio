package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.passwordio.adapters.LoginAdapter;
import com.example.passwordio.models.Login;

public class LoginItemsActivity extends AppCompatActivity {

    RecyclerView rvLogin;
    TextView count;
    Login[] logins;
    LoginAdapter loginAdapter;
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_items);

        db = new DB(getApplicationContext());
        logins = db.allLogins();

        loginAdapter = new LoginAdapter(logins);

        rvLogin = findViewById(R.id.loginItemsRV);
        rvLogin.setLayoutManager(new LinearLayoutManager(this));
        rvLogin.setAdapter(loginAdapter);

        count = findViewById(R.id.loginItemsCount);
        count.setText(String.valueOf(logins.length));

    }
}