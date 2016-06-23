package com.tek_genie.hangman;

import android.provider.BaseColumns;

/**
 * Created by jasmineislam on 6/16/16.
 */
public class HangmanDBContract {
    private HangmanDBContract() {}

    /*When you change the schema you need to update the version number*/
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "hangmandb";

    /*CREATE TABLE Statements*/
    public static final String SQL_CREATE_NAMES = String.format(
            "CREATE TABLE %s ( %s TEXT, %s TEXT, %s INTEGER, %s TEXT, %s TEXT, %s TEXT, %s TEXT )",
            Names.TABLE_NAME, Names.COLUMN_NAME_FIRST_NAME, Names.COLUMN_NAME_LAST_NAME, Names.COLUMN_NAME_DISPLAYEDCOUNT, Names.COLUMN_NAME_LINK_TO_WIKI, Names.COLUMN_NAME_LINK_TO_IMAGE, Names.COLUMN_NAME_DESCRIPTION, Names.COLUMN_NAME_CLUE);

    public static final String SQL_CREATE_GAMEINFO = String.format(
            "CREATE TABLE %s ( %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER)",
            GameInfo.TABLE_NAME, GameInfo.COLUMN_NAME_GAMES_WON, GameInfo.COLUMN_NAME_GAMES_PLAYED, GameInfo.COLUMN_NAME_DB_VERSION, GameInfo.COLUMN_NAME_AVERAGE_TIME, GameInfo.COLUMN_NAME_SHORTEST_TIME, GameInfo.COLUMN_NAME_LONGEST_TIME);

    /*DROP TABLE Statements*/
    public static final String SQL_DELETE_NAMES =
            String.format("DROP TABLE IF EXISTS %s", Names.TABLE_NAME);

    public static final String SQL_DELETE_GAMEINFO =
            String.format("DROP TABLE IF EXISTS %s", GameInfo.TABLE_NAME);

    //first name, last name, displayed (0/1), link to wikipedia, link to image, description, clue
    /* Inner class that defines the Names table */
    public static abstract class Names implements BaseColumns {
        public static final String TABLE_NAME = "names";
        public static final String COLUMN_NAME_ID = "rowid";
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_LAST_NAME = "last_name";
        public static final String COLUMN_NAME_DISPLAYEDCOUNT = "displayedcount";
        public static final String COLUMN_NAME_LINK_TO_WIKI = "link_to_wiki";
        public static final String COLUMN_NAME_LINK_TO_IMAGE = "link_to_image";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_CLUE = "clue";

    }

    /* Inner class that defines the GameInfo table */
    public static abstract class GameInfo implements BaseColumns {
        public static final String TABLE_NAME = "gameinfo";
        public static final String COLUMN_NAME_ID = "rowid";
        public static final String COLUMN_NAME_GAMES_WON = "games_won";
        public static final String COLUMN_NAME_GAMES_PLAYED = "games_played";
        public static final String COLUMN_NAME_DB_VERSION = "db_version";
        public static final String COLUMN_NAME_AVERAGE_TIME = "average_time";
        public static final String COLUMN_NAME_SHORTEST_TIME = "shortest_time";
        public static final String COLUMN_NAME_LONGEST_TIME = "longest_time";

    }

}
