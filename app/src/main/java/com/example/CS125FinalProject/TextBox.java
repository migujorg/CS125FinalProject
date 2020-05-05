package com.example.CS125FinalProject;

import android.media.MediaPlayer;

import java.util.ArrayList;

import processing.core.PFont;

/** This class manages rendering text on the canvas.*/
public class TextBox {
    private static final double TYPE_DELAY = 1;
    private Rectangle box = new Rectangle();
    private String message;
    private int alignment;
    //private PFont font = Main.sketch.createFont("Arial", 100, true);
    private int currentChar;
    private int messageLength; //This is for optimization so it doesn't have to call message.length() a billion times.
    private String partialMessage = "";
    private boolean complete;
    private String color = "";
    PFont font;
    public TextBox() {};

    public TextBox(double setX, double setY, String setMessage) {
        box.x = setX;
        box.y = setY;
        box.width = Sketch.PIXEL2XL_DISPLAY_W * 0.8;
        box.height = Sketch.PIXEL2XL_DISPLAY_H * 0.8;
        message = setMessage;
        messageLength = message.length();
        font = Main.sketch.createFont("thinPixel.ttf", 150, false);
    }

    public TextBox(String setMessage) { //By default it puts the message in the center of the screen!
        box.width = Sketch.PIXEL2XL_DISPLAY_W * 0.8;
        box.height = setMessage.length() * 3.1;
        box.x = (Sketch.PIXEL2XL_DISPLAY_W / (float) 2) - (box.width) / 2;
        box.y = Sketch.PIXEL2XL_DISPLAY_H / (float) 4;
        message = setMessage;
        messageLength = message.length();
        font = Main.sketch.createFont("thinPixel.ttf", 200, false);
    }

    /**
     * @param setX x coordinate
     * @param setY y coordinate
     * @param setMessage the message
     * @param setFont the desired font (.ttf)
     * @param scale the scale of the text
     * @param isSmooth will it be smoothed in rendering?
     */
    public TextBox(double setX, double setY, String setMessage, String setFont, double scale, boolean isSmooth) {
        box.width = Sketch.PIXEL2XL_DISPLAY_W * 0.8;
        box.height = Sketch.PIXEL2XL_DISPLAY_H * 0.8;
        box.x = (Sketch.PIXEL2XL_DISPLAY_H / (float) 2) - (box.width / 2) + setX;
        box.y = setY;
        message = setMessage;
        messageLength = message.length();
        font = Main.sketch.createFont(setFont, (float) scale, isSmooth);
    }

     TextBox(double setX, double setY, double setWidth, double setHeight, String setMessage) {
        box.width = setWidth;
        box.height = setHeight;
        box.x = (Sketch.PIXEL2XL_DISPLAY_W / (float) 2) - (box.width / 2) + setX;
        box.y = setY;
        message = setMessage;
        messageLength = message.length();
        font = Main.sketch.createFont("thinPixel.ttf", (float) 125, false);
    }

    TextBox(double setX, double setY, double setWidth, double setHeight, String setMessage, int setAlignment, String setColor) {
        box.width = setWidth;
        box.height = setHeight;
        box.x = (Sketch.PIXEL2XL_DISPLAY_W / (float) 2) - (box.width / 2) + setX;
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
        if (!color.equals("not")) {
            slowText();
        } else {
            scrollyText();
        }
    }

    /** This method --WAS A HUGE PAIN OH MY GOD-- renders the text on screen and automatically
     *  deletes the oldest line once the text is too long to fit in it's box.
     *  '\b' characters can be used to add dramatic pause in the message!
     *  Super duper useful for really long paragraphs. They no longer have to take up the whole screen! */
    private void scrollyText() {
        Main.sketch.fill(0,255,0);
        Main.sketch.textAlign(alignment, Main.sketch.CENTER);
        Main.sketch.textFont(font);
        if (currentChar < messageLength) {
            char thisChar = message.charAt(currentChar);
            if (Main.sketch.frameCount % TYPE_DELAY == 0) {
                partialMessage += message.charAt(currentChar);
                currentChar++;
                if (thisChar != '\b') {
                    playSounds(thisChar);
                }
                scrollyTextHelper();
            }
        } else {
            complete = true;
        }
        Main.sketch.text(partialMessage, (float) box.x, (float) box.y, (float) (box.width + 1000), (float) box.height);

    }

    private void scrollyTextHelper() {
        int lastNewLine = 0;
        if (partialMessage.lastIndexOf('\n') != -1) {
            lastNewLine = partialMessage.lastIndexOf('\n');
        }
        String line = partialMessage.substring(lastNewLine);
        float partialHeight = (Main.sketch.textAscent() + Main.sketch.textDescent() + 15) * ((partialMessage.replaceAll("[^\n]", "")).length() + 1);
        if (Main.sketch.textWidth(line) >= box.width) {
            partialMessage = partialMessage.substring(0, partialMessage.lastIndexOf(' '));
            currentChar = message.substring(0, currentChar).lastIndexOf(' ') + 1;
            partialMessage += '\n';
        }
        if (partialHeight >= box.height) {
            partialMessage = partialMessage.substring(partialMessage.indexOf('\n') + 1);
        }
    }

    private void slowText() {
        if (currentChar < messageLength) { //Process stops once message in finished (to prevent lag)
            char thisChar = message.charAt(currentChar);
            if (Main.sketch.frameCount % TYPE_DELAY == 0) {
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
        } else if (color.equals("red")) {
            Main.sketch.fill(255,115,116);
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
        } else if (color.equals("red")) {
            drawRedBox();
        } else {
            drawTerminalBox();
        }
        Main.sketch.rectMode(Main.sketch.CORNER);
    }

    boolean isComplete() {
        return complete;
    }

    void reset() {
        complete = false;
        partialMessage = "";
        currentChar = 0;

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

    private void drawRedBox() {
        Main.sketch.stroke(147, 44, 62);
        Main.sketch.fill(147, 44, 62);
        Main.sketch.rect((float) (box.x + (box.width / 2)),
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.1),
                (float) (box.height * 1.15),
                20);
        Main.sketch.stroke(118, 35, 49);
        Main.sketch.fill(118, 35, 49);
        Main.sketch.rect((float) (box.x + (box.width / 2)),
                (float) ((box.y) + (box.height / 2)),
                (float) (box.width * 1.05),
                (float) (box.height * 1.1),
                20);
        Main.sketch.stroke(85, 25, 36);
        Main.sketch.fill(85, 25, 36);
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
