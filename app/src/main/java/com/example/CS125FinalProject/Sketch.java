package com.example.CS125FinalProject;
import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;

/**This can be seen as the "Main Method" of processing.
 *  settings() is ran once before everything.
 *  setup() runs immediately after. Once.
 *  draw() runs a set number of times per second. */
public class Sketch extends PApplet {
    static double GLOBAL_GRAVITY = 1;
    static boolean debugMode = true; //affects display of hitboxes
    static boolean rightPressed = false;
    static boolean leftPressed = false;

    //TODO: REMOVE BELOW [Used for testing]
    private Character player = new Character();
    private Platform platform0 = new Platform(300,1000, 400, 200);
    private Room room0 = new Room(new ArrayList<Environment>(Arrays.asList(platform0)), (new ArrayList<Character>(Arrays.asList(player))));
    private ArrayList<Room> rooms = new ArrayList<>(Arrays.asList(room0));
    private RoomManager roomManager = new RoomManager(rooms);
    //TODO: REMOVE ABOVE [Used for testing]

    public void settings() {
        size(displayWidth, displayHeight);
    }
    public void setup() {
        //orientation(LANDSCAPE);
        smooth(0);
        background(255,0,0); //red for debugging purposes (if you can see it, it's no good)

    }

    public void draw() {
        fill(255, 255, 255);    //sets fill color to white
        stroke(255, 255, 255);  //sets edge color to white
        rect(0, 0, displayWidth, displayHeight);   //draws white rectangle the size of the canvas.
        roomManager.run();




    }

    public RoomManager getRoomManager() {
        return roomManager;
    }

    Character getPlayer() {
        return player;
    }


}

