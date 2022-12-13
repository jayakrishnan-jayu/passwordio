package com.example.passwordio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.passwordio.models.Login;
import com.example.passwordio.models.Note;
import com.google.android.material.textfield.TextInputEditText;

public class NoteItemViewActivity extends AppCompatActivity implements  View.OnClickListener {

    TextInputEditText name;
    TextInputEditText noteTV;
    Spinner spinner;
    TextView actionBarTitle, actionBarSave, actionBarEdit, actionBarCancel;
    View supportActionBar;

    DB db;
    Note note;

    private void update() {
        noteTV.setEnabled(false);
        name.setEnabled(false);
        spinner.setEnabled(false);
        spinner.setVisibility(View.GONE);

        name.setText(note.name);
        noteTV.setText(note.note);
//        uri.setText(login.url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_item_view);

        Settings.setCustomActionBar(getSupportActionBar());
        supportActionBar = getSupportActionBar().getCustomView();
        actionBarTitle = supportActionBar.findViewById(R.id.tvTitle);
        actionBarSave = supportActionBar.findViewById(R.id.actionBarSave);
        actionBarEdit = supportActionBar.findViewById(R.id.actionBarEdit);
        actionBarCancel = supportActionBar.findViewById(R.id.actionBarCancel);

        note = (Note) getIntent().getSerializableExtra("note");
        db = new DB(this);

        actionBarTitle.setText("View Item");
        actionBarEdit.setVisibility(View.VISIBLE);

        name = findViewById(R.id.noteItemViewName);
        noteTV = findViewById(R.id.noteItemViewNote);
        spinner = findViewById(R.id.noteItemViewSpinner);

        actionBarEdit.setOnClickListener(this);
        actionBarSave.setOnClickListener(this);
        actionBarCancel.setOnClickListener(this);

        name.addTextChangedListener(new GenericTextWatcher(name));
        noteTV.addTextChangedListener(new GenericTextWatcher(noteTV));
        update();
    }


    @Override
    public void onClick(View view) {
        Log.v("LoginItemViewActivity", "onClick id: "+view.getId());
        switch (view.getId()) {
            case R.id.actionBarEdit:
                actionBarEdit.setVisibility(View.GONE);
                actionBarCancel.setVisibility(View.VISIBLE);
                actionBarSave.setVisibility(View.VISIBLE);
                actionBarTitle.setText("Edit Item");

                noteTV.setEnabled(true);
                name.setEnabled(true);
//                spinner.setEnabled(true);

                break;
            case R.id.actionBarCancel:
                actionBarEdit.setVisibility(View.VISIBLE);
                actionBarCancel.setVisibility(View.GONE);
                actionBarSave.setVisibility(View.GONE);
                actionBarTitle.setText("View Item");
                note = db.noteByID(note.id);
                update();
                break;
            case R.id.actionBarSave:
                db.updateNote(note);
                actionBarEdit.setVisibility(View.VISIBLE);
                actionBarCancel.setVisibility(View.GONE);
                actionBarSave.setVisibility(View.GONE);
                actionBarTitle.setText("View Item");
                note = db.noteByID(note.id);
                update();
                break;
        }
    }

    private class GenericTextWatcher implements TextWatcher {

        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch(view.getId()){
                case R.id.noteItemViewName:
                    note.name = text;
                    break;
                case R.id.noteItemViewNote:
                    note.note = text;
                    break;
            }
        }
    }

}