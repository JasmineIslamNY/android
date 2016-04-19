package com.tek_genie.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {
    private EditText noteEditText;
    private NoteListItem note;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        note = (NoteListItem) intent.getSerializableExtra("Note");
        noteEditText = (EditText) findViewById(R.id.note_text_edit);
        noteEditText.setText(note.getText());

        saveButton = (Button) findViewById(R.id.note_save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String note_text = noteEditText.getText().toString();
                note.setText(note_text);
                NoteDAO dao = new NoteDAO(EditNoteActivity.this);
                dao.update(note);

                Intent intent = new Intent();
                intent.putExtra("Note", note);
                setResult(RESULT_OK, intent);
                finish();
            }

        });

    }
}
