package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.passwordio.adapters.LoginAdapter;
import com.example.passwordio.adapters.NoteAdapter;
import com.example.passwordio.models.Folder;
import com.example.passwordio.models.Login;
import com.example.passwordio.models.Note;

public class FolderItemsActivity extends AppCompatActivity {

    LoginAdapter loginAdapter;
    NoteAdapter noteAdapter;
    RecyclerView rvLogins;
    RecyclerView rvNotes;
    TextView count;
    Login[] logins;
    Note[] notes;
    DB db;

    Folder folder;

    private void update() {
        logins = db.loginsByFolder(folder.id);
        notes = db.notesByFolder(folder.id);

        loginAdapter = new LoginAdapter(logins);
        noteAdapter = new NoteAdapter(notes);

        rvLogins.setLayoutManager(new LinearLayoutManager(this));
        rvLogins.setAdapter(loginAdapter);

        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setAdapter(noteAdapter);

        count.setText(String.valueOf(logins.length + notes.length));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_items);

        Settings.setCustomActionBar(getSupportActionBar());

        View supportActionBar = getSupportActionBar().getCustomView();
        TextView actionBarTitle = supportActionBar.findViewById(R.id.tvTitle);



        folder = (Folder) getIntent().getSerializableExtra("folder");

        actionBarTitle.setText(folder.name);
        db = new DB(getApplicationContext());

        rvLogins = findViewById(R.id.folderItemsLoginRV);
        rvNotes = findViewById(R.id.folderItemsNoteRV);
        count = findViewById(R.id.folderItemsCount);



        update();
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }
}