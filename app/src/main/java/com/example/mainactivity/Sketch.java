package com.example.mainactivity;
import processing.core.PApplet;

/**This can be seen as the "Main Method" of processing.
 *  settings() is ran once before everything.
 *  setup() runs immediately after. Once.
 *  draw() runs a set number of times per second. */
public class Sketch extends PApplet{
    static double GLOBAL_GRAVITY = 1;
    static boolean rightPressed = false;
    static boolean leftPressed = false;
    private Character testCharacter = new Character();

    public void settings() {
        size(displayWidth, displayHeight);
    }
    public void setup() {
        smooth(0);
        background(255);

    }

    public void draw() {
        fill(255, 255, 255);    //sets fill color to white
        stroke(255, 255, 255);  //sets edge color to white
        rect(0, 0, displayWidth, displayHeight);   //draws white rectangle the size of the canvas.
        testCharacter.run();




    }
}

