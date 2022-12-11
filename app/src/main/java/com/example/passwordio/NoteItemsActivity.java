package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.passwordio.adapters.FolderAdapter;
import com.example.passwordio.adapters.NoteAdapter;
import com.example.passwordio.models.Folder;
import com.example.passwordio.models.Note;

public class NoteItemsActivity extends AppCompatActivity {

    RecyclerView rv;
    TextView count;

    public static final Note[] noteData = {
            new Note("Github"),
            new Note("Gitlab"),
            new Note("College"),
            new Note("Google"),
            new Note("Opera"),
            new Note("Network"),
            new Note("Home"),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_items);

        NoteAdapter noteAdapter = new NoteAdapter(noteData);

        rv = findViewById(R.id.noteItemsRV);
        rv.setLayoutManager(new LinearLayoutManager(this));
        count = findViewById(R.id.noteItemsCount);

        rv.setAdapter(noteAdapter);
        count.setText(String.valueOf(noteData.length));


    }
}