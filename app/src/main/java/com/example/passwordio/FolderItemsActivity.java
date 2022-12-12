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

        rvLogins = findViewById(R.id.folderItemsLoginRV);
        rvNotes = findViewById(R.id.folderItemsNoteRV);
        count = findViewById(R.id.folderItemsCount);

        db = new DB(getApplicationContext());

        logins = db.loginsByFolder(folder_id);
        notes = db.notesByFolder(folder_id);

        LoginAdapter loginAdapter = new LoginAdapter(logins);
        NoteAdapter noteAdapter = new NoteAdapter(notes);

        rvLogins.setLayoutManager(new LinearLayoutManager(this));
        rvLogins.setAdapter(loginAdapter);

        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setAdapter(noteAdapter);

        count.setText(String.valueOf(logins.length + notes.length));

    }
}