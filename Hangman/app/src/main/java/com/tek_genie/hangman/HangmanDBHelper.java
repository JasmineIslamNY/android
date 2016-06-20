package com.tek_genie.hangman;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jasmineislam on 6/16/16.
 */
public class HangmanDBHelper {
    private static HangmanDBHelper instance = null;

    public static HangmanDBHelper getInstance(Context context){
        if(instance == null ){
            instance = new HangmanDBHelper(context);
        }

        return instance;
    }

    private HangmanDBHelper(Context context) {
        super(context, HangmanDBContract.DATABASE_NAME, null, HangmanDBContract.DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HangmanDBContract.SQL_CREATE_NAMES);
        db.execSQL(HangmanDBContract.SQL_CREATE_GAMEINFO);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(HangmanDBContract.SQL_DELETE_NAMES);
        db.execSQL(HangmanDBContract.SQL_DELETE_GAMEINFO);
        onCreate(db);
    }
}
