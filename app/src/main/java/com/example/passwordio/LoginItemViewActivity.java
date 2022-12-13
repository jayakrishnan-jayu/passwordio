package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.passwordio.models.Login;
import com.google.android.material.textfield.TextInputEditText;

public class LoginItemViewActivity extends AppCompatActivity implements View.OnClickListener{

    TextInputEditText username;
    TextInputEditText password;
    TextInputEditText uri;

    DB db;

    Login login;

    TextView actionBarTitle, actionBarSave, actionBarEdit, actionBarCancel;
    View supportActionBar;
    com.google.android.material.textfield.TextInputLayout uriLayout;

    private void update() {
        username.setEnabled(false);
        password.setEnabled(false);
        uri.setEnabled(false);

        username.setText(login.username);
        password.setText(login.password);
        uri.setText(login.url.substring(8));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_item_view);

        db = new DB(getApplicationContext());

        Settings.setCustomActionBar(getSupportActionBar());
        supportActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = supportActionBar.findViewById(R.id.tvTitle);
        actionBarSave = supportActionBar.findViewById(R.id.actionBarSave);
        actionBarEdit = supportActionBar.findViewById(R.id.actionBarEdit);
        actionBarCancel = supportActionBar.findViewById(R.id.actionBarCancel);

        actionBarTitle.setText("View Item");
        actionBarEdit.setVisibility(View.VISIBLE);

        uriLayout = findViewById(R.id.loginItemViewURILayout);

        username = findViewById(R.id.loginItemViewUsername);
        password = findViewById(R.id.loginItemViewPassowrd);
        uri = findViewById(R.id.loginItemViewURI);

        actionBarEdit.setOnClickListener(this);
        actionBarSave.setOnClickListener(this);
        actionBarCancel.setOnClickListener(this);

        username.addTextChangedListener(new GenericTextWatcher(username));
        password.addTextChangedListener(new GenericTextWatcher(password));
        uri.addTextChangedListener(new GenericTextWatcher(uri));

        login = (Login) getIntent().getSerializableExtra("login");
        update();

        uriLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!uri.isEnabled()) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(uri.getText().toString()));
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        Log.v("LoginItemViewActivity", "onClick id: "+view.getId());
        switch (view.getId()) {
            case R.id.actionBarEdit:
                actionBarEdit.setVisibility(View.GONE);
                actionBarCancel.setVisibility(View.VISIBLE);
                actionBarSave.setVisibility(View.VISIBLE);
                actionBarTitle.setText("Edit Item");

                username.setEnabled(true);
                password.setEnabled(true);
                uri.setEnabled(true);

                break;
            case R.id.actionBarCancel:
                actionBarEdit.setVisibility(View.VISIBLE);
                actionBarCancel.setVisibility(View.GONE);
                actionBarSave.setVisibility(View.GONE);
                actionBarTitle.setText("View Item");
                login = db.loginsByID(login.id);
                update();
                break;
            case R.id.actionBarSave:
                db.updateLogin(login);
                actionBarEdit.setVisibility(View.VISIBLE);
                actionBarCancel.setVisibility(View.GONE);
                actionBarSave.setVisibility(View.GONE);
                actionBarTitle.setText("View Item");
                login = db.loginsByID(login.id);
                update();
                break;
        }
    }


    private class GenericTextWatcher implements TextWatcher{

        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch(view.getId()){
                case R.id.loginItemViewUsername:
                    login.username = text;
                    break;
                case R.id.loginItemViewPassowrd:
                    login.password = text;
                    break;
                case R.id.loginItemViewURI:
                    login.url = "https://"+text;
                    break;
            }
            Log.v("LoginItemViewActivity", "\n"+login.username+"\n"+login.url);
        }
    }
}

//class GenericTextWatcher implements TextWatcher{
//
//    private View view;
//    GenericTextWatcher(View view) {
//        this.view = view;
//    }
//
//    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//
//    public void afterTextChanged(Editable editable) {
//        String text = editable.toString();
//        switch(view.getId()){
//            case R.id.loginItemViewUsername:
//                model.setName(text);
//                break;
//            case R.id.loginItemViewPassowrd:
//                model.setEmail(text);
//                break;
//            case R.id.loginItemViewURI:
//                model.setPhone(text);
//                break;
//        }
//    }
//}
