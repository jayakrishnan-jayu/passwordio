package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.passwordio.adapters.NoteAdapter;
import com.example.passwordio.models.Note;

public class NoteItemsActivity extends AppCompatActivity {

    RecyclerView rvNote;
    DB db;
    TextView count;
    NoteAdapter noteAdapter;
    ImageView addButton, searchButton;
    TextView actionBarTitle;
    View supportActionBar;

    public Note[] notes;

    private void update() {
        db = new DB(getApplicationContext());
        notes = db.allNotes();
        noteAdapter = new NoteAdapter(notes);
        rvNote.setLayoutManager(new LinearLayoutManager(this));
        rvNote.setAdapter(noteAdapter);
        count.setText(String.valueOf(notes.length));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_items);

        Settings.setCustomActionBar(getSupportActionBar());

        supportActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = supportActionBar.findViewById(R.id.tvTitle);
        searchButton = supportActionBar.findViewById(R.id.actionBarSearch);
        addButton = supportActionBar.findViewById(R.id.actionBarAdd);

        actionBarTitle.setText("Secure Notes");
        addButton.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.VISIBLE);

        rvNote = findViewById(R.id.noteItemsRV);
        count = findViewById(R.id.noteItemsCount);

        update();
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }
}