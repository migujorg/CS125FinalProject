package com.example.CS125FinalProject;

import android.media.MediaPlayer;

import java.util.ArrayList;

import processing.core.PFont;

/** This class manages rendering text on the canvas.*/
public class TextBox {
    private Rectangle box = new Rectangle();
    private String message;
    private int alignment;
    //private PFont font = Main.sketch.createFont("Arial", 100, true);
    private int currentChar;
    private int messageLength; //This is for optimization so it doesn't have to call message.length() a billion times.
    private boolean complete;
    private String color = "";
    PFont font;
    //TODO: Right now game pretty much only supports 1 font because it loads that font in the beginning
    public TextBox() {};

    public TextBox(double setX, double setY, String setMessage) {
        box.x = setX;
        box.y = setY;
        box.width = Main.sketch.displayWidth * 0.8;
        box.height = Main.sketch.displayHeight * 0.8;
        message = setMessage;
        messageLength = message.length();
        font = Main.sketch.createFont("thinPixel.ttf", 150, false);
    }

    public TextBox(String setMessage) { //By default it puts the message in the center of the screen!
        box.width = Main.sketch.displayWidth * 0.8;
        box.height = setMessage.length() * 3.1;
        box.x = (Main.sketch.displayWidth / 2) - (box.width) / 2;
        box.y = Main.sketch.displayHeight / 4;
        message = setMessage;
        messageLength = message.length();
        font = Main.sketch.createFont("thinPixel.ttf", 200, false);
    }

    /**
     * @param setX x coordinate
     * @param setY
     * @param setMessage
     * @param setFont
     * @param scale
     * @param isSmooth
     */
    public TextBox(double setX, double setY, String setMessage, String setFont, double scale, boolean isSmooth) {
        box.width = Main.sketch.displayWidth * 0.8;
        box.height = Main.sketch.displayHeight * 0.8;
        box.x = (Main.sketch.displayWidth / 2) - (box.width / 2) + setX;
        box.y = setY;
        message = setMessage;
        messageLength = message.length();
        font = Main.sketch.createFont(setFont, (float) scale, isSmooth);
    }

     TextBox(double setX, double setY, double setWidth, double setHeight, String setMessage) {
        box.width = setWidth;
        box.height = setHeight;
        box.x = (Main.sketch.displayWidth / 2) - (box.width / 2) + setX;
        box.y = setY;
        message = setMessage;
        messageLength = message.length();
        font = Main.sketch.createFont("thinPixel.ttf", (float) 125, false);
    }

    TextBox(double setX, double setY, double setWidth, double setHeight, String setMessage, int setAlignment, String setColor) {
        box.width = setWidth;
        box.height = setHeight;
        box.x = (Main.sketch.displayWidth / 2) - (box.width / 2) + setX;
        box.y = setY;
        message = setMessage;
        messageLength = message.length();
        if (setAlignment == Main.sketch.CENTER) {
            alignment = Main.sketch.CENTER;
        } else {
            alignment = Main.sketch.CORNER;
        }
        color = setColor;
        font = Main.sketch.createFont("thinPixel.ttf", (float) 125, false);


    }

    public TextBox(double setX, double setY, double setWidth, double setHeight, String setMessage, String setFont, int scale, boolean isSmooth) {
        box.x = setX;
        box.y = setY;
        box.width = setWidth;
        box.height = setHeight;
        message = setMessage;
        messageLength = message.length();
        font = Main.sketch.createFont(setFont, scale, isSmooth);
    }

    void run() {
        drawBackground();
        drawText();
    }

    void drawText() {
        slowText(2);
    }

    private void slowText(double delay) {
        if (currentChar < messageLength) { //Process stops once message in finished (to prevent lag)
            char thisChar = message.charAt(currentChar);
            if (Main.sketch.frameCount % delay == 0) {
                currentChar++;
                playSounds(thisChar);
            }
        } else {
            complete = true;
        }
        Main.sketch.stroke(0,0,0);
        Main.sketch.fill(0,0,0);
        Main.sketch.textFont(font);
        Main.sketch.textAlign(alignment, Main.sketch.CENTER);
        if (color.equals("not")) {
            Main.sketch.fill(0,255,0);
        }
        Main.sketch.text(message.substring(0, currentChar), (float) box.x, (float) box.y, (float) box.width, (float) box.height);

    }

    void drawBackground() {
        Main.sketch.rectMode(Main.sketch.CENTER);
        if (color.equals("orange")) {
            drawOrangeBox();
        } else if (color.equals("blue")) {
            drawBlueBox();
        } else if (color.equals("purple")) {
            drawPurpleBox();
        } else {
            drawTerminalBox();
        }
        Main.sketch.rectMode(Main.sketch.CORNER);
    }

    public boolean isComplete() {
        return complete;
    }

    private void drawOrangeBox() {
        Main.sketch.stroke(241, 83, 30);
        Main.sketch.fill(241, 83, 30);
        Main.sketch.rect((float) (box.x + (box.width / 2)), //Outermost
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.1),
                (float) (box.height * 1.2),
                20);
        Main.sketch.stroke(240, 64, 5);
        Main.sketch.fill(240, 64, 5);
        Main.sketch.rect((float) (box.x + (box.width / 2)), //Outermost
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.05),
                (float) (box.height * 1.1),
                20);
        Main.sketch.stroke(243, 120, 54);
        Main.sketch.fill(243, 120, 54);
        Main.sketch.rect((float) (box.x + (box.width / 2)), //Outermost
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.025),
                (float) (box.height * 1.05),
                20);
        Main.sketch.stroke(244, 130, 74);
        Main.sketch.fill(244, 130, 74);
        Main.sketch.rect((float) (box.x + (box.width / 2)), //Outermost
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width),
                (float) (box.height),
                20);
    }

    private void drawBlueBox() {
        Main.sketch.stroke(63, 81, 181);
        Main.sketch.fill(63, 81, 181);
        Main.sketch.rect((float) (box.x + (box.width / 2)), //Outermost
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.1),
                (float) (box.height * 1.2),
                20);
        Main.sketch.stroke(56, 72, 162);
        Main.sketch.fill(56, 72, 162);
        Main.sketch.rect((float) (box.x + (box.width / 2)), //Outermost
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.05),
                (float) (box.height * 1.1),
                20);
        Main.sketch.stroke(0, 168, 190);
        Main.sketch.fill(0, 168, 190);
        Main.sketch.rect((float) (box.x + (box.width / 2)), //Outermost
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.025),
                (float) (box.height * 1.05),
                20);
        Main.sketch.stroke(0, 187, 212);
        Main.sketch.fill(0, 187, 212);
        Main.sketch.rect((float) (box.x + (box.width / 2)), //Outermost
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width),
                (float) (box.height),
                20);
    }

    private void drawPurpleBox() {
        Main.sketch.stroke(166, 61, 184);
        Main.sketch.fill(166, 61, 184);
        Main.sketch.rect((float) (box.x + (box.width / 2)), //Outermost
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.1),
                (float) (box.height * 1.2),
                20);
        Main.sketch.stroke(156, 39, 176);
        Main.sketch.fill(156, 39, 176);
        Main.sketch.rect((float) (box.x + (box.width / 2)), //Outermost
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.05),
                (float) (box.height * 1.1),
                20);
        Main.sketch.stroke(198, 44, 225);
        Main.sketch.fill(198, 44, 225);
        Main.sketch.rect((float) (box.x + (box.width / 2)), //Outermost
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.025),
                (float) (box.height * 1.05),
                20);
        Main.sketch.stroke(221, 49, 251);
        Main.sketch.fill(221, 49, 251);
        Main.sketch.rect((float) (box.x + (box.width / 2)), //Outermost
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width),
                (float) (box.height),
                20);
    }

    private void drawTerminalBox() {
        Main.sketch.stroke(23, 87, 1);
        Main.sketch.fill(23, 87, 1);
        Main.sketch.rect((float) (box.x + (box.width / 2)),
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.1),
                (float) (box.height * 1.15),
                20);
        Main.sketch.stroke(18, 70, 1);
        Main.sketch.fill(18, 70, 1);
        Main.sketch.rect((float) (box.x + (box.width / 2)),
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.05),
                (float) (box.height * 1.1),
                20);
        Main.sketch.stroke(13, 50, 0);
        Main.sketch.fill(13, 50, 0);
        Main.sketch.rect((float) (box.x + (box.width / 2)),
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.025),
                (float) (box.height * 1.05),
                20);
    }

    private void playSounds(char thisChar) {
        if (color.equals("not") && thisChar != ' ' && Main.sketch.frameCount % 4 == 0) {
            ArrayList<MediaPlayer> terminalSounds = ((Sketch) Main.sketch).getTerminalSounds();
            terminalSounds.get(Main.sketch.frameCount % 8).start();
        }
    }

}
