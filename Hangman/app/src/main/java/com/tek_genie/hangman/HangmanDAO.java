package com.tek_genie.hangman;

import java.io.Serializable;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import com.tek_genie.hangman.HangmanNameItem;
import java.util.List;

/**
 * Created by jasmineislam on 5/18/16.
 */
public class HangmanDAO implements Serializable {
    private Context context;
    private String displayedCount = "0";
    private Integer nameReturnedCounter;
    List<HangmanNameItem> names;


    public HangmanDAO (Context context){
        this.context = context;
    }

    public HangmanDAO() {
    }

    public HangmanNameItem nextName (){
        HangmanNameItem name = null;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        nameReturnedCounter = Integer.valueOf(prefs.getString("savedNameReturnedCounter", "-1"));
        SharedPreferences.Editor editor = prefs.edit();

        if (nameReturnedCounter == -1) {
            name = new HangmanNameItem(0L, "Hillary", "Clinton", 0, "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The only US first lady ever to have sought elective office.");
            LoadDatabase loadNames = new LoadDatabase();
            loadNames.loadDB();
            nameReturnedCounter = 10;
            editor.putString("savedNameReturnedCounter", String.valueOf(nameReturnedCounter));
            editor.commit();
        }
        else if (nameReturnedCounter > 9) {
            List<HangmanNameItem> names = nextTenNamesFromDB();
            name = (HangmanNameItem) names.get(0);
            nameReturnedCounter = 1;
            editor.putString("savedNameReturnedCounter", String.valueOf(nameReturnedCounter));
            editor.commit();
        }
        else if (nameReturnedCounter < 10) {
            name = (HangmanNameItem) names.get(nameReturnedCounter);
            nameReturnedCounter++;
            editor.putString("savedNameReturnedCounter", String.valueOf(nameReturnedCounter));
            editor.commit();
        }

        return name;
    }

    public List<HangmanNameItem> nextTenNamesFromDB () {
        HangmanDBHelper helper = HangmanDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {
                HangmanDBContract.Names.COLUMN_NAME_FIRST_NAME,
                HangmanDBContract.Names.COLUMN_NAME_LAST_NAME,
                HangmanDBContract.Names.COLUMN_NAME_DISPLAYEDCOUNT,
                HangmanDBContract.Names.COLUMN_NAME_LINK_TO_WIKI,
                HangmanDBContract.Names.COLUMN_NAME_LINK_TO_IMAGE,
                HangmanDBContract.Names.COLUMN_NAME_DESCRIPTION,
                HangmanDBContract.Names.COLUMN_NAME_CLUE};
        //String sortOrder = HangmanDBContract.Names.COLUMN_NAME_LAST_NAME + " DESC";
        String whereClauseColumn = HangmanDBContract.Names.COLUMN_NAME_DISPLAYEDCOUNT;
        String [] whereClauseValue = {displayedCount};
        String limit = "10";

        Cursor c = db.query(
                HangmanDBContract.Names.TABLE_NAME,       // The table to query
                projection,                               // The columns to return
                whereClauseColumn,                        // The columns for the WHERE clause
                whereClauseValue,                         // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null //,                                      // The sort order
                //limit                                      // The limit
        );

        names = new ArrayList<HangmanNameItem>();

        while(c.moveToNext()){
            long id = c.getLong(c.getColumnIndex(HangmanDBContract.Names.COLUMN_NAME_ID));
            String fName = c.getString(c.getColumnIndex(HangmanDBContract.Names.COLUMN_NAME_FIRST_NAME));
            String lName = c.getString(c.getColumnIndex(HangmanDBContract.Names.COLUMN_NAME_LAST_NAME));
            Integer dCount = c.getInt(c.getColumnIndex(HangmanDBContract.Names.COLUMN_NAME_DISPLAYEDCOUNT));
            String lnkWiki = c.getString(c.getColumnIndex(HangmanDBContract.Names.COLUMN_NAME_LINK_TO_WIKI));
            String lnkImage = c.getString(c.getColumnIndex(HangmanDBContract.Names.COLUMN_NAME_LINK_TO_IMAGE));
            String Desc = c.getString(c.getColumnIndex(HangmanDBContract.Names.COLUMN_NAME_DESCRIPTION));
            String Clue = c.getString(c.getColumnIndex(HangmanDBContract.Names.COLUMN_NAME_CLUE));

            names.add(new HangmanNameItem(id, fName, lName, dCount, lnkWiki, lnkImage, Desc, Clue));
        }
        Log.i("HangmanDAO", names.size() + " names loaded");
        return names;
    }

    public void saveName(String[] nameArray) {
        HangmanDBHelper helper = HangmanDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        Log.i("HangmanDAO", "In saveName: name[0] " + nameArray[0]);
        Log.i("HangmanDAO", "In saveName: name[1] " + nameArray[1]);
        Log.i("HangmanDAO", "In saveName: name[2] " + String.valueOf(nameArray[2]));
        Log.i("HangmanDAO", "In saveName: name[3] " + nameArray[3]);
        Log.i("HangmanDAO", "In saveName: name[4] " + nameArray[4]);
        Log.i("HangmanDAO", "In saveName: name[5] " + nameArray[5]);
        Log.i("HangmanDAO", "In saveName: name[6] " + nameArray[6]);

        ContentValues values = new ContentValues();
        values.put(HangmanDBContract.Names.COLUMN_NAME_FIRST_NAME, nameArray[0]);
        values.put(HangmanDBContract.Names.COLUMN_NAME_LAST_NAME, nameArray[1]);
        values.put(HangmanDBContract.Names.COLUMN_NAME_DISPLAYEDCOUNT, nameArray[2]);
        values.put(HangmanDBContract.Names.COLUMN_NAME_LINK_TO_WIKI, nameArray[3]);
        values.put(HangmanDBContract.Names.COLUMN_NAME_LINK_TO_IMAGE, nameArray[4]);
        values.put(HangmanDBContract.Names.COLUMN_NAME_DESCRIPTION, nameArray[5]);
        values.put(HangmanDBContract.Names.COLUMN_NAME_CLUE, nameArray[6]);

        db.insert(HangmanDBContract.Names.TABLE_NAME, null, values);
    }

    public void updateName(Long id, Integer dispCount){
        HangmanDBHelper helper = HangmanDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HangmanDBContract.Names.COLUMN_NAME_DISPLAYEDCOUNT, dispCount+1);

        String selection = HangmanDBContract.Names.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.update(
                HangmanDBContract.Names.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
}
