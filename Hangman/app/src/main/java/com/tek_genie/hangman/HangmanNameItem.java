package com.tek_genie.hangman;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by jasmineislam on 6/21/16.
 */
public class HangmanNameItem implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer displayedCount;
    private String linkToWiki;
    private String linkToImage;
    private String description;
    private String clue;


    public HangmanNameItem(Long id, String firstName, String lastName, Integer displayedCount,
                           String linkToWiki, String linkToImage, String description, String clue){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayedCount = displayedCount;
        this.linkToWiki = linkToWiki;
        this.linkToImage = linkToImage;
        this.description = description;
        this.clue = clue;
    }

    public String [] getName() {
        String [] name = {String.valueOf(Long id), firstName, lastName, String.valueOf(Integer displayedCount),
                    linkToWiki, linkToImage, description, clue};
        return name;
        }




}
