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
    private String partialMessage = "";
    private String messageSoFar = "";
    private boolean complete;
    private String color = "";
    PFont font;
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
    private void specialText(double delay) {
        if (currentChar < messageLength - 1) {
            char thisChar = message.charAt(currentChar);
            if (Main.sketch.frameCount % delay == 0) {
                partialMessage += message.charAt(currentChar);
                //messageSoFar += message.charAt(currentChar);
                currentChar++;
                playSounds(thisChar);
            }
        } else {
            complete = true;
        }
        int lastNewLine = 0;
        if (partialMessage.lastIndexOf('\n') != -1) {
            lastNewLine = partialMessage.lastIndexOf('\n');
        }
        String line = partialMessage.substring(lastNewLine);
        System.out.println("line: " + line);
        float partialHeight = (Main.sketch.textAscent() + Main.sketch.textDescent() + 15) * ((partialMessage.replaceAll("[^\n]", "")).length() + 1);
        Main.sketch.rect(300, (float) (box.y + box.height), 100, -1 * partialHeight);
        if (Main.sketch.textWidth(line) >= box.width) {
            partialMessage = partialMessage.substring(0, partialMessage.lastIndexOf(' '));
            currentChar = partialMessage.length() + 1;
            partialMessage += '\n';
        }
        if (partialHeight >= box.height) {
            partialMessage = partialMessage.substring(partialMessage.indexOf('\n'));
        }
        Main.sketch.fill(0,255,0);
        Main.sketch.textAlign(alignment, Main.sketch.CENTER);
        Main.sketch.text(partialMessage, (float) box.x, (float) box.y, (float) (box.width + 1000), (float) box.height);

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
            Main.sketch.textAlign(alignment, Main.sketch.CENTER);
        } else if (color.equals("orange")) {
            Main.sketch.fill(255,165,0);
        } else if (color.equals("blue")) {
            Main.sketch.fill(0,223,255);
        } else if (color.equals("purple")) {
            Main.sketch.fill(255,78,255);
        }
        Main.sketch.text(message.substring(0, currentChar), (float) box.x, (float) box.y, (float) box.width, (float) box.height);
    }

    private void printText() {
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
        Main.sketch.stroke(122, 57, 7);
        Main.sketch.fill(122, 57, 7);
        Main.sketch.rect((float) (box.x + (box.width / 2)),
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.1),
                (float) (box.height * 1.15),
                20);
        Main.sketch.stroke(98, 46, 5);
        Main.sketch.fill(98, 46, 5);
        Main.sketch.rect((float) (box.x + (box.width / 2)),
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.05),
                (float) (box.height * 1.1),
                20);
        Main.sketch.stroke(71, 33, 3);
        Main.sketch.fill(71, 33, 3);
        Main.sketch.rect((float) (box.x + (box.width / 2)),
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.025),
                (float) (box.height * 1.05),
                20);
    }

    private void drawBlueBox() {
        Main.sketch.stroke(0, 84, 107);
        Main.sketch.fill(0, 84, 107);
        Main.sketch.rect((float) (box.x + (box.width / 2)),
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.1),
                (float) (box.height * 1.15),
                20);
        Main.sketch.stroke(0, 67, 86);
        Main.sketch.fill(0, 67, 86);
        Main.sketch.rect((float) (box.x + (box.width / 2)),
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.05),
                (float) (box.height * 1.1),
                20);
        Main.sketch.stroke(0, 48, 61);
        Main.sketch.fill(0, 48, 61);
        Main.sketch.rect((float) (box.x + (box.width / 2)),
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.025),
                (float) (box.height * 1.05),
                20);
    }

    private void drawPurpleBox() {
        Main.sketch.stroke(134, 31, 126);
        Main.sketch.fill(134, 31, 126);
        Main.sketch.rect((float) (box.x + (box.width / 2)),
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.1),
                (float) (box.height * 1.15),
                20);
        Main.sketch.stroke(108, 24, 101);
        Main.sketch.fill(108, 24, 101);
        Main.sketch.rect((float) (box.x + (box.width / 2)),
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.05),
                (float) (box.height * 1.1),
                20);
        Main.sketch.stroke(77, 17, 72);
        Main.sketch.fill(77, 17, 72);
        Main.sketch.rect((float) (box.x + (box.width / 2)),
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.025),
                (float) (box.height * 1.05),
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
        if (color.equals("not") && thisChar != '\b' && Main.sketch.frameCount % 4 == 0) { //Use "\b" in text to add pauses in typing without changing look.
            ArrayList<MediaPlayer> terminalSounds = ((Sketch) Main.sketch).getTerminalSounds();
            terminalSounds.get(Main.sketch.frameCount % ((Sketch) Main.sketch).getTerminalSounds().size()).start();

        }
    }

}
