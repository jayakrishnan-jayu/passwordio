package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.passwordio.adapters.LoginAdapter;
import com.example.passwordio.adapters.NoteAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_items);

        long folder_id = getIntent().getLongExtra("folder_id", -1);

        db = new DB(getApplicationContext());
        logins = db.loginsByFolder(folder_id);
        notes = db.notesByFolder(folder_id);

        loginAdapter = new LoginAdapter(logins);
        noteAdapter = new NoteAdapter(notes);

        rvLogins = findViewById(R.id.folderItemsLoginRV);
        rvLogins.setLayoutManager(new LinearLayoutManager(this));
        rvLogins.setAdapter(loginAdapter);


        rvNotes = findViewById(R.id.folderItemsNoteRV);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setAdapter(noteAdapter);

        count = findViewById(R.id.folderItemsCount);
        count.setText(String.valueOf(logins.length + notes.length));
    }
}