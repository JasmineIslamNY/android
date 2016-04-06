package com.tek_genie.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jasmineislam on 4/5/16.
 */
public class NoteDAO {

    private Context context;

    public NoteDAO(Context context){
        this.context = context;
    }

    public void save(NoteListItem note) {
        NotesDBHelper helper = NotesDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NotesDBContract.Note.COLUMN_NAME_NOTE_TEXT, note.getText());
        values.put(NotesDBContract.Note.COLUMN_NAME_STATUS, note.getStatus());
        values.put(NotesDBContract.Note.COLUMN_NAME_NOTE_DATE, (note.getDate().getTimeInMillis()/1000));

        db.insert(NotesDBContract.Note.TABLE_NAME, null, values);
    }

    public List<NoteListItem> list(){
        NotesDBHelper helper = NotesDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {
                NotesDBContract.Note.COLUMN_NAME_NOTE_TEXT,
                NotesDBContract.Note.COLUMN_NAME_STATUS,
                NotesDBContract.Note.COLUMN_NAME_NOTE_DATE};
        String sortOrder = NotesDBContract.Note.COLUMN_NAME_NOTE_DATE + " DESC";

        Cursor c = db.query(
                NotesDBContract.Note.TABLE_NAME,          // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<NoteListItem> notes = new ArrayList<NoteListItem>();

        while(c.moveToNext()){
            String text = c.getString(
                    c.getColumnIndex(NotesDBContract.Note.COLUMN_NAME_NOTE_TEXT));
            String status = c.getString(c.getColumnIndex(
                    NotesDBContract.Note.COLUMN_NAME_STATUS));
            Calendar date = new GregorianCalendar();
            date.setTimeInMillis(c.getLong(c.getColumnIndex(
                    NotesDBContract.Note.COLUMN_NAME_NOTE_DATE)) * 1000);

            notes.add(new NoteListItem(text, status, date));
        }
        return notes;
    }
}
