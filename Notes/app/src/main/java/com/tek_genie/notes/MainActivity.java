package com.tek_genie.notes;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("ForeGroundColor", foreGroundColor);
            editor.putString("BackGroundColor", backGroundColor);
            editor.commit();
            setColor();
            System.exit(0);
            //doRestart(this);
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

    public static void doRestart(Context c) {
        Log.i("RESTART", "Invoked Restart");
        try {
            //check if the context is given
            if (c != null) {
                //fetch the packagemanager so we can get the default launch activity
                // (you can replace this intent with any other activity if you want
                PackageManager pm = c.getPackageManager();
                //check if we got the PackageManager
                if (pm != null) {
                    //create the intent with the default start activity for your application
                    Intent mStartActivity = pm.getLaunchIntentForPackage(
                            c.getPackageName()
                    );
                    if (mStartActivity != null) {
                        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //create a pending intent so the application is restarted after System.exit(0) was called.
                        // We use an AlarmManager to call this intent in 100ms
                        int mPendingIntentId = 223344;
                        PendingIntent mPendingIntent = PendingIntent
                                .getActivity(c, mPendingIntentId, mStartActivity,
                                        PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager mgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                        //kill the application
                        System.exit(0);
                    } else {
                        Log.e("RESTART", "Was not able to restart application, mStartActivity null");
                    }
                } else {
                    Log.e("RESTART", "Was not able to restart application, PM null");
                }
            } else {
                Log.e("RESTART", "Was not able to restart application, Context null");
            }
        } catch (Exception ex) {
            Log.e("RESTART", "Was not able to restart application");
        }
    }

    public void setColor(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String backGroundColor = prefs.getString("BackGroundColor", "W");
        if(backGroundColor.toUpperCase().contains("G")){
            mRecyclerView.setBackgroundColor(Color.GREEN);
        }else if(backGroundColor.toUpperCase().contains("R")){
            mRecyclerView.setBackgroundColor(Color.RED);
        }else{
            mRecyclerView.setBackgroundColor(Color.WHITE);
        }

        String foreGroundColor = prefs.getString("ForeGroundColor", "Grey");
        if(foreGroundColor.toUpperCase().contains("YELLOW")){
            //mRecyclerView.setBackgroundColor(Color.YELLOW);
        }else if(foreGroundColor.toUpperCase().contains("PURPLE")){
            //mRecyclerView.setBackgroundColor(Color.PURPLE);
        }else if(foreGroundColor.toUpperCase().contains("BLACK")){
            //mRecyclerView.setBackgroundColor(Color.BLACK);
        }else{
            //mRecyclerView.setBackgroundColor(Color.GREY);
        }

        //TextView text = (TextView) findViewById(R.id.text);
        //text.setBackgroundColor(Color.RED);
    }
}
