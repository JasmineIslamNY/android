package com.tek_genie.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;


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
                //foreGroundColor = "Red";
                //backGroundColor = "Green";

                Intent intent = new Intent();
                intent.putExtra("ForeGroundColor", foreGroundColor);
                intent.putExtra("BackGroundColor", backGroundColor);
                setResult(RESULT_OK, intent);
                finish();
            }

        });

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonBackgroundColorRed:
                if (checked)
                    backGroundColor = "Red";
                    break;
            case R.id.radioButtonBackgroundColorGreen:
                if (checked)
                    backGroundColor = "Green";
                    break;
            case R.id.radioButtonBackgroundColorWhite:
                if (checked)
                    backGroundColor = "White";
                    break;
        }

        switch(view.getId()) {
            case R.id.radioButtonForegroundColorYellow:
                if (checked)
                    foreGroundColor = "Yellow";
                break;
            case R.id.radioButtonForegroundColorPurple:
                if (checked)
                    foreGroundColor = "Purple";
                break;
            case R.id.radioButtonForegroundColorGrey:
                if (checked)
                    foreGroundColor = "Grey";
                break;
            case R.id.radioButtonForegroundColorBlack:
                if (checked)
                    foreGroundColor = "Black";
                break;
        }
    }
}


