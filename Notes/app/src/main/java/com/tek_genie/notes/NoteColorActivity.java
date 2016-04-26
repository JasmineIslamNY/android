package com.tek_genie.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class NoteColorActivity extends AppCompatActivity {

    private String backGroundColor;
    private String foreGroundColor;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_color);


        Intent intent = getIntent();
        /*
        note = (NoteListItem) intent.getSerializableExtra("Note");
        noteEditText = (EditText) findViewById(R.id.note_text_edit);
        noteEditText.setText(note.getText());
        */

        saveButton = (Button) findViewById(R.id.buttonColorSelectorSave);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foreGroundColor = "";
                backGroundColor = "";

                Intent intent = new Intent();
                intent.putExtra("BackGroundColor", backGroundColor);
                intent.putExtra("ForeGroundColor", foreGroundColor);
                setResult(RESULT_OK, intent);
                finish();
            }

        });

    }
}


