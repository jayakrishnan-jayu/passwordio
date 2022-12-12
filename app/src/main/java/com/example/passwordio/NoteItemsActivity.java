package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;


import com.example.passwordio.adapters.NoteAdapter;
import com.example.passwordio.models.Note;

public class NoteItemsActivity extends AppCompatActivity {

    RecyclerView rv;
    TextView count;

    public Note[] notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_items);

        DB db = new DB(getApplicationContext());
        notes = db.allNotes();

        NoteAdapter noteAdapter = new NoteAdapter(notes);

        rv = findViewById(R.id.noteItemsRV);
        rv.setLayoutManager(new LinearLayoutManager(this));
        count = findViewById(R.id.noteItemsCount);

        rv.setAdapter(noteAdapter);
        count.setText(String.valueOf(notes.length));


    }
}