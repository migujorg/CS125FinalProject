package com.example.CS125FinalProject;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;
import processing.core.PImage;

/**This can be seen as the "Main Method" of processing.
 *  settings() is ran once before everything.
 *  setup() runs immediately after. Once.
 *  draw() runs a set number of times per second. */
public class Sketch extends PApplet {
    /** Acceleration constant. yVelocity is increased by this many pixels-per-second each frame. */
    static double GLOBAL_GRAVITY = 2;
    /** Friction constant. Magnitude of xVelocity is decreased by this many pixels-per-second each frame it is on a surface. */
    static double FRICTION_COEFFICIENT = 0.5;
    /** Frame rate of the sketch. Will attempt to reach this frame rate, but may be lower if it cannot be achieved. */
    private static int FRAME_RATE = 60;
    /** Boolean used by developer to help debug things. When set to true turns on various display that are normally hidden. */
    static boolean debugMode = true; //affects display of hitboxes

    /** Is the right side of the screen pressed? Used for player control. */
    private boolean rightPressed = false;
    /** Is the left side of the screen pressed? Used for player control. */
    private boolean leftPressed = false;
    /** is the top of the screen pressed? Used for player control. */
    private boolean upPressed = false;

    //TODO: REMOVE BELOW [Used for testing]
    private Character player = new Character(true);
    private Platform platform0 = new Platform(200,1000, 1000, 200);
    private Platform platform1 = new Platform(800,800, 100, 200);
    private Platform platform2 = new Platform(100,800, 100, 200);
    private Room room0 = new Room(new ArrayList<Environment>(Arrays.asList(platform0, platform1, platform2)),
            (new ArrayList<Character>(Arrays.asList(player))));
    private ArrayList<Room> rooms = new ArrayList<>(Arrays.asList(room0));
    private RoomManager roomManager = new RoomManager(rooms);
    private RoomManager roomManager0;
    Context activityContext;
    //TODO: REMOVE ABOVE [Used for testing]

    //TODO: UNCOMMENT THE TWO LINES ABOVE AND FIX FileNotFoundException
    public Sketch(Context context){
        activityContext = context;
    }

    /**Settings for the screen. Runs once before everything*/
    public void settings() {
        size(displayWidth, displayHeight);
        smooth(0);
    }

    /**Runs once after settings(). */
    public void setup() {
        //orientation(LANDSCAPE);
        //activityContext.getResources().openRawResource(R.raw.game_setup);
        //truckTest = loadImage("truck.png");
        roomManager0 = JsonHandler.getRoomManager(activityContext);
        frameRate(FRAME_RATE);
        background(255,0,0); //red for debugging purposes (if you can see it, it's no good)

    }

    /**Runs FRAME_RATE times per second */
    public void draw() {
        //System.out.println(new File(".").getAbsoluteFile());
        refreshBackground();
        roomManager0.run();
        if (debugMode) {
            showTouch();
            //image(truckTest, 500,500, 500, 500);
        }
    }

    /**Shows which part of the screen is being detected as pressed. Used for debugging*/
    private void showTouch() {
        if (leftPressed) {
            fill(0,0,0, 100);
            stroke(0,0,0);
            rect(0, 0, (float) (displayWidth / 2.0), (float) displayHeight);
            if (upPressed) {
                rect(0, 0, (float) (displayWidth / 2.0) , (float) ((displayHeight - 300) / 2.0));
            }
        }
        if (rightPressed) {
            fill(0,0,0, 100);
            stroke(0,0,0);
            rect((float) (displayWidth / 2.0), 0, (float) (displayWidth / 2.0), displayHeight);
            if (upPressed) {
                rect( (float) (displayWidth / 2.0) , 0,  (float) (displayWidth / 2.0), (float) ((displayHeight - 300)/ 2.0));
            }
        }
    }

    /**Draws a white background. Required before each frame to prevent Tron-line drawing. */
    private void refreshBackground() {
        fill(255, 255, 255);    //sets fill color to white
        stroke(255, 255, 255);  //sets edge color to white
        rect(0, 0, displayWidth, displayHeight);   //draws white rectangle the size of the canvas.
    }

    //TODO: FIX TOUCH CONTROLS TO WORK WITH MULTITOUCH

    /**Runs when a touch is detected. Sets leftPressed and rightPressed accordingly. */
    public void touchStarted() {
        if (mouseX < displayWidth / 2) {
            leftPressed =  true;
        } else {
            rightPressed = true;
        }
        if (mouseY < (displayHeight - 300) / 2) {
            upPressed = true;
        }
        fill(0,0,0);
        stroke(0,0,0);
        rect(mouseX - 100, mouseY - 100, 200, 200);
    }

    /**Runs when a touch moves. Sets leftPressed and rightPressed accordingly. */
    public void touchMoved() {
        if (mouseX < displayWidth /2) {
            leftPressed = true;
            rightPressed = false;
        } else {
            rightPressed = true;
            leftPressed = false;
        }
        if (mouseY < (displayHeight - 300) / 2) {
            upPressed = true;
        } else {
            upPressed = false;
        }
    }

    /**Runs when a touch is released. Sets leftPressed and rightPressed accordingly. */
    public void touchEnded() {
        if (mouseX < displayWidth /2) {
            leftPressed = false;
        } else {
            rightPressed = false;
        }
        upPressed = false;
    }

    /**@return returns rightPressed. Used for player control. */
    boolean isRightPressed() {
        return rightPressed;
    }

    /**@return returns leftPressed. Used for player control. */
    boolean isLeftPressed() {
        return leftPressed;
    }

    boolean isUpPressed() { return upPressed; }

    /**@return returns roomManger. This getter is not currently used*/
    public RoomManager getRoomManager() {
        return roomManager;
    }

    /**@return returns the player Character*/
    Character getPlayer() {
        return player;
    }
}

