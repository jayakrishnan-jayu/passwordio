package com.example.passwordio;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.passwordio.models.Folder;
import com.google.android.material.textfield.TextInputEditText;

public class FolderItemViewActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText name;
    Folder folder;
    ImageView addButton;
    TextView actionBarTitle, actionBarSave, actionBarDelete, actionBarCancel;
    View supportActionBar;
    DB db;
    boolean newFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_item_view);

        newFolder = getIntent().getBooleanExtra("new", false);

        Settings.setCustomActionBar(getSupportActionBar());
        supportActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = supportActionBar.findViewById(R.id.tvTitle);
        actionBarSave = supportActionBar.findViewById(R.id.actionBarSave);
        actionBarDelete = supportActionBar.findViewById(R.id.actionBarDelete);
        actionBarCancel = supportActionBar.findViewById(R.id.actionBarCancel);

        addButton = supportActionBar.findViewById(R.id.actionBarAdd);
        addButton.setVisibility(View.GONE);

        actionBarSave.setVisibility(View.VISIBLE);
        actionBarSave.setOnClickListener(this);

        if (!newFolder) {
            actionBarDelete.setVisibility(View.VISIBLE);
            actionBarDelete.setOnClickListener(this);
            actionBarTitle.setText("Edit Folder");
            folder = (Folder) getIntent().getSerializableExtra("folder");
        } else {
            actionBarTitle.setText("Create Folder");
            actionBarCancel.setVisibility(View.VISIBLE);
            actionBarCancel.setOnClickListener(this);
            folder = new Folder("");
        }

        db = new DB(getApplicationContext());

        name = findViewById(R.id.folderItemViewName);
        name.setOnClickListener(this);
        name.setEnabled(true);
        name.setText(folder.name);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                folder.name = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionBarSave:
                if (newFolder) {
                    if (folder.name.equals("")) {
                        Toast.makeText(this, "Invalid Folder Name", Toast.LENGTH_LONG).show();
                        return;
                    }
                    db.createFolder(folder.name);
                    Toast.makeText(this, "Folder Created", Toast.LENGTH_SHORT).show();
                } else {
                    if (folder.name.equals("")) {
                        db.deleteFolder(folder);
                        Toast.makeText(this, "Folder Deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        db.updateFolder(folder);
                        Toast.makeText(this, "Folder Updated", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.actionBarDelete:
                db.deleteFolder(folder);
                Toast.makeText(getApplicationContext(), "Folder Deleted", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actionBarCancel:
                finish();
                break;
        }

        finish();
    }

}