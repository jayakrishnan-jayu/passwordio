package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;


import com.example.passwordio.adapters.NoteAdapter;
import com.example.passwordio.models.Note;

public class NoteItemsActivity extends AppCompatActivity {

    RecyclerView rvNote;
    DB db;
    TextView count;
    NoteAdapter noteAdapter;

    public Note[] notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_items);

        db = new DB(getApplicationContext());
        notes = db.allNotes();

        noteAdapter = new NoteAdapter(notes);

        rvNote = findViewById(R.id.noteItemsRV);
        rvNote.setLayoutManager(new LinearLayoutManager(this));
        rvNote.setAdapter(noteAdapter);

        count = findViewById(R.id.noteItemsCount);
        count.setText(String.valueOf(notes.length));
    }
}