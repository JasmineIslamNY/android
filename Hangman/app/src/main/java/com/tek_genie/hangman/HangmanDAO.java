package com.tek_genie.hangman;

import java.io.Serializable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jasmineislam on 5/18/16.
 */
public class HangmanDAO implements Serializable {
    private String [][] names;
    private Integer gamesWon = 0;
    private Integer gamesPlayed = 0;
    private Context context;

    public HangmanDAO (Context context){
        this.context = context;
    }

    public HangmanDAO() {
        //first name, last name, displayed (0/1), link to wikipedia, link to image, description, clue
        this.names = new String[][]{
                {"Hillary", "Clinton", "0", "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The only US first lady ever to have sought elective office"},
                {"Bernie", "Sanders", "0", "https://en.wikipedia.org/wiki/Bernie_Sanders", "https://upload.wikimedia.org/wikipedia/commons/d/de/Bernie_Sanders.jpg", "Bernard \"Bernie\" Sanders (born September 8, 1941) is an American politician and the junior United States senator from Vermont. He is a candidate for the Democratic nomination for President of the United States in the 2016 election. A member of the Democratic Party since 2015, Sanders had been the longest-serving independent in U.S. congressional history, though his caucusing with the Democrats entitled him to committee assignments and at times gave Democrats a majority. Sanders became the ranking minority member on the Senate Budget Committee in January 2015; he had previously served for two years as chair of the Senate Veterans' Affairs Committee. A self-proclaimed democratic socialist, Sanders is pro-labor and favors greater economic equality.", "Rose to national prominence following his 2010 filibuster against the proposed extension of the Bush tax cuts."},
                {"Donald","Trump", "0", "https://en.wikipedia.org/wiki/Donald_Trump", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Donald_Trump_August_19%2C_2015_%28cropped%29.jpg", "Donald John Trump (born June 14, 1946) is an American businessman, politician, television personality, author, and the presumptive nominee of the Republican Party for President of the United States in the 2016 election. Trump is the Chairman and President of The Trump Organization, as well as the founder of the gaming and hotel enterprise Trump Entertainment Resorts (now owned by Carl Icahn).", "First campaigned for the U.S. presidency in 2000, and withdrew before any votes were cast, but afterwards won two Reform Party primaries."}
        };

    }

    public String[] nextName (){
        String[] name = null;
        int i = 0;
        while (name == null && i < names.length) {
            if (names[i][2] == "0") {
                names[i][2] = "1";
                name = names[i];

            }
            i++;
            }
        if (name == null){
            name = names[0];
        }
        return name;
    }

    public void gameCompleted (int didYouWin) {
        this.gamesPlayed++;
        if (didYouWin == 1) {
            this.gamesWon++;
        }

    }

    public String gamesWonTotal () {
        String returnWonTotal =  this.gamesWon + " / " + this.gamesPlayed;
        return returnWonTotal;
    }
}
