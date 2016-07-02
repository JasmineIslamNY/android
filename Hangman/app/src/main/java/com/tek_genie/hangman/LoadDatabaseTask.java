package com.tek_genie.hangman;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


/**
 * Created by jasmineislam on 6/27/16.
 */
public class LoadDatabaseTask extends AsyncTask <String, Void, Void> {
    private Context context;
    private String [][] names = new String[][]{
            {"Barack", "Obama", "0", "https://en.wikipedia.org/wiki/Barack_Obama", "https://upload.wikimedia.org/wikipedia/commons/8/8d/President_Barack_Obama.jpg", "Barack Hussein Obama II (born August 4, 1961) is an American politician serving as the 44th President of the United States. He is the first African American to hold the office, as well as the first president born outside of the continental United States. Born in Honolulu, Hawaii, Obama is a graduate of Columbia University and Harvard Law School, where he served as president of the Harvard Law Review. He was a community organizer in Chicago before earning his law degree. He worked as a civil rights attorney and taught constitutional law at the University of Chicago Law School between 1992 and 2004. He served three terms representing the 13th District in the Illinois Senate from 1997 to 2004.", "2009 Nobel Peace Prize laureate."},
            {"Tony", "Blair", "0", "https://en.wikipedia.org/wiki/Tony_Blair", "https://upload.wikimedia.org/wikipedia/commons/a/ae/MSC_2014_Blair_Mueller_MSC2014_%28cropped%29.jpg","Anthony Charles Lynton Blair (born 6 May 1953), originally known as Anthony Blair, but later as Tony Blair, is a British Labour Party politician, who served as the Prime Minister of the United Kingdom (UK), from 1997 to 2007. From 1983 to 2007, Blair was the Member of Parliament (MP) for Sedgefield, and from 1994 to 2007, Blair was the Leader of the Labour Party. He now runs a consultancy business and performs charitable work.", "Strongly supported much of the foreign policy of U.S. President George W. Bush, and ensured that British Armed Forces participated in the 2001 invasion of Afghanistan and, more controversially, the 2003 invasion of Iraq."},
            {"Joe", "Biden", "0", "https://en.wikipedia.org/wiki/Joe_Biden", "https://upload.wikimedia.org/wikipedia/commons/e/ea/Official_portrait_of_Vice_President_Joe_Biden.jpg", "Joseph Robinette \"Joe\" Biden Jr. (born November 20, 1942) is an American politician who is the 47th and current Vice President of the United States, jointly elected twice with President Barack Obama, and in office since 2009. A member of the Democratic Party, Biden represented Delaware as a United States Senator from 1973 until becoming Vice President in 2009.", "First Delawarean to be Vice President of the United States."},
            {"Donald","Trump", "0", "https://en.wikipedia.org/wiki/Donald_Trump", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Donald_Trump_August_19%2C_2015_%28cropped%29.jpg", "Donald John Trump (born June 14, 1946) is an American businessman, politician, television personality, author, and the presumptive nominee of the Republican Party for President of the United States in the 2016 election. Trump is the Chairman and President of The Trump Organization, as well as the founder of the gaming and hotel enterprise Trump Entertainment Resorts (now owned by Carl Icahn).", "First campaigned for the U.S. presidency in 2000, and withdrew before any votes were cast, but afterwards won two Reform Party primaries."},
            {"David", "Cameron", "0", "https://en.wikipedia.org/wiki/David_Cameron", "https://upload.wikimedia.org/wikipedia/commons/2/21/David_Cameron_official.jpg", "David William Donald Cameron (born 9 October 1966) is a British politician. Cameron has served as Prime Minister of the United Kingdom since 2010, and as Member of Parliament for Witney since 2001. The Leader of the Conservative Party since 2005, Cameron identifies as a One-Nation Conservative, and has been associated with both economically liberal and socially liberal policies. After the referendum on leaving the European Union, Cameron announced that he would leave office by October 2016 (after a new Party leader is elected).", "Announced he would leave after Britain decided to leave."},
            {"George W", "Bush", "0", "https://en.wikipedia.org/wiki/George_W._Bush", "https://upload.wikimedia.org/wikipedia/commons/d/d4/George-W-Bush.jpeg", "George Walker Bush (born July 6, 1946) is an American politician who served as the 43rd President of the United States from 2001 to 2009 and 46th Governor of Texas from 1995 to 2000. The eldest son of Barbara and George H. W. Bush, he was born in New Haven, Connecticut. He co-owned the Texas Rangers baseball team before defeating Ann Richards in the 1994 Texas gubernatorial election. He was elected president in 2000 after a close and controversial election against Al Gore, becoming the fourth president to be elected while receiving fewer popular votes nationwide than an opponent.", "The second president to have been the son of a former president."},
            {"Bernie", "Sanders", "0", "https://en.wikipedia.org/wiki/Bernie_Sanders", "https://upload.wikimedia.org/wikipedia/commons/d/de/Bernie_Sanders.jpg", "Bernard \"Bernie\" Sanders (born September 8, 1941) is an American politician and the junior United States senator from Vermont. He is a candidate for the Democratic nomination for President of the United States in the 2016 election. A member of the Democratic Party since 2015, Sanders had been the longest-serving independent in U.S. congressional history, though his caucusing with the Democrats entitled him to committee assignments and at times gave Democrats a majority. Sanders became the ranking minority member on the Senate Budget Committee in January 2015; he had previously served for two years as chair of the Senate Veterans' Affairs Committee. A self-proclaimed democratic socialist, Sanders is pro-labor and favors greater economic equality.", "Rose to national prominence following his 2010 filibuster against the proposed extension of the Bush tax cuts."},
            {"Dick", "Cheney", "0", "https://en.wikipedia.org/wiki/Dick_Cheney", "https://upload.wikimedia.org/wikipedia/commons/8/88/46_Dick_Cheney_3x4.jpg", "Richard Bruce Cheney, generally known as Dick Cheney (born January 30, 1941) is an American politician and businessman who was the 46th Vice President of the United States from 2001 to 2009, under President George W. Bush. He served as the White House Chief of Staff, from 1975 to 1977 and the Secretary of Defense during the Presidency of George H. W. Bush.","Chairman and CEO of Halliburton Company from 1995 to 2000."},
            {"Al","Gore","0","https://en.wikipedia.org/wiki/Al_Gore", "https://upload.wikimedia.org/wikipedia/commons/c/c5/Al_Gore%2C_Vice_President_of_the_United_States%2C_official_portrait_1994.jpg", "Albert Arnold \"Al\" Gore Jr. (born March 31, 1948) is an American politician and environmentalist who served as the 45th Vice President of the United States from 1993 to 2001 under President Bill Clinton. Chosen as Clinton's running mate in their successful 1992 campaign, he was reelected in 1996. At the end of Clinton's second term, Gore was the Democratic Party's nominee for President in 2000. After leaving office, Gore remained prominent as an author and environmental activist, whose work in climate change activism earned him (jointly with the IPCC) the Nobel Peace Prize in 2007.", "Won the popular vote but lost in the Electoral College."},
            {"Bill", "Clinton", "0", "https://en.wikipedia.org/wiki/Bill_Clinton", "https://upload.wikimedia.org/wikipedia/commons/4/49/44_Bill_Clinton_3x4.jpg", "William Jefferson Clinton (born William Jefferson Blythe III, August 19, 1946) is an American politician who was the 42nd President of the United States from 1993 to 2001. Clinton was previously Governor of Arkansas from 1979 to 1981 and 1983 to 1992, and the Arkansas Attorney General from 1977 to 1979. A member of the Democratic Party, ideologically Clinton was a New Democrat, and many of his policies reflected a centrist \"Third Way\" philosophy of governance.","First Baby Boomer President."},
            {"Angela", "Merkel", "0", "https://en.wikipedia.org/wiki/Angela_Merkel", "https://upload.wikimedia.org/wikipedia/commons/9/94/Angela_Merkel_2013_%28cropped%29.jpg", "Angela Dorothea Merkel (born 17 July 1954) is a German politician and former research scientist. Merkel has been the Chancellor of Germany since 2005, and the leader of the Christian Democratic Union (CDU) since 2000.", "Named the most powerful woman in the world for a record ninth time by Forbes in May 2015."},
            {"Hillary", "Clinton", "0", "https://en.wikipedia.org/wiki/Hillary_Clinton", "https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg", "Hillary Diane Rodham Clinton (born October 26, 1947) is an American politician and a candidate for the Democratic presidential nomination in the 2016 election. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas for twelve years.", "The first female senator from New York"},
            {"Shinzo", "Abe", "0", "https://en.wikipedia.org/wiki/Shinz%C5%8D_Abe", "https://upload.wikimedia.org/wikipedia/commons/e/e9/Shinz%C5%8D_Abe_April_2015.jpg", "Shinzō Abe (born 21 September 1954) is the current Prime Minister of Japan, re-elected to the position in December 2012. Abe is also the President of the Liberal Democratic Party (LDP). Abe served for a year as Prime Minister, from 2006 to 2007. Hailing from a politically prominent family, at age 52, Abe became Japan's youngest post-war prime minister, and the first to be born after World War II, when he was elected by a special session of the National Diet in September 2006. Abe resigned on 12 September 2007, for health reasons. Abe was replaced by Yasuo Fukuda, beginning a string of five Prime Ministers, none of whom retained office for more than sixteen months, before Abe staged a political comeback.","Japanese PM whose name is part of a portmanteau with economics for his policies."}
    };

    public LoadDatabaseTask (Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        HangmanDAO dao = new HangmanDAO(context);
        for (int i=0; i < names.length; i++) {
            Log.i("LoadDatabase", "In loadDB: name[0] " + names[i][0]);
            Log.i("LoadDatabase", "In loadDB: name[1] " + names[i][1]);
            Log.i("LoadDatabase", "In loadDB: name[2] " + String.valueOf(names[i][2]));
            Log.i("LoadDatabase", "In loadDB: name[3] " + names[i][3]);
            Log.i("LoadDatabase", "In loadDB: name[4] " + names[i][4]);
            Log.i("LoadDatabase", "In loadDB: name[5] " + names[i][5]);
            Log.i("LoadDatabase", "In loadDB: name[6] " + names[i][6]);
            dao.saveName(names[i]);
        }
        return null;
    }

}



