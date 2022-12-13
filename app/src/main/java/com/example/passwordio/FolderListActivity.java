package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.passwordio.adapters.FolderListAdapter;

import com.example.passwordio.models.Folder;

public class FolderListActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView rvNote;
    DB db;
    TextView count;
    FolderListAdapter folderAdapter;

    public Folder[] folders;

    ImageView addButton;
    TextView actionBarTitle, actionBarSave, actionBarDelete;
    View supportActionBar;

    private void update() {
        folders = db.allFolders();

        folderAdapter = new FolderListAdapter(folders);

        rvNote = findViewById(R.id.folderListRV);
        rvNote.setLayoutManager(new LinearLayoutManager(this));
        rvNote.setAdapter(folderAdapter);

        count = findViewById(R.id.folderListCount);
        count.setText(String.valueOf(folders.length));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_list);

        Settings.setCustomActionBar(getSupportActionBar());
        supportActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = supportActionBar.findViewById(R.id.tvTitle);
        actionBarSave = supportActionBar.findViewById(R.id.actionBarSave);
        actionBarDelete = supportActionBar.findViewById(R.id.actionBarDelete);

        addButton = supportActionBar.findViewById(R.id.actionBarAdd);
        addButton.setVisibility(View.VISIBLE);
        addButton.setOnClickListener(this);

        actionBarSave.setVisibility(View.GONE);
        actionBarDelete.setVisibility(View.GONE);

        db = new DB(getApplicationContext());
        update();
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionBarAdd:
                Intent intent = new Intent(this, FolderItemViewActivity.class);
                intent.putExtra("new", true);
                startActivity(intent);
                break;
        }
    }
}