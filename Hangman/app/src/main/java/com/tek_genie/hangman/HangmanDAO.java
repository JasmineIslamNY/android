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
    private Integer nameReturnedCounter;
    private List<HangmanNameItem> names;
    private SharedPreferences prefs;
    private Integer displayedCountForWhereClause;
    private Integer namesDisplayedSoFarCounter;
    private Integer numberOfNames = 10;
    private Integer namesInDB = 28;


    public HangmanDAO (Context context){
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        nameReturnedCounter = Integer.valueOf(prefs.getString("savedNameReturnedCounter", "-1"));
        displayedCountForWhereClause = Integer.valueOf(prefs.getString("savedDisplayedCountForWhereClause", "1"));
        namesDisplayedSoFarCounter = Integer.valueOf(prefs.getString("savedNamesDisplayedSoFarCounter", "0"));


    }

    //public HangmanDAO() {}

    public HangmanNameItem nextName (){
        HangmanNameItem name = null;

        if (nameReturnedCounter == -1) {
            Log.i("HangmanDAO", "Running : nameReturnedCounter == -1");
            name = new HangmanNameItem(0L, "Hillary", "Clinton", 0, "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The only US first lady ever to have sought elective office.");
            //LoadDatabase loadNames = new LoadDatabase();
            //loadNames.loadDB();
            //this.loadDB();
            //new UpdateStatisticsTask(this, gameResult, gameTime, nameItem.getID(), nameItem.getDisplayedCount()).execute();
            new LoadDatabaseTask(context).execute();
            nameReturnedCounter = numberOfNames;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("savedNameReturnedCounter", String.valueOf(nameReturnedCounter));
            editor.putString("savedDisplayedCountForWhereClause", String.valueOf(displayedCountForWhereClause));
            editor.putString("savedNamesDisplayedSoFarCounter", String.valueOf(namesDisplayedSoFarCounter));
            editor.commit();
        }
        else if (nameReturnedCounter > (numberOfNames -1)) {
            Log.i("HangmanDAO", "Running : nameReturnedCounter > 9");
            names = nextTenNamesFromDB();
            name = names.get(0);
        }
        else if (nameReturnedCounter < numberOfNames) {
            Log.i("HangmanDAO", "Running : nameReturnedCounter < 10");
            name = names.get(nameReturnedCounter);
            Log.i("HangmanDAO", "Counter: " + nameReturnedCounter);
            Log.i("HangmanDAO", "name: " + name.getFirstName() + " id: " + name.getID());
            nameReturnedCounter++;
        }

        return name;
    }

    public List<HangmanNameItem> nextTenNamesFromDB () {
        nameReturnedCounter = 1;
        displayedCountForWhereClause = Integer.valueOf(prefs.getString("savedDisplayedCountForWhereClause", "1"));
        namesDisplayedSoFarCounter = Integer.valueOf(prefs.getString("savedNamesDisplayedSoFarCounter", "0"));
        if (namesDisplayedSoFarCounter >= namesInDB) {
            displayedCountForWhereClause += 1;
            namesDisplayedSoFarCounter = 0;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("savedDisplayedCountForWhereClause", String.valueOf(displayedCountForWhereClause));
            editor.putString("savedNamesDisplayedSoFarCounter", String.valueOf(namesDisplayedSoFarCounter));
            editor.commit();

        }

        HangmanDBHelper helper = HangmanDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {
                HangmanDBContract.Names.COLUMN_NAME_ID,
                HangmanDBContract.Names.COLUMN_NAME_FIRST_NAME,
                HangmanDBContract.Names.COLUMN_NAME_LAST_NAME,
                HangmanDBContract.Names.COLUMN_NAME_DISPLAYEDCOUNT,
                HangmanDBContract.Names.COLUMN_NAME_LINK_TO_WIKI,
                HangmanDBContract.Names.COLUMN_NAME_LINK_TO_IMAGE,
                HangmanDBContract.Names.COLUMN_NAME_DESCRIPTION,
                HangmanDBContract.Names.COLUMN_NAME_CLUE};
        String whereClauseColumn = HangmanDBContract.Names.COLUMN_NAME_DISPLAYEDCOUNT+ " < ?";
        String [] whereClauseValue = {String.valueOf(displayedCountForWhereClause)};
        String limit = String.valueOf(numberOfNames);

        Cursor c = db.query(
                false,                                      //bool for distinct
                HangmanDBContract.Names.TABLE_NAME,       // The table to query
                projection,                               // The columns to return
                whereClauseColumn,                        // The columns for the WHERE clause
                whereClauseValue,                           // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null,                                     // The sort order
                limit                                       // The limit
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
        if (names.size() == 0){
            //this will be increased to 1 in nextTenNamesFromDB()
            displayedCountForWhereClause = 0;
            //this will make the if statement in nextTenNamesFromDB() true so displayedCountForWhereClause and namesDisplayedSoFarCounter are reset
            namesDisplayedSoFarCounter = namesInDB;
            //this will make nextName() method call's if (nameReturnedCounter > (numberOfNames -1)) statement true so nextTenNamesFromDB() is called the next time
            nameReturnedCounter = numberOfNames;
            names.add(new HangmanNameItem(0L, "Khaleda", "Zia", 0, "https://en.wikipedia.org/wiki/Khaleda_Zia", "https://upload.wikimedia.org/wikipedia/commons/8/8b/Khaleda_Zia_former_Prime_Minister_of_Bangladesh_cropped.jpg", "Begum Khaleda Zia (born 15 August 1945) is a Bangladeshi politician who was the Prime Minister of Bangladesh from 1991 to 1996 and again from 2001 to 2006. When she took office in 1991, she was the first woman in the country's history and second in the Muslim world (after Benazir Bhutto of Pakistan in 1988–1990) to head a democratic government as prime minister. Zia was the First Lady of Bangladesh during the presidency of her husband Ziaur Rahman. She is the chairperson and leader of the Bangladesh Nationalist Party (BNP) which was founded by Rahman in the late 1970s.\n After a military coup in 1982, led by Army Chief General Hussain Muhammad Ershad, Zia helped lead the continuing movement for democracy until the fall of military dictator Ershad in 1990. Khaleda became prime minister following the victory of the BNP in the 1991 general election. She also served briefly in the short-lived government in 1996, when other parties had boycotted the first election. In the next round of general elections of 1996, the Awami League came to power. Her party came to power again in 2001. She has been elected to five separate parliamentary constituencies in the general elections of 1991, 1996 and 2001.\n In its list of the 100 Most Powerful Women in the World, Forbes magazine ranked Zia at number 14 in 2004, number 29 in 2005, and number 33 in 2006.\n Following her government's term end in 2006, the scheduled January 2007 elections were delayed due to political violence and in-fighting, resulting in a bloodless military takeover of the caretaker government. During its interim rule, it charged Zia and her two sons with corruption.\n For the better part of the last two decades, Khaleda's chief rival has been Awami League leader Sheikh Hasina. The two women have alternated as non-interim prime ministers since 1991.","Second woman in the Muslim world to head a democratic government as prime minister."));
            new LoadDatabaseTask(context).execute();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("savedNameReturnedCounter", String.valueOf(nameReturnedCounter));
            editor.putString("savedDisplayedCountForWhereClause", String.valueOf(displayedCountForWhereClause));
            editor.putString("savedNamesDisplayedSoFarCounter", String.valueOf(namesDisplayedSoFarCounter));
            editor.commit();
        }
        else if (names.size() <  numberOfNames){
            int countToIncrease = numberOfNames - names.size();
            for (int i =0; i < countToIncrease; i++) {
                names.add(0, new HangmanNameItem());
            }
            nameReturnedCounter = countToIncrease;

        }
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

    /*
    public void loadDB() {
        String [][] names = new String[][]{
                {"One", "Clinton", "1", "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The only US first lady ever to have sought elective office"},
                {"Two", "Sanders", "0", "https://en.wikipedia.org/wiki/Bernie_Sanders", "https://upload.wikimedia.org/wikipedia/commons/d/de/Bernie_Sanders.jpg", "Bernard \"Bernie\" Sanders (born September 8, 1941) is an American politician and the junior United States senator from Vermont. He is a candidate for the Democratic nomination for President of the United States in the 2016 election. A member of the Democratic Party since 2015, Sanders had been the longest-serving independent in U.S. congressional history, though his caucusing with the Democrats entitled him to committee assignments and at times gave Democrats a majority. Sanders became the ranking minority member on the Senate Budget Committee in January 2015; he had previously served for two years as chair of the Senate Veterans' Affairs Committee. A self-proclaimed democratic socialist, Sanders is pro-labor and favors greater economic equality.", "Rose to national prominence following his 2010 filibuster against the proposed extension of the Bush tax cuts."},
                {"Three","Trump", "1", "https://en.wikipedia.org/wiki/Donald_Trump", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Donald_Trump_August_19%2C_2015_%28cropped%29.jpg", "Donald John Trump (born June 14, 1946) is an American businessman, politician, television personality, author, and the presumptive nominee of the Republican Party for President of the United States in the 2016 election. Trump is the Chairman and President of The Trump Organization, as well as the founder of the gaming and hotel enterprise Trump Entertainment Resorts (now owned by Carl Icahn).", "First campaigned for the U.S. presidency in 2000, and withdrew before any votes were cast, but afterwards won two Reform Party primaries."},
                {"Four", "Clinton", "1", "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The only US first lady ever to have sought elective office"},
                {"Five", "Sanders", "0", "https://en.wikipedia.org/wiki/Bernie_Sanders", "https://upload.wikimedia.org/wikipedia/commons/d/de/Bernie_Sanders.jpg", "Bernard \"Bernie\" Sanders (born September 8, 1941) is an American politician and the junior United States senator from Vermont. He is a candidate for the Democratic nomination for President of the United States in the 2016 election. A member of the Democratic Party since 2015, Sanders had been the longest-serving independent in U.S. congressional history, though his caucusing with the Democrats entitled him to committee assignments and at times gave Democrats a majority. Sanders became the ranking minority member on the Senate Budget Committee in January 2015; he had previously served for two years as chair of the Senate Veterans' Affairs Committee. A self-proclaimed democratic socialist, Sanders is pro-labor and favors greater economic equality.", "Rose to national prominence following his 2010 filibuster against the proposed extension of the Bush tax cuts."},
                {"Six","Trump", "1", "https://en.wikipedia.org/wiki/Donald_Trump", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Donald_Trump_August_19%2C_2015_%28cropped%29.jpg", "Donald John Trump (born June 14, 1946) is an American businessman, politician, television personality, author, and the presumptive nominee of the Republican Party for President of the United States in the 2016 election. Trump is the Chairman and President of The Trump Organization, as well as the founder of the gaming and hotel enterprise Trump Entertainment Resorts (now owned by Carl Icahn).", "First campaigned for the U.S. presidency in 2000, and withdrew before any votes were cast, but afterwards won two Reform Party primaries."},
                {"Seven", "Clinton", "1", "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The only US first lady ever to have sought elective office"},
                {"Eight", "Sanders", "0", "https://en.wikipedia.org/wiki/Bernie_Sanders", "https://upload.wikimedia.org/wikipedia/commons/d/de/Bernie_Sanders.jpg", "Bernard \"Bernie\" Sanders (born September 8, 1941) is an American politician and the junior United States senator from Vermont. He is a candidate for the Democratic nomination for President of the United States in the 2016 election. A member of the Democratic Party since 2015, Sanders had been the longest-serving independent in U.S. congressional history, though his caucusing with the Democrats entitled him to committee assignments and at times gave Democrats a majority. Sanders became the ranking minority member on the Senate Budget Committee in January 2015; he had previously served for two years as chair of the Senate Veterans' Affairs Committee. A self-proclaimed democratic socialist, Sanders is pro-labor and favors greater economic equality.", "Rose to national prominence following his 2010 filibuster against the proposed extension of the Bush tax cuts."},
                {"Nine","Trump", "1", "https://en.wikipedia.org/wiki/Donald_Trump", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Donald_Trump_August_19%2C_2015_%28cropped%29.jpg", "Donald John Trump (born June 14, 1946) is an American businessman, politician, television personality, author, and the presumptive nominee of the Republican Party for President of the United States in the 2016 election. Trump is the Chairman and President of The Trump Organization, as well as the founder of the gaming and hotel enterprise Trump Entertainment Resorts (now owned by Carl Icahn).", "First campaigned for the U.S. presidency in 2000, and withdrew before any votes were cast, but afterwards won two Reform Party primaries."},
                {"Ten", "Clinton", "1", "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The only US first lady ever to have sought elective office"},
                {"Eleven", "Sanders", "0", "https://en.wikipedia.org/wiki/Bernie_Sanders", "https://upload.wikimedia.org/wikipedia/commons/d/de/Bernie_Sanders.jpg", "Bernard \"Bernie\" Sanders (born September 8, 1941) is an American politician and the junior United States senator from Vermont. He is a candidate for the Democratic nomination for President of the United States in the 2016 election. A member of the Democratic Party since 2015, Sanders had been the longest-serving independent in U.S. congressional history, though his caucusing with the Democrats entitled him to committee assignments and at times gave Democrats a majority. Sanders became the ranking minority member on the Senate Budget Committee in January 2015; he had previously served for two years as chair of the Senate Veterans' Affairs Committee. A self-proclaimed democratic socialist, Sanders is pro-labor and favors greater economic equality.", "Rose to national prominence following his 2010 filibuster against the proposed extension of the Bush tax cuts."},
                {"Twelve","Trump", "1", "https://en.wikipedia.org/wiki/Donald_Trump", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Donald_Trump_August_19%2C_2015_%28cropped%29.jpg", "Donald John Trump (born June 14, 1946) is an American businessman, politician, television personality, author, and the presumptive nominee of the Republican Party for President of the United States in the 2016 election. Trump is the Chairman and President of The Trump Organization, as well as the founder of the gaming and hotel enterprise Trump Entertainment Resorts (now owned by Carl Icahn).", "First campaigned for the U.S. presidency in 2000, and withdrew before any votes were cast, but afterwards won two Reform Party primaries."},
                {"13", "Clinton", "1", "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The only US first lady ever to have sought elective office"},
                {"14", "Sanders", "0", "https://en.wikipedia.org/wiki/Bernie_Sanders", "https://upload.wikimedia.org/wikipedia/commons/d/de/Bernie_Sanders.jpg", "Bernard \"Bernie\" Sanders (born September 8, 1941) is an American politician and the junior United States senator from Vermont. He is a candidate for the Democratic nomination for President of the United States in the 2016 election. A member of the Democratic Party since 2015, Sanders had been the longest-serving independent in U.S. congressional history, though his caucusing with the Democrats entitled him to committee assignments and at times gave Democrats a majority. Sanders became the ranking minority member on the Senate Budget Committee in January 2015; he had previously served for two years as chair of the Senate Veterans' Affairs Committee. A self-proclaimed democratic socialist, Sanders is pro-labor and favors greater economic equality.", "Rose to national prominence following his 2010 filibuster against the proposed extension of the Bush tax cuts."},
                {"15","Trump", "1", "https://en.wikipedia.org/wiki/Donald_Trump", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Donald_Trump_August_19%2C_2015_%28cropped%29.jpg", "Donald John Trump (born June 14, 1946) is an American businessman, politician, television personality, author, and the presumptive nominee of the Republican Party for President of the United States in the 2016 election. Trump is the Chairman and President of The Trump Organization, as well as the founder of the gaming and hotel enterprise Trump Entertainment Resorts (now owned by Carl Icahn).", "First campaigned for the U.S. presidency in 2000, and withdrew before any votes were cast, but afterwards won two Reform Party primaries."},
                {"16", "Clinton", "1", "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The only US first lady ever to have sought elective office"},
                {"17", "Sanders", "0", "https://en.wikipedia.org/wiki/Bernie_Sanders", "https://upload.wikimedia.org/wikipedia/commons/d/de/Bernie_Sanders.jpg", "Bernard \"Bernie\" Sanders (born September 8, 1941) is an American politician and the junior United States senator from Vermont. He is a candidate for the Democratic nomination for President of the United States in the 2016 election. A member of the Democratic Party since 2015, Sanders had been the longest-serving independent in U.S. congressional history, though his caucusing with the Democrats entitled him to committee assignments and at times gave Democrats a majority. Sanders became the ranking minority member on the Senate Budget Committee in January 2015; he had previously served for two years as chair of the Senate Veterans' Affairs Committee. A self-proclaimed democratic socialist, Sanders is pro-labor and favors greater economic equality.", "Rose to national prominence following his 2010 filibuster against the proposed extension of the Bush tax cuts."},
                {"18","Trump", "1", "https://en.wikipedia.org/wiki/Donald_Trump", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Donald_Trump_August_19%2C_2015_%28cropped%29.jpg", "Donald John Trump (born June 14, 1946) is an American businessman, politician, television personality, author, and the presumptive nominee of the Republican Party for President of the United States in the 2016 election. Trump is the Chairman and President of The Trump Organization, as well as the founder of the gaming and hotel enterprise Trump Entertainment Resorts (now owned by Carl Icahn).", "First campaigned for the U.S. presidency in 2000, and withdrew before any votes were cast, but afterwards won two Reform Party primaries."},
                {"19", "Clinton", "1", "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The only US first lady ever to have sought elective office"},
                {"20", "Sanders", "0", "https://en.wikipedia.org/wiki/Bernie_Sanders", "https://upload.wikimedia.org/wikipedia/commons/d/de/Bernie_Sanders.jpg", "Bernard \"Bernie\" Sanders (born September 8, 1941) is an American politician and the junior United States senator from Vermont. He is a candidate for the Democratic nomination for President of the United States in the 2016 election. A member of the Democratic Party since 2015, Sanders had been the longest-serving independent in U.S. congressional history, though his caucusing with the Democrats entitled him to committee assignments and at times gave Democrats a majority. Sanders became the ranking minority member on the Senate Budget Committee in January 2015; he had previously served for two years as chair of the Senate Veterans' Affairs Committee. A self-proclaimed democratic socialist, Sanders is pro-labor and favors greater economic equality.", "Rose to national prominence following his 2010 filibuster against the proposed extension of the Bush tax cuts."},
                {"21","Trump", "1", "https://en.wikipedia.org/wiki/Donald_Trump", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Donald_Trump_August_19%2C_2015_%28cropped%29.jpg", "Donald John Trump (born June 14, 1946) is an American businessman, politician, television personality, author, and the presumptive nominee of the Republican Party for President of the United States in the 2016 election. Trump is the Chairman and President of The Trump Organization, as well as the founder of the gaming and hotel enterprise Trump Entertainment Resorts (now owned by Carl Icahn).", "First campaigned for the U.S. presidency in 2000, and withdrew before any votes were cast, but afterwards won two Reform Party primaries."},
                {"22", "Clinton", "1", "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The only US first lady ever to have sought elective office"},
                {"23", "Sanders", "0", "https://en.wikipedia.org/wiki/Bernie_Sanders", "https://upload.wikimedia.org/wikipedia/commons/d/de/Bernie_Sanders.jpg", "Bernard \"Bernie\" Sanders (born September 8, 1941) is an American politician and the junior United States senator from Vermont. He is a candidate for the Democratic nomination for President of the United States in the 2016 election. A member of the Democratic Party since 2015, Sanders had been the longest-serving independent in U.S. congressional history, though his caucusing with the Democrats entitled him to committee assignments and at times gave Democrats a majority. Sanders became the ranking minority member on the Senate Budget Committee in January 2015; he had previously served for two years as chair of the Senate Veterans' Affairs Committee. A self-proclaimed democratic socialist, Sanders is pro-labor and favors greater economic equality.", "Rose to national prominence following his 2010 filibuster against the proposed extension of the Bush tax cuts."},
                {"24","Trump", "1", "https://en.wikipedia.org/wiki/Donald_Trump", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Donald_Trump_August_19%2C_2015_%28cropped%29.jpg", "Donald John Trump (born June 14, 1946) is an American businessman, politician, television personality, author, and the presumptive nominee of the Republican Party for President of the United States in the 2016 election. Trump is the Chairman and President of The Trump Organization, as well as the founder of the gaming and hotel enterprise Trump Entertainment Resorts (now owned by Carl Icahn).", "First campaigned for the U.S. presidency in 2000, and withdrew before any votes were cast, but afterwards won two Reform Party primaries."},
                {"25", "Clinton", "1", "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The only US first lady ever to have sought elective office"},
                {"26", "Sanders", "0", "https://en.wikipedia.org/wiki/Bernie_Sanders", "https://upload.wikimedia.org/wikipedia/commons/d/de/Bernie_Sanders.jpg", "Bernard \"Bernie\" Sanders (born September 8, 1941) is an American politician and the junior United States senator from Vermont. He is a candidate for the Democratic nomination for President of the United States in the 2016 election. A member of the Democratic Party since 2015, Sanders had been the longest-serving independent in U.S. congressional history, though his caucusing with the Democrats entitled him to committee assignments and at times gave Democrats a majority. Sanders became the ranking minority member on the Senate Budget Committee in January 2015; he had previously served for two years as chair of the Senate Veterans' Affairs Committee. A self-proclaimed democratic socialist, Sanders is pro-labor and favors greater economic equality.", "Rose to national prominence following his 2010 filibuster against the proposed extension of the Bush tax cuts."},
                {"27","Trump", "1", "https://en.wikipedia.org/wiki/Donald_Trump", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Donald_Trump_August_19%2C_2015_%28cropped%29.jpg", "Donald John Trump (born June 14, 1946) is an American businessman, politician, television personality, author, and the presumptive nominee of the Republican Party for President of the United States in the 2016 election. Trump is the Chairman and President of The Trump Organization, as well as the founder of the gaming and hotel enterprise Trump Entertainment Resorts (now owned by Carl Icahn).", "First campaigned for the U.S. presidency in 2000, and withdrew before any votes were cast, but afterwards won two Reform Party primaries."},
                {"28", "Clinton", "1", "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The only US first lady ever to have sought elective office"},
                {"29", "Sanders", "0", "https://en.wikipedia.org/wiki/Bernie_Sanders", "https://upload.wikimedia.org/wikipedia/commons/d/de/Bernie_Sanders.jpg", "Bernard \"Bernie\" Sanders (born September 8, 1941) is an American politician and the junior United States senator from Vermont. He is a candidate for the Democratic nomination for President of the United States in the 2016 election. A member of the Democratic Party since 2015, Sanders had been the longest-serving independent in U.S. congressional history, though his caucusing with the Democrats entitled him to committee assignments and at times gave Democrats a majority. Sanders became the ranking minority member on the Senate Budget Committee in January 2015; he had previously served for two years as chair of the Senate Veterans' Affairs Committee. A self-proclaimed democratic socialist, Sanders is pro-labor and favors greater economic equality.", "Rose to national prominence following his 2010 filibuster against the proposed extension of the Bush tax cuts."},
                {"30","Trump", "1", "https://en.wikipedia.org/wiki/Donald_Trump", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Donald_Trump_August_19%2C_2015_%28cropped%29.jpg", "Donald John Trump (born June 14, 1946) is an American businessman, politician, television personality, author, and the presumptive nominee of the Republican Party for President of the United States in the 2016 election. Trump is the Chairman and President of The Trump Organization, as well as the founder of the gaming and hotel enterprise Trump Entertainment Resorts (now owned by Carl Icahn).", "First campaigned for the U.S. presidency in 2000, and withdrew before any votes were cast, but afterwards won two Reform Party primaries."},
                {"31", "Clinton", "1", "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The only US first lady ever to have sought elective office"},
                {"32", "Sanders", "0", "https://en.wikipedia.org/wiki/Bernie_Sanders", "https://upload.wikimedia.org/wikipedia/commons/d/de/Bernie_Sanders.jpg", "Bernard \"Bernie\" Sanders (born September 8, 1941) is an American politician and the junior United States senator from Vermont. He is a candidate for the Democratic nomination for President of the United States in the 2016 election. A member of the Democratic Party since 2015, Sanders had been the longest-serving independent in U.S. congressional history, though his caucusing with the Democrats entitled him to committee assignments and at times gave Democrats a majority. Sanders became the ranking minority member on the Senate Budget Committee in January 2015; he had previously served for two years as chair of the Senate Veterans' Affairs Committee. A self-proclaimed democratic socialist, Sanders is pro-labor and favors greater economic equality.", "Rose to national prominence following his 2010 filibuster against the proposed extension of the Bush tax cuts."},
                {"33","Trump", "1", "https://en.wikipedia.org/wiki/Donald_Trump", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Donald_Trump_August_19%2C_2015_%28cropped%29.jpg", "Donald John Trump (born June 14, 1946) is an American businessman, politician, television personality, author, and the presumptive nominee of the Republican Party for President of the United States in the 2016 election. Trump is the Chairman and President of The Trump Organization, as well as the founder of the gaming and hotel enterprise Trump Entertainment Resorts (now owned by Carl Icahn).", "First campaigned for the U.S. presidency in 2000, and withdrew before any votes were cast, but afterwards won two Reform Party primaries."},
                {"34", "Clinton", "1", "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The only US first lady ever to have sought elective office"},
                {"35", "Sanders", "0", "https://en.wikipedia.org/wiki/Bernie_Sanders", "https://upload.wikimedia.org/wikipedia/commons/d/de/Bernie_Sanders.jpg", "Bernard \"Bernie\" Sanders (born September 8, 1941) is an American politician and the junior United States senator from Vermont. He is a candidate for the Democratic nomination for President of the United States in the 2016 election. A member of the Democratic Party since 2015, Sanders had been the longest-serving independent in U.S. congressional history, though his caucusing with the Democrats entitled him to committee assignments and at times gave Democrats a majority. Sanders became the ranking minority member on the Senate Budget Committee in January 2015; he had previously served for two years as chair of the Senate Veterans' Affairs Committee. A self-proclaimed democratic socialist, Sanders is pro-labor and favors greater economic equality.", "Rose to national prominence following his 2010 filibuster against the proposed extension of the Bush tax cuts."},
                {"36","Trump", "1", "https://en.wikipedia.org/wiki/Donald_Trump", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Donald_Trump_August_19%2C_2015_%28cropped%29.jpg", "Donald John Trump (born June 14, 1946) is an American businessman, politician, television personality, author, and the presumptive nominee of the Republican Party for President of the United States in the 2016 election. Trump is the Chairman and President of The Trump Organization, as well as the founder of the gaming and hotel enterprise Trump Entertainment Resorts (now owned by Carl Icahn).", "First campaigned for the U.S. presidency in 2000, and withdrew before any votes were cast, but afterwards won two Reform Party primaries."}
        };
        for (int i=0; i < names.length; i++) {
            Log.i("LoadDatabase", "In loadDB: name[0] " + names[i][0]);
            Log.i("LoadDatabase", "In loadDB: name[1] " + names[i][1]);
            Log.i("LoadDatabase", "In loadDB: name[2] " + String.valueOf(names[i][2]));
            Log.i("LoadDatabase", "In loadDB: name[3] " + names[i][3]);
            Log.i("LoadDatabase", "In loadDB: name[4] " + names[i][4]);
            Log.i("LoadDatabase", "In loadDB: name[5] " + names[i][5]);
            Log.i("LoadDatabase", "In loadDB: name[6] " + names[i][6]);
            this.saveName(names[i]);
        }

    }
    */
}
