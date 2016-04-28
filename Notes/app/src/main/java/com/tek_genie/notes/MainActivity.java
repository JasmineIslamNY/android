package com.tek_genie.notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NoteListItemAdapter mAdapter;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotesDBHelper.getInstance(this).getReadableDatabase();

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
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NoteListItemAdapter(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        final String TAG = "MyData";

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText noteText = (EditText) findViewById(R.id.edit_text);
                Log.i(TAG, "Adding: " + noteText.getText().toString());
                NoteListItem newNote = new NoteListItem(noteText.getText().toString());
                mAdapter.addItem(newNote);
                noteText.setText("");
                mLayoutManager.scrollToPosition(0);
                NoteDAO dao = new NoteDAO(MainActivity.this);
                dao.save(newNote);
            }
        });
        setColor();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data.hasExtra("Note")) {
                NoteListItem note = (NoteListItem)data.getSerializableExtra("Note");
                Toast.makeText(this, "Note Updated to: \n" + note.getText(),
                        Toast.LENGTH_LONG).show();
                mAdapter.addItem(note);
                mLayoutManager.scrollToPosition(0);
            }
        }

        if (resultCode == RESULT_OK && requestCode == 2) {
            String foreGroundColor = data.getStringExtra("ForeGroundColor");
            String backGroundColor = data.getStringExtra("BackGroundColor");
                Toast.makeText(this, "Foreground Color: \n" + foreGroundColor,
                        Toast.LENGTH_LONG).show();
                Toast.makeText(this, "Background Color: \n" + backGroundColor,
                        Toast.LENGTH_LONG).show();
            SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("ForeGroundColor", foreGroundColor);
            editor.putString("BackGroundColor", backGroundColor);
            editor.commit();
            setColor();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Toast toast = Toast.makeText(getApplicationContext(), "You Clicked Settings!!", Toast.LENGTH_SHORT);
            //toast.setGravity(Gravity.CENTER,0,0);
            //toast.show();
            //openColorDialog();

            Intent intent = new Intent(this, NoteColorActivity.class);
            ((Activity)this).startActivityForResult(intent, 2);// 2 is the request code. The request code tells the activity who called it, etc MainActivity
            return true;
        }

        if (id == R.id.action_about) {
            Toast toast = Toast.makeText(getApplicationContext(), "You Clicked About!!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void openColorDialog() {
        final EditText input = new EditText(this);

        new AlertDialog.Builder(this)
                .setTitle(R.string.setting_color_title)
                .setMessage(R.string.setting_color_message)
                .setView(input)
                .setPositiveButton(R.string.positive_button_label, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("NOTE_COLOR", value);
                        editor.commit();
                        setColor();
                    }
                })
                .setNegativeButton(R.string.negative_button_label, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //no need to do anything
                    }
                })
                .show();

    }

    public void setColor(){
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        String backGroundColor = prefs.getString("BackGroundColor", "W");
        if(backGroundColor.toUpperCase().contains("G")){
            mRecyclerView.setBackgroundColor(Color.GREEN);
        }else if(backGroundColor.toUpperCase().contains("R")){
            mRecyclerView.setBackgroundColor(Color.RED);
        }else{
            mRecyclerView.setBackgroundColor(Color.WHITE);
        }
    }
}
