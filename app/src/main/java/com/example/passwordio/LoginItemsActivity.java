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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_items);

        DB db = new DB(getApplicationContext());
        logins = db.allLogins();

        LoginAdapter noteAdapter = new LoginAdapter(logins);

        rv = findViewById(R.id.loginItemsRV);
        rv.setLayoutManager(new LinearLayoutManager(this));
        count = findViewById(R.id.loginItemsCount);

        rv.setAdapter(noteAdapter);
        count.setText(String.valueOf(logins.length));

    }
}