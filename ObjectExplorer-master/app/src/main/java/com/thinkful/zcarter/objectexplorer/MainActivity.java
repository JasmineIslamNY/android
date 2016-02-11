package com.thinkful.zcarter.objectexplorer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;

// This class handles output of log to the emulator screen
class Screen {
    // static fields
    static TextView textView;
    static String text = new String();

    public static void outputToScreen() {
        textView.setText(text);
    }

    public static void log(String textToLog) {
        text += textToLog + "\n";
        textView.setText(text);
    }
}

abstract class Ball extends Observable {
    public abstract void roll();
}

class Football extends Ball {

    // Note the @Override is a compiler directive.
    // It is not required, but is a best practice to use
    // for your own benefit
    @Override
    public void roll() {
        Screen.log("This football is rolling");
        this.setChanged();
        this.notifyObservers();
    }
}

class Referee implements Observer {

    @Override
    public void update(Observable observable, Object data) {
        String[] parts = observable.getClass().toString().split("\\.");
        String ballClass = parts[parts.length-1];
        Screen.log("The " + ballClass + " has been changed.");

    }
}

abstract class Baseball extends Ball {
    float speed = 1f;
    String direction = "To the Catcher";

    // A no-argument constructor
    Baseball(){}

    public void pitching(float speed, String direction) {
        Screen.log("This ball is thrown at " + speed + " miles per hour in a direction: " + direction);
    }
}

class Curveball extends Baseball {
    int curveAmount;

    Curveball(int curveAmount) {
        super();
        this.curveAmount = curveAmount;
    }

    @Override
    public void pitching(float speed, String direction) {
        super.pitching(speed, direction);
        Screen.log(" ...and it has a curve of " + curveAmount);
    }

    @Override
    public void roll() {
        Screen.log("This hard ball is rolling");
        this.setChanged();
        this.notifyObservers();
    }
}

class Softball extends Baseball {
    // @Override
    public void pitch(){
        Log.i("Softball", "A soft ball is pitched underhand");
    }

    @Override
    public void roll() {
        Screen.log("This soft ball is rolling");
        this.setChanged();
        this.notifyObservers();
    }
}

class BouncyBall {

    public void bounce() {
        Screen.log("The BouncyBall object bounces.");
    }
}

class SuperBall extends BouncyBall {

    @Override
    public void bounce() {
        Screen.log("The SuperBall object bounces super high.");
    }
}

class Hardball extends Baseball {


    public Hardball(int speed) {
        this.speed = speed;
    }

    //@Override
    public void pitch() {
        Screen.log("A hard ball is pitched overhand with speed" + this.speed);
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void roll() {
        Screen.log("This hard ball is rolling");
        this.setChanged();
        this.notifyObservers();
    }
}

//-------------------------------------------------------------------
// This space is for students to define classes in Thinkful Unit 2
// Alternatively, create classes in a new file in the same package,
// and they will be available here

//-------------------------------------------------------------------


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Screen.textView = (TextView) this.findViewById(R.id.textView);
        Button startButton = (Button)this.findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Screen.outputToScreen();
            }
        });
        this.playBall();
    }

    public void playBall() {
        // Students experiment with their classes here by instantiating their objects
        // and calling methods on those objects
        // example using the Football class:
        //Football football = new Football();
        Hardball firstGame = new Hardball(15);
        firstGame.pitch();
        SuperBall superBall = new SuperBall();
        superBall.bounce();
        Curveball x = new Curveball(3);
        x.pitching(2, "hit the ground");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
