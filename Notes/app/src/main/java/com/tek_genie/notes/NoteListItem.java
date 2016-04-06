package com.tek_genie.notes;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by jasmineislam on 2/24/16.
 */
public class NoteListItem implements Serializable {
    private long id;
    private String text;
    private String status;
    private Calendar date;

    public NoteListItem(String text) {
        this(text, "Open", Calendar.getInstance());
    }

    public NoteListItem(String text, String status, Calendar date){
        this.text = text;
        this.status = status;
        this.date = date;
    }

    public String getText() {
        return this.text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}