package com.tek_genie.whosfirst;

import android.util.Log;

import java.util.Random;

/**
 * Created by jasmineislam on 4/25/16.
 */
public class DetermineWhosFirst {
    public String[] nameList = new String[]{"Dana", "Harpreet", "Hatice", "Jennifer", "Maki"};
    Random r = new Random();

    public DetermineWhosFirst(){}

    public String whatsTheName(){
        int number = r.nextInt(5);
        //Log.i("Log", "The random number is " + number);
        String name = nameList[number];
        //Log.i("Log", "The name is " + name);
        return name;

    }
}
