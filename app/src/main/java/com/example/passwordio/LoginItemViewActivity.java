package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.passwordio.models.Folder;
import com.example.passwordio.models.Login;
import com.google.android.material.textfield.TextInputEditText;

public class LoginItemViewActivity extends AppCompatActivity implements View.OnClickListener{

    TextInputEditText username;
    TextInputEditText password;
    TextInputEditText uri;

    DB db;
    Login login;

    Folder[] folders;
    TextInputEditText folder;
    TextView actionBarTitle, actionBarSave, actionBarEdit, actionBarCancel;
    View supportActionBar;
    long folder_id = -1;
    com.google.android.material.textfield.TextInputLayout uriLayout;

    private void update() {
        username.setEnabled(false);
        password.setEnabled(false);
        folder.setEnabled(false);
        uri.setEnabled(false);
        String result = "No Folder";
        if (login.folder_id > 0) {
            for (Folder f: folders) {
                if (f.id == login.folder_id) {
                    result = f.name;
                    folder_id = login.folder_id;
                    break;
                }
            }
        }
        if (result.equals("No Folder")) folder_id = -1;
        folder.setText(result);
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

        folder = findViewById(R.id.loginItemViewFolder);
        actionBarEdit.setOnClickListener(this);
        actionBarSave.setOnClickListener(this);
        actionBarCancel.setOnClickListener(this);

        username.addTextChangedListener(new GenericTextWatcher(username));
        password.addTextChangedListener(new GenericTextWatcher(password));
        uri.addTextChangedListener(new GenericTextWatcher(uri));
        folder.setOnClickListener(this);

        login = (Login) getIntent().getSerializableExtra("login");
        folders = db.allFolders();

        update();

        uriLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!uri.isEnabled()) {

                    String url = login.url;
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "http://" + url;
                    }
                    Uri uri = Uri.parse(url);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
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
                folder.setEnabled(true);

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

                Log.v("LoginItemViewActivity", "Save");
                Log.v("LoginItemViewActivity", "folder id " + login.folder_id);
                Log.v("LoginItemViewActivity", login.username + " " + login.folder_id);
                db.updateLogin(login);
                actionBarEdit.setVisibility(View.VISIBLE);
                actionBarCancel.setVisibility(View.GONE);
                actionBarSave.setVisibility(View.GONE);
                actionBarTitle.setText("View Item");
                login = db.loginsByID(login.id);
                update();
                break;
            case R.id.loginItemViewFolder:
                PopupMenu menu = new PopupMenu(view.getContext(), view);
                menu.getMenu().add("No Folder");
                for (Folder folder: folders) {
                    menu.getMenu().add(folder.name);
                }
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        folder.setText(menuItem.getTitle());
                        login.folder_id = -1;
                        for (Folder f: folders) {
                            if (f.name.equals(menuItem.getTitle().toString())) {
                                Log.v("LoginItemViewActivity", "New id " + f.id);
                                login.folder_id = f.id;
                                break;
                            }
                        }
                        return false;
                    }
                });
                menu.show();
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
                    String url = text;
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "https://" + url;
                    }
                    login.url = url;
                    break;
            }
        }
    }
}
