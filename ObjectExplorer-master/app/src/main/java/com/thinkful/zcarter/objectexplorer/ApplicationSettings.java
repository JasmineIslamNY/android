package com.thinkful.zcarter.objectexplorer;

/**
 * Created by jasmineislam on 2/17/16.
 */
public class ApplicationSettings {
    private static ApplicationSettings ourInstance = new ApplicationSettings();
    public String gameDifficulty;
    public String homeTeamName;
    public String awayTeamName;
    int numberOfBallsInGame;

    public static ApplicationSettings getInstance() {
        return ourInstance;
    }

    // This private constructor cannot be accessed from outside the class
    private ApplicationSettings() {
        this.gameDifficulty = "easy";
        this.homeTeamName = "redSocks";
        this.awayTeamName = "blueSocks";
        this.numberOfBallsInGame = 1;
    }
}