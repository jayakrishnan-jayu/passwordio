package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.passwordio.models.Login;
import com.example.passwordio.models.Note;
import com.google.android.material.textfield.TextInputEditText;

public class NoteItemViewActivity extends AppCompatActivity {

    TextInputEditText name;
    TextInputEditText noteTV;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_item_view);

        Note note = (Note) getIntent().getSerializableExtra("note");

        name = findViewById(R.id.noteItemViewName);
        noteTV = findViewById(R.id.noteItemViewNote);
        spinner = findViewById(R.id.noteItemViewSpinner);

        noteTV.setEnabled(false);
        name.setEnabled(false);
        spinner.setEnabled(false);
        spinner.setVisibility(View.GONE);

        name.setText(note.name);
        noteTV.setText(note.note);
//        uri.setText(login.url);
    }
}