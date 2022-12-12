package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.passwordio.adapters.LoginAdapter;
import com.example.passwordio.models.Login;

public class LoginItemsActivity extends AppCompatActivity {

    RecyclerView rv;
    TextView count;
    Login[] logins;
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_items);

        db = new DB(getApplicationContext());
        logins = db.allLogins();

        LoginAdapter loginAdapter = new LoginAdapter(logins);

        rv = findViewById(R.id.loginItemsRV);
        rv.setLayoutManager(new LinearLayoutManager(this));
        count = findViewById(R.id.loginItemsCount);

        rv.setAdapter(loginAdapter);
        count.setText(String.valueOf(logins.length));

    }
}