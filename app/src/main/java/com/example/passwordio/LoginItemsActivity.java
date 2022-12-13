package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.passwordio.adapters.LoginAdapter;
import com.example.passwordio.models.Login;

public class LoginItemsActivity extends AppCompatActivity {

    RecyclerView rvLogin;
    TextView count;
    Login[] logins;
    LoginAdapter loginAdapter;
    DB db;

    ImageView addButton, searchButton;
    TextView actionBarTitle;
    View supportActionBar;

    private void update() {
        db = new DB(getApplicationContext());
        logins = db.allLogins();
        loginAdapter = new LoginAdapter(logins);
        rvLogin.setLayoutManager(new LinearLayoutManager(this));
        rvLogin.setAdapter(loginAdapter);
        count.setText(String.valueOf(logins.length));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_items);

        Settings.setCustomActionBar(getSupportActionBar());
        supportActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = supportActionBar.findViewById(R.id.tvTitle);
        searchButton = supportActionBar.findViewById(R.id.actionBarSearch);
        addButton = supportActionBar.findViewById(R.id.actionBarAdd);

        actionBarTitle.setText("Logins");
        addButton.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.VISIBLE);

        rvLogin = findViewById(R.id.loginItemsRV);
        count = findViewById(R.id.loginItemsCount);

        update();
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }
}