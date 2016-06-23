package com.tek_genie.hangman;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by jasmineislam on 6/21/16.
 */
public class HangmanNameItem implements Serializable {
    private Long id;
    private String text;
    private String status;
    private Calendar date;

    public HangmanNameItem(Long id, String text, String status, Calendar date){
        setId(id);
        setText(text);
        setStatus(status);
        setDate(date);
    }

    public String getText() {
            return this.text;
        }

    public long getId() {
            return id;
        }

    public void setId(Long id) {
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
